package com.zxiaoyao.htw.ex09.core;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/25 14:04
 */
public class SimpleContextConfig implements LifecycleListener {
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if(Lifecycle.START_EVENT.equals(event.getType())){
            Context context = (Context) event.getLifecycle();
            context.setConfigured(true);
        }
    }
}
