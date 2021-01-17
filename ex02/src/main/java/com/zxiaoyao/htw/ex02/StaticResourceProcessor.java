package com.zxiaoyao.htw.ex02;

import java.io.IOException;

public class StaticResourceProcessor {

    public void process(Request request,Respone respone){
        try {
            respone.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
