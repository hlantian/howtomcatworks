package com.zxiaoyao.htw.ex15.startup;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.ContextConfig;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 14:57
 */
public class Bootstrap {

    public static void main(String[] args) {
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector = new HttpConnector();

        Context ctx = new StandardContext();
        ctx.setPath("/app1");
        ctx.setDocBase("app1");
        ((StandardContext) ctx).addLifecycleListener(new ContextConfig());

        Host host = new StandardHost();
        host.addChild(ctx);
        host.setName("localhost");
        host.setAppBase("webapps");

        ctx.setLoader(new WebappLoader());
        connector.setContainer(host);

        try {
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) host).start();

            Container[] cs = ctx.findChildren();
            for(int i = 0; i< cs.length;i++){
                System.out.println(cs[i].getName());
            }
            System.in.read();
            ((Lifecycle)host).stop();

        }catch (Exception e){

        }
    }
}
