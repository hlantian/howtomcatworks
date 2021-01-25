package com.zxiaoyao.htw.ex09.startup;

import com.zxiaoyao.htw.ex09.core.SimpleContextConfig;
import com.zxiaoyao.htw.ex09.core.SimpleWrapper;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.session.StandardManager;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/25 11:22
 */
public class Bootstrap {
    public static void main(String[] args) {
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Session");
        wrapper1.setServletClass("SessionServlet");

        Context context = new StandardContext();
        context.setPath("/myApp");
        context.setDocBase("myApp");
        context.addChild(wrapper1);
        context.addServletMapping("/myApp/Session","Session");

        LifecycleListener listener = new SimpleContextConfig();
        ((StandardContext) context).addLifecycleListener(listener);

        Loader loader = new  WebappLoader();
        context.setLoader(loader);
        connector.setContainer(context);

        Manager manager = new StandardManager();
        context.setManager(manager);

        try{
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) context).start();

            System.in.read();
            ((Lifecycle) context).stop();
        }catch (Exception t){
            t.printStackTrace();
        }

    }
}
