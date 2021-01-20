package com.zxiaoyao.htw.ex05.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:43
 */
public class SimpleWrapperValve implements Valve, Contained {
    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {

    }
}
