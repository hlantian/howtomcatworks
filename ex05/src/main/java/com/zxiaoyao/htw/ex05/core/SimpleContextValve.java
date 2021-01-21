package com.zxiaoyao.htw.ex05.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/21 13:37
 */
public class SimpleContextValve implements Valve, Contained {

    protected Container container;

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        if (!(request.getRequest() instanceof HttpServletRequest) || !(response.getResponse() instanceof HttpServletResponse)) {
            return;
        }

        HttpServletRequest hreq = (HttpServletRequest) request.getRequest();
        String contextPath = hreq.getContextPath();
        String requestURI = ((HttpRequest)request).getDecodedRequestURI();
        String relativeURI = requestURI.substring(contextPath.length()).toLowerCase();
        Context context = (Context)getContainer();
        Wrapper wrapper = null;

        try {
            wrapper = (Wrapper)context.map(request,true);
        }catch (IllegalArgumentException e){
            badRequest(requestURI,(HttpServletResponse)response);
            return;
        }
        if(wrapper == null){
            notFound(requestURI,(HttpServletResponse)response);
        }
        response.setContext(context);
        wrapper.invoke(request,response);
    }

    private void badRequest(String requestURI,HttpServletResponse response){
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,requestURI);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notFound(String requestURI,HttpServletResponse response){
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,requestURI);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
