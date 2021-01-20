package com.zxiaoyao.htw.ex05.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:13
 */
public class SimplePipeline implements Pipeline {

    private Container container;

    public SimplePipeline(Container container) {
        this.container = container;
    }

    public Valve getBasic() {
        return null;
    }

    public void setBasic(Valve valve) {

    }

    public void addValve(Valve valve) {

    }

    public Valve[] getValves() {
        return new Valve[0];
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {

    }

    public void removeValve(Valve valve) {

    }
}
