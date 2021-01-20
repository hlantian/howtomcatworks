package com.zxiaoyao.htw.ex05.core;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

import java.beans.PropertyChangeListener;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:15
 */
public class SimpleLoader implements Loader {
    public ClassLoader getClassLoader() {
        return null;
    }

    public Container getContainer() {
        return null;
    }

    public void setContainer(Container container) {

    }

    public DefaultContext getDefaultContext() {
        return null;
    }

    public void setDefaultContext(DefaultContext defaultContext) {

    }

    public boolean getDelegate() {
        return false;
    }

    public void setDelegate(boolean delegate) {

    }

    public String getInfo() {
        return null;
    }

    public boolean getReloadable() {
        return false;
    }

    public void setReloadable(boolean reloadable) {

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    public void addRepository(String repository) {

    }

    public String[] findRepositories() {
        return new String[0];
    }

    public boolean modified() {
        return false;
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
