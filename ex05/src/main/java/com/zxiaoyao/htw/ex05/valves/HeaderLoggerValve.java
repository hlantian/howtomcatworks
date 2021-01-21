package com.zxiaoyao.htw.ex05.valves;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:17
 */
public class HeaderLoggerValve implements Valve, Contained {

    private Container container;
    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        context.invokeNext(request,response);
        System.out.println("Header Logger Valve");

        ServletRequest sreq = request.getRequest();
        if(sreq instanceof HttpServletRequest){
            HttpServletRequest hreq = (HttpServletRequest)sreq;
            Enumeration headerNames = hreq.getHeaderNames();
            while(headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement().toString();
                String headerValue = hreq.getHeader(headerName);
                System.out.println(headerName + ":" + headerValue);
            }
        }else {
            System.out.println("Not an HTTP Request");
        }

        System.out.println("-------------------------------------------------------------");

    }
}
