package com.zxiaoyao.htw.ex07.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

public class SimplePipeline implements Pipeline, Lifecycle {
    protected Valve basic = null;
    protected  Container container;
    protected Valve[] valves = new Valve[0];

    public SimplePipeline(Container container) {
        setContainer(container);
    }

    public Container getContainer() {
        return container;
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
        return null;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
        ((Contained)valve).setContainer(this.container);
    }

    @Override
    public void addValve(Valve valve) {
        if(valve instanceof Contained){
            ((Contained)valve).setContainer(this.container);
        }
        synchronized (valves){
            Valve[] results = new Valve[valves.length +1];
            System.arraycopy(valves,0,results,0,valves.length);
            results[valves.length] = valve;
            valves = results;
        }
    }

    @Override
    public Valve[] getValves() {
        return this.valves;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        (new StandardPipelineValueContext()).invokeNext(request,response);
    }

    @Override
    public void removeValve(Valve valve) {

    }

    protected class StandardPipelineValueContext implements ValveContext {

        protected  int stage =0;
        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            int subscript = stage;
            stage +=1;
            if(subscript<valves.length){
                valves[subscript].invoke(request,response,this);
            }else if(subscript ==valves.length && basic!= null){
                basic.invoke(request,response,this);
            }else {
                throw new ServletException("No valve");
            }
        }
    }
}
