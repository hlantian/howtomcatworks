package com.zxiaoyao.htw.ex05.valves;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:18
 */
public class ClientIPLoggerValve implements Valve, Contained {

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
        System.out.println("Client IP Logger Valve");
        ServletRequest sreq = request.getRequest();
        System.out.println(sreq.getRemoteAddr());
        System.out.println("---------------------------------------------------");
    }
}
