package com.zxiaoyao.htw.ex03.connector.http;

import java.io.IOException;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/18 17:30
 */
public class StaticResourceProcessor {

    public void process(HttpRequest request,HttpResponse response){
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
