package com.zxiaoyao.htw.ex01;

import java.io.*;
import java.net.Socket;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/15 16:13
 */
public class SocketDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1",8080);
//        OutputStream os = socket.getOutputStream();
        boolean autoFlush = true;
        PrintWriter out = new PrintWriter(socket.getOutputStream(),autoFlush);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("GET /servlet/ModernServlet HTTP/1.1");
        out.println("Host: localhost:8080");
//        out.println("Connection: Close");

        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while (loop){
            if(in.ready()){
                int i = 0;
                while(i != -1){
                    i = in.read();
                    sb.append((char)i);
                }
                loop =false;
            }
            Thread.currentThread().sleep(50);
        }
        System.out.println(sb.toString());
        socket.close();
    }
}
