package com.zxiaoyao.htw.ex07.core;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class SimpleContextLifecycleListener implements LifecycleListener {
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        Lifecycle lifecycle = event.getLifecycle();
        String eventType = event.getType();
        System.out.println("SimpleContextLifecycleListener's event " + eventType);
        if(Lifecycle.START_EVENT.equals(eventType)){
            System.out.println("Starting context");
        }else if(Lifecycle.STOP_EVENT.equals(eventType)){
            System.out.println("Stopping context");
        }else {
            System.out.println("doing "+ eventType);
        }

    }
}
