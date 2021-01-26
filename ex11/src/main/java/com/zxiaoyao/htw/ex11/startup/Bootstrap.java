package com.zxiaoyao.htw.ex11.startup;

import com.zxiaoyao.htw.ex11.core.SimpleContextConfig;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.loader.WebappLoader;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/26 17:58
 */
public class Bootstrap {

    public static void main(String[] args) {
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new StandardWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context ctx= new StandardContext();
        ctx.setPath("/myApp");
        ctx.setDocBase("myApp");
        LifecycleListener listener = new SimpleContextConfig();
        ((StandardContext) ctx).addLifecycleListener(listener);

        ctx.addChild(wrapper1);
        ctx.addChild(wrapper2);
        ctx.setLoader(new WebappLoader());
        ctx.addServletMapping("/Primitive","Primitive");
        ctx.addServletMapping("/Modern","Modern");

        connector.setContainer(ctx);
        try {
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) ctx).start();

            // make the application wait until we press a key.
            System.in.read();
            ((Lifecycle) ctx).stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
