package com.zxiaoyao.htw.ex01.test;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/21 14:35
 */
public class Object1 {

    private String context;
    private Object2 object2 = new Object2("zhangsan",19,this);

    public Object1(String context) {
        System.out.println(object2.toString());
        this.context = context;
    }

    @Override
    public String toString() {
        return "Object1{" +
                "context='" + context + '\'' +
                ", object2=" + object2.toString() +
                '}';
    }
}
