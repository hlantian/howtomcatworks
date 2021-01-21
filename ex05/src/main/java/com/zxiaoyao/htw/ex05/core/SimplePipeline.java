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

    private Container container = null;

    protected Valve basic = null;

    private Valve[] valves = new Valve[0];

    public void setContainer(Container container) {
        this.container = container;
    }

    public SimplePipeline(Container container) {
        setContainer(container);
    }

    public Valve getBasic() {
        return this.basic;
    }

    public void setBasic(Valve valve) {
        this.basic = valve;
        ((Contained)valve).setContainer(this.container);
    }

    public void addValve(Valve valve) {
        if(valve instanceof Contained){
            ((Contained)valve).setContainer(container);
        }
        synchronized (valves){
            Valve[] results = new Valve[valves.length+1];
            System.arraycopy(valves,0,results,0,valves.length);
            results[valves.length]= valve;
            valves =results;
        }
    }

    public Valve[] getValves() {
        return this.valves;
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        (new SimplePipelineValveContext()).invokeNext(request,response);
    }

    public void removeValve(Valve valve) {

    }

    protected class SimplePipelineValveContext implements ValveContext{

        protected int stage = 0;

        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            int subcript = stage;
            this.stage +=1;
            if(subcript <valves.length){
                valves[subcript].invoke(request,response,this);
            }else if(subcript == valves.length && basic != null){
                basic.invoke(request,response,this);
            }else {
                throw new ServletException("No valve");
            }
        }
    }
}
