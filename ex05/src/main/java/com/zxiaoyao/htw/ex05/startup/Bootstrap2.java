package com.zxiaoyao.htw.ex05.startup;

import com.zxiaoyao.htw.ex05.core.SimpleContext;
import com.zxiaoyao.htw.ex05.core.SimpleContextMapper;
import com.zxiaoyao.htw.ex05.core.SimpleLoader;
import com.zxiaoyao.htw.ex05.core.SimpleWrapper;
import com.zxiaoyao.htw.ex05.valves.ClientIPLoggerValve;
import com.zxiaoyao.htw.ex05.valves.HeaderLoggerValve;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/21 11:39
 */
public class Bootstrap2 {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");

        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new SimpleContext();
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Valve valve1 = new HeaderLoggerValve();
        Valve valve2 = new ClientIPLoggerValve();
        ((SimpleContext) context).addValve(valve1);
        ((SimpleContext) context).addValve(valve2);

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        context.addMapper(mapper);
        Loader loader = new SimpleLoader();
        context.setLoader(loader);
        context.addServletMapping("/Primitive","Primitive");
        context.addServletMapping("/Modern","Modern");

        connector.setContainer(context);

        try {
            connector.initialize();
            connector.start();

            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
