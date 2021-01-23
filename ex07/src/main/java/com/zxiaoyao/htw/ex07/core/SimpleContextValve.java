package com.zxiaoyao.htw.ex07.core;

import org.apache.catalina.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleContextValve implements Valve, Contained {

    protected  Container container;
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
        if(!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)){
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String contextPath = httpServletRequest.getContextPath();
        String requestURI = ((HttpRequest) request).getDecodedRequestURI();
        String relativeURI = requestURI.substring(contextPath.length()).toLowerCase();

        Context context = (Context) getContainer();
        Wrapper wrapper = null;
        try{
            wrapper = (Wrapper)context.map(request,true);
        }catch (IllegalArgumentException e) {
            badRequest(requestURI,httpServletResponse);
            return;
        }
        if(wrapper == null){
            notFound(requestURI,httpServletResponse);
            return;
        }

        response.setContext(context);
        wrapper.invoke(request,response);
    }

    private void badRequest(String requestURI, HttpServletResponse response){
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,requestURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void notFound(String requestURI, HttpServletResponse response){
        try {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,requestURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
