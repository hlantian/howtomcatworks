package com.zxiaoyao.htw.ex05.core;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/20 17:15
 */
public class SimpleLoader implements Loader {

    private static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    private ClassLoader classLoader = null;

    private Container container;

    public SimpleLoader() {
        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(WEB_ROOT);
        try {
            String repository = (new URL("file",null,classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null,repository,streamHandler);
            classLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
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
        return "A simple loader";
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
