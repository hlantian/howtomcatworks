package com.zxiaoyao.htw.ex04.startup;

import com.zxiaoyao.htw.ex04.core.SimpleContainer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 10:22
 */
public class Bootstrap {

    public static void main(String[] args) {

        HttpConnector connector = new HttpConnector();
        SimpleContainer container = new SimpleContainer();
        connector.setContainer(container);
        try {
            connector.initialize();
            connector.start();

            // make the application wait until we press any key.
            System.in.read();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
