package com.zxiaoyao.htw.ex06.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/22 14:59
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
        ServletRequest servletRequest = request.getRequest();
        ServletResponse servletResponse = response.getResponse();
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            return;
        }

        HttpServletRequest hreq = (HttpServletRequest)servletRequest;
        HttpServletResponse hres = (HttpServletResponse) servletResponse;
        String contextPath = hreq.getContextPath();
        String requsetURI = ((HttpRequest)request).getDecodedRequestURI();
        String relativeURI = requsetURI.substring(contextPath.length()).toLowerCase();
        Context context = (Context)getContainer();

        Wrapper wrapper = null;
        try {
            wrapper = (Wrapper)context.map(request,true);
        }catch (IllegalArgumentException e){
            badRequest(relativeURI,hres);
            return;
        }
        if(wrapper == null){
            notFound(relativeURI,hres);
            return;
        }

        response.setContext(context);
        wrapper.invoke(request,response);
    }

    private void badRequest(String requestURI, HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notFound(String requestURI, HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
