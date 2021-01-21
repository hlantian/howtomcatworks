package com.zxiaoyao.htw.ex01.test;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/21 14:36
 */
public class Object2 {
    private String name;
    private Integer age;

    private Object1 object1;
    public Object2(String name, Integer age,Object1 object1) {
        this.name = name;
        this.age = age;
        this.object1 = object1;
    }

    @Override
    public String toString() {
        return "Object2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", object1=" + object1.toString() +
                '}';
    }
}
