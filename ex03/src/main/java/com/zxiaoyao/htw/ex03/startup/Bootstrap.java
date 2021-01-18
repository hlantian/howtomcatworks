package com.zxiaoyao.htw.ex03.startup;

import com.zxiaoyao.htw.ex03.connector.http.HttpConnector;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/18 16:49
 */
public class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
