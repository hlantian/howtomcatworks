package com.zxiaoyao.htw.ex06;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 15:20
 */
public class ShutdownHookDemo {

    public void start(){
        System.out.println("demo");
        ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
    public static void main(String[] args) {
        ShutdownHookDemo demo = new ShutdownHookDemo();
        demo.start();
        try {
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ShutdownHook extends Thread {
        public void  run(){
            System.out.println("Shutting down");
        }
    }
}
