package com.zxiaoyao.htw.ex03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/18 16:48
 */
public class HttpConnector implements Runnable {

    private boolean stopped;
    private String schema = "http";

    public String getSchema() {
        return schema;
    }

    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while(!stopped){
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                continue;
            }
            HttpProcessor processor = new HttpProcessor(this);
            processor.process(socket);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
