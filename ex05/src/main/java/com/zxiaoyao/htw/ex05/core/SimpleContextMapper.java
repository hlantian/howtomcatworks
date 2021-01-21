package com.zxiaoyao.htw.ex05.core;

import org.apache.catalina.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/21 11:45
 */
public class SimpleContextMapper implements Mapper {

    private SimpleContext context = null;

    @Override
    public Container getContainer() {
        return this.context;
    }

    @Override
    public void setContainer(Container container) {
        if(!(container instanceof SimpleContext)){
            throw new IllegalArgumentException("Illegal type of container");
        }
        this.context = (SimpleContext) container;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String protocol) {

    }

    @Override
    public Container map(Request request, boolean update) {
        String contextPath = ((HttpServletRequest)request.getRequest()).getContextPath();
        String requstURI = ((HttpRequest)request).getDecodedRequestURI();
        String relativeURI = requstURI.substring(contextPath.length());
        Wrapper wrapper = null;
        String servletPath = relativeURI;
        String pathINfo = null;
        String name = this.context.findServletMapping(relativeURI);
        if(name != null){
            wrapper = (Wrapper)this.context.findChild(name);
        }
        return wrapper;
    }
}
