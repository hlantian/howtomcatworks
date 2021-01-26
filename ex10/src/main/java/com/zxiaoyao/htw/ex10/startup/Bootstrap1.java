package com.zxiaoyao.htw.ex10.startup;

import com.zxiaoyao.htw.ex10.core.SimpleContextConfig;
import com.zxiaoyao.htw.ex10.core.SimpleWrapper;
import com.zxiaoyao.htw.ex10.realm.SimpleRealm;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.loader.WebappLoader;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/25 17:38
 */
public class Bootstrap1 {
    public static void main(String[] args) {
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new StandardContext();
        context.setPath("/myApp");
        context.setDocBase("myApp");

        LifecycleListener listener = new SimpleContextConfig();
        ((StandardContext) context).addLifecycleListener(listener);

        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Loader loader = new WebappLoader();
        context.setLoader(loader);
        context.addServletMapping("/Primitive","Primitive");
        context.addServletMapping("/Modern","Modern");

        SecurityCollection securityCollection = new SecurityCollection();
        securityCollection.addPattern("/");
        securityCollection.addMethod("GET");

        SecurityConstraint constraint = new SecurityConstraint();
        constraint.addCollection(securityCollection);
        constraint.addAuthRole("manager");
        LoginConfig loginConfig = new LoginConfig();
        loginConfig.setRealmName("Simple Realm");

        Realm realm = new SimpleRealm();
        context.setRealm(realm);
        context.addConstraint(constraint);
        context.setLoginConfig(loginConfig);

        connector.setContainer(context);

        try{
           connector.initialize();
            ((Lifecycle)connector).start();
            ((Lifecycle)context).start();

            System.in.read();
            ((Lifecycle)context).stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
