package com.zxiaoyao.htw.ex07.startup;

import com.zxiaoyao.htw.ex07.core.*;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.logger.FileLogger;

public class BootStrap {
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
        ((SimpleContext) context).addLifecycleListener(listener);
        context.addMapper(mapper);

        Loader loader = new SimpleLoader();
        context.setLoader(loader);

        context.addServletMapping("/Primitive","Primitive");
        context.addServletMapping("/Modern","Modern");

        System.setProperty("catalina.base",System.getProperty("user.dir"));
        FileLogger logger = new FileLogger();
        logger.setPrefix("FileLog_");
        logger.setSuffix(".txt");
        logger.setTimestamp(true);
        logger.setDirectory("webroot");
        context.setLogger(logger);

        connector.setContainer(context);

        try{
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) context).start();

            System.in.read();
            ((Lifecycle)context).stop();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
