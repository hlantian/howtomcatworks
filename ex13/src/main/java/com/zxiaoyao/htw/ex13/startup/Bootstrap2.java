package com.zxiaoyao.htw.ex13.startup;

import com.zxiaoyao.htw.ex13.core.SimpleContextConfig;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.loader.WebappLoader;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 10:50
 */
public class Bootstrap2 {
    public static void main(String[] args) {
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector = new HttpConnector();

        Wrapper wrapper1 = new StandardWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context c = new StandardContext();
        c.setPath("/app1");
        c.setDocBase("app1");

        c.addChild(wrapper1);
        c.addChild(wrapper2);

        ((Lifecycle)c).addLifecycleListener(new SimpleContextConfig());

        Host host = new StandardHost();
        host.addChild(c);
        host.setName("localhost");
        host.setAppBase("webapps");

        c.setLoader(new WebappLoader());
        c.addServletMapping("/Primitive","Primitive");
        c.addServletMapping("/Modern","Modern");

        Engine engine = new StandardEngine();
        engine.addChild(host);
        engine.setDefaultHost("localhost");

        connector.setContainer(engine);

        try{
           connector.initialize();
            ((Lifecycle)connector).start();
            ((Lifecycle)engine).start();

            System.in.read();
            ((Lifecycle)engine).stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
