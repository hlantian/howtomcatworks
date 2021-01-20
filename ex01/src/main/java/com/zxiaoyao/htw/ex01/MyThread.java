package com.zxiaoyao.htw.ex01;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 14:15
 */
public class MyThread implements Runnable {


    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyThread thread run name = " + Thread.currentThread().getName());
        }
    }

    public void start(){
        System.out.println("MyThread thread run start = " + Thread.currentThread().getName());
        Thread thread = new Thread(this);
        thread.start();
    }
}
