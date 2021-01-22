package com.zxiaoyao.htw.ex06.core;

import org.apache.catalina.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/22 11:55
 */
public class SimpleContextMapper implements Mapper {

    private SimpleContext context;

    @Override
    public Container getContainer() {
        return this.context;
    }

    @Override
    public void setContainer(Container container) {
        if(!(container instanceof SimpleContext)){
            throw new IllegalArgumentException("Illegal type of Container");
        }
        this.context  = (SimpleContext) container;
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
        if(update && request.getWrapper() != null){
            return request.getWrapper();
        }
        String contextPath = ((HttpServletRequest) request.getRequest()).getContextPath();
        String requestURI = ((HttpRequest)request).getDecodedRequestURI();
        String relativeURI = requestURI.substring(contextPath.length());
        Wrapper wrapper = null;
        String servletPath = relativeURI;
        String pathInfo = null;
        String name = this.context.findServletMapping(relativeURI);
        if(name != null){
            wrapper = (Wrapper) context.findChild(name);
        }
        if(update){
            request.setWrapper(wrapper);
            ((HttpRequest)request).setServletPath(servletPath);
            ((HttpRequest)request).setPathInfo(pathInfo);
        }
        return wrapper;
    }
}
