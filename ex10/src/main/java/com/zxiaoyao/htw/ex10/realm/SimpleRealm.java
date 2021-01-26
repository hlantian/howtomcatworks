package com.zxiaoyao.htw.ex10.realm;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;

import javax.sound.midi.Soundbank;
import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/26 11:36
 */
public class SimpleRealm implements Realm {

    private Container container;
    private ArrayList users = new ArrayList();

    public SimpleRealm() {
        createUserDatabase();
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return "A simple Realm implementation";
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Principal authenticate(String username, String credentials) {
        System.out.println("SimpleRealm.authenticate()");
        if (username == null || credentials == null) {
            return null;
        }
        User user = getUser(username, credentials);
        return user == null ? null : new GenericPrincipal(this, user.username, user.password, user.getRoles());
    }

    private User getUser(String username, String password) {
        Iterator iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Principal authenticate(String username, byte[] credentials) {
        return null;
    }

    @Override
    public Principal authenticate(String username, String digest, String nonce, String nc, String cnonce, String qop, String realm, String md5a2) {
        return null;
    }

    @Override
    public Principal authenticate(X509Certificate[] certs) {
        return null;
    }

    @Override
    public boolean hasRole(Principal principal, String role) {
        if (role == null || !(principal instanceof GenericPrincipal)) {
            return false;
        }
        GenericPrincipal gp = (GenericPrincipal) principal;
        return gp.getRealm() == this || gp.hasRole(role);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {


    }

    private void createUserDatabase() {
        User user1 = new User("zxy", "123");
        user1.addRole("manager");
        user1.addRole("programmer");
        User user2 = new User("hlantian", "456");
        user2.addRole("programmer");

        users.add(user1);
        users.add(user2);

    }

    class User {
        public String username;
        private ArrayList roles = new ArrayList();
        public String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public void addRole(String role) {
            roles.add(role);
        }

        public ArrayList getRoles() {
            return roles;
        }
    }
}
