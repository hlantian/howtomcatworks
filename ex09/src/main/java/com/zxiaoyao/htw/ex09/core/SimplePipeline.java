package com.zxiaoyao.htw.ex09.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/25 11:24
 */
public class SimplePipeline implements Pipeline, Lifecycle {

    private Valve basic = null;
    protected Container container;
    protected Valve[] valves =  new Valve[0];;

    public SimplePipeline(Container container) {
        setContainer(container);
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public Valve getBasic() {
        return this.basic;
    }

    @Override
    public void setBasic(Valve valve) {
        ((Contained) valve).setContainer(container);
        this.basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
        if (valve instanceof Contained) {
            ((Contained) valve).setContainer(container);
        }
        synchronized (valves) {
            int currentLength = valves.length;
            Valve[] results = new Valve[currentLength];
            System.arraycopy(valves, 0, results, 0, currentLength);
            results[currentLength] = valve;
            valves = results;
        }
    }

    @Override
    public Valve[] getValves() {
        return this.valves;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        (new StandardPipelineValveContext()).invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {

    }

    protected class StandardPipelineValveContext implements ValveContext {

        private int stage = 0;

        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            int subscript = stage;
            stage += 1;
            if (subscript < valves.length) {
                valves[subscript].invoke(request, response, this);
            } else if (subscript == valves.length && basic != null) {
                basic.invoke(request, response, this);
            } else {
                throw new ServletException("No value");
            }
        }
    }
}
