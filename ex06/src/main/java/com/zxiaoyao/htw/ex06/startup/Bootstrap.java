package com.zxiaoyao.htw.ex06.startup;

import com.zxiaoyao.htw.ex06.core.*;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/22 11:46
 */
public class Bootstrap {

    public static void main(String[] args) {
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new SimpleContext();
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        LifecycleListener listener = new SimpleContextLifecycleListener();
        ((Lifecycle) context).addLifecycleListener(listener);
        context.addMapper(mapper);

        Loader loader = new SimpleLoader();
        context.setLoader(loader);

        context.addServletMapping("/Primitive","Primitive");
        context.addServletMapping("/Modern","Modern");
        connector.setContainer(context);

        try {
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) context).start();

            System.in.read();
            ((Lifecycle) context).stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
