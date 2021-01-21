package com.zxiaoyao.htw.ex05.core;

import org.apache.catalina.Container;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/21 11:45
 */
public class SimpleContextMapper implements Mapper {

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String protocol) {

    }

    @Override
    public Container map(Request request, boolean update) {
        return null;
    }
}
