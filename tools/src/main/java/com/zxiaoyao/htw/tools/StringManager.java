package com.zxiaoyao.htw.tools;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/18 15:51
 */
public class StringManager {

    private ResourceBundle bundle;

    /**
     * 构造器
     *
     * @param packageName 包名
     */
    public StringManager(String packageName) {
        String bundleName = packageName + ".LocalStrings";
        this.bundle = ResourceBundle.getBundle(bundleName);
    }


    /**
     * Get a string from the underlying resource bundle.
     *
     * @param key key
     * @return 结果
     */
    public String getString(String key) {
        if (key == null) {
            String msg = "key is null";
            throw new NullPointerException(msg);
        }
        String str;

        try {
            str = bundle.getString(key);
        } catch (MissingResourceException mre) {
            str = "Cannot find message associated with key '" + key + "'";
        }

        return str;
    }

    public String getString(String key, Object[] args) {
        String iString;
        String value = getString(key);
        Object nonNUllArgs[] = args;
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    if (nonNUllArgs == args) {
                        nonNUllArgs = args.clone();
                    }
                    nonNUllArgs[i] = "null";
                }
            }
            iString = MessageFormat.format(value, nonNUllArgs);
        } catch (IllegalArgumentException iae) {
            StringBuffer buf = new StringBuffer();
            buf.append(value);
            for (int i = 0; i < args.length; i++) {
                buf.append(" arg[" + i + "]=" + args[i]);
            }
            iString = buf.toString();
        }

        return iString;
    }

    public String getString(String key,Object arg){
        Object[] args = new Object[]{arg};
        return getString(key,args);
    }

    public String getString(String key, Object arg1,Object arg2){
        Object[] args = new Object[]{arg1,arg2};
        return getString(key,args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3) {
        Object[] args = new Object[] {arg1, arg2, arg3};
        return getString(key, args);
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4) {
        Object[] args = new Object[] {arg1, arg2, arg3, arg4};
        return getString(key, args);
    }

    private static Hashtable managers = new Hashtable();

    public synchronized static StringManager getManager(String packageName){
        StringManager mgr = (StringManager) managers.get(packageName);
        if(mgr == null){
            mgr = new StringManager(packageName);
            managers.put(packageName,mgr);
        }
        return mgr;
    }
}
