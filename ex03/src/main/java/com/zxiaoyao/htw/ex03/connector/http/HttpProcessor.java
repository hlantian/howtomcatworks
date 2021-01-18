package com.zxiaoyao.htw.ex03.connector.http;

import com.zxiaoyao.htw.tools.StringManager;
import org.apache.catalina.connector.http.SocketInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/18 16:53
 */
public class HttpProcessor {

    private HttpConnector connector;
    private HttpRequest request;
    private HttpResponse response;

    protected StringManager sm = StringManager.getManager("com.zxiaoyao.htw.ex03.connector.http");

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    public void process(Socket socket){
        SocketInputStream input;
        OutputStream output;
        try {
            input = new SocketInputStream(socket.getInputStream(),2048);
            output = socket.getOutputStream();

            request = new HttpRequest(input);

            response = new HttpResponse(output);
            response.setRequest(request);

            response.setHeader("Server","zxy Servlet Container");

            parseRequest(input,output);
            parseHeader(input);

            if(request.getRequestURI().startsWith("/servlet/")){
                ServletProcessor processor = new ServletProcessor();
                processor.process(request,response);
            }else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request,response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void parseHeader(SocketInputStream input){

    }

    private void parseRequest(SocketInputStream input,OutputStream output){

    }

}
