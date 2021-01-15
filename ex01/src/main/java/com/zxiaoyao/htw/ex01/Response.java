package com.zxiaoyao.htw.ex01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/14 17:22
 */
public class Response {

    private static  final Logger logger = LoggerFactory.getLogger(Response.class);
    private static final int BUFFER_SIZE = 1024;

    private Request request;

    private OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        System.out.println("HttpServer.WEB_ROOT = " + HttpServer.WEB_ROOT);
        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                StringBuffer bf = new StringBuffer(1024);
                fis = new FileInputStream(file);
                int ch = fis.read(bytes,0,BUFFER_SIZE);
                while(ch != -1){
                    output.write(bytes,0,ch);
                    for (int j =0;j<ch;j++){
                        bf.append((char)bytes[j]);
                    }
                    ch = fis.read(bytes,0,BUFFER_SIZE);

                }
                String s = bf.toString();
                System.out.println(s);
            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
