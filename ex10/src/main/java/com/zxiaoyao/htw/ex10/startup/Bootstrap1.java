package com.zxiaoyao.htw.ex10.startup;

import org.apache.catalina.Connector;
import org.apache.catalina.connector.http.HttpConnector;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/25 17:38
 */
public class Bootstrap1 {
    public static void main(String[] args) {
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector = new HttpConnector();

    }
}
