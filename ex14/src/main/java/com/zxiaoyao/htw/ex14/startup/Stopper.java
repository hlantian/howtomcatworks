package com.zxiaoyao.htw.ex14.startup;

import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 13:37
 */
public class Stopper {

    public static void main(String[] args) {
        int port = 8005;
        try{
            Socket socket = new Socket("127.0.0.1",port);
            OutputStream outputStream = socket.getOutputStream();
            String shutdown = "SHUTDOWN";
            for(int i=0;i<shutdown.length();i++){
                outputStream.write(shutdown.charAt(i));
            }
            outputStream.flush();
            outputStream.close();
            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
