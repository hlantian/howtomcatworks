package com.zxiaoyao.htw.ex05.valves;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:18
 */
public class ClientIPLoggerValve implements Valve, Contained {
    public Container getContainer() {
        return null;
    }

    public void setContainer(Container container) {

    }

    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {

    }
}
