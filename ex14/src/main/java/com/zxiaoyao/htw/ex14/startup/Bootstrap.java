package com.zxiaoyao.htw.ex14.startup;

import com.zxiaoyao.htw.ex14.core.SimpleContextConfig;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.*;
import org.apache.catalina.loader.WebappLoader;

import javax.servlet.Servlet;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 11:26
 */
public class Bootstrap {

    public static void main(String[] args) {
        System.setProperty("catalina.base", System.getProperty("user.dir"));
        Connector connector = new HttpConnector();

        Wrapper wrapper1 = new StandardWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context ctx = new StandardContext();
        ctx.setPath("/app1");
        ctx.setDocBase("app1");

        ctx.addChild(wrapper1);
        ctx.addChild(wrapper2);


        ((StandardContext) ctx).addLifecycleListener(new SimpleContextConfig());

        Host host = new StandardHost();
        host.addChild(ctx);
        host.setName("localhost");
        host.setAppBase("webapps");


        ctx.setLoader(new WebappLoader());
        ctx.addServletMapping("/Primitive", "Primitive");
        ctx.addServletMapping("/Modern", "Modern");

        Engine engine = new StandardEngine();
        engine.addChild(host);
        engine.setDefaultHost("localhost");

        Service service = new StandardService();
        service.setName("Stand-alone service");

        Server server = new StandardServer();
        server.addService(service);
        service.addConnector(connector);
        service.setContainer(engine);

        if (server instanceof Lifecycle) {
            try {
                server.initialize();
                ((Lifecycle) server).start();
                server.await();
            } catch (LifecycleException e) {
                e.printStackTrace(System.out);
            }
        }

        if (server instanceof Lifecycle) {
            try {
                ((Lifecycle) server).stop();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
