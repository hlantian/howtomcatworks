package com.zxiaoyao.htw.ex05.startup;

import com.zxiaoyao.htw.ex05.core.SimpleLoader;
import com.zxiaoyao.htw.ex05.core.SimpleWrapper;
import com.zxiaoyao.htw.ex05.valves.ClientIPLoggerValve;
import com.zxiaoyao.htw.ex05.valves.HeaderLoggerValve;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:10
 */
public class Bootstrap1 {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        Wrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("ModernServlet");
        Loader loader = new SimpleLoader();
        Valve valve1 = new HeaderLoggerValve();
        Valve valve2 = new ClientIPLoggerValve();

        wrapper.setLoader(loader);
        ((Pipeline) wrapper).addValve(valve1);
        ((Pipeline) wrapper).addValve(valve2);
        connector.setContainer(wrapper);

        try {
            connector.initialize();
            connector.start();

            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
