package com.zxiaoyao.htw.ex01;

import java.util.StringTokenizer;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 14:15
 */
public class Mytest {

    public static void main(String[] args) {
//        System.out.println("main start name = " + Thread.currentThread().getName());
//        MyThread myThread = new MyThread();
//        myThread.start();

        String s = new String("This is a test string");
        StringTokenizer st = new StringTokenizer(s);
        System.out.println( "Token Total: " + st.countTokens() );
        while( st.hasMoreElements() ){
            System.out.println(st.nextToken());
        }
    }
}
