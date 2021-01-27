package com.zxiaoyao.htw.ex13.core;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 10:30
 */
public class SimpleContextConfig implements LifecycleListener {
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (Lifecycle.START_EVENT.equals(event.getType())) {
            ((Context) event.getLifecycle()).setConfigured(true);
        }
    }
}
