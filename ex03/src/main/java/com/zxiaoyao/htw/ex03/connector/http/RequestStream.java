package com.zxiaoyao.htw.ex03.connector.http;


import org.apache.catalina.util.StringManager;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/19 11:41
 */
public class RequestStream extends ServletInputStream {

    protected boolean closed = false;
    protected int count = 0;
    protected int length = -1;
    protected static StringManager sm = StringManager.getManager(Constants.Package);
    protected InputStream stream = null;

    public RequestStream(HttpRequest request) {
        super();
        this.stream = request.getStream();
        this.closed = false;
        this.length = request.getContentLength();
        this.count = 0;
    }

    public void close() throws IOException {
        if (closed) {
            throw new IOException(sm.getString("requestStream.close.closed"));
        }
        if (length > 0) {
            while (count < length) {
                int b = read();
                if (b < 0) {
                    break;
                }
            }
        }
        closed = true;
    }

    public int read() throws IOException {
        if (closed) {
            throw new IOException(sm.getString("requestStream.close.closed"));
        }
        if ((length > 0) && (count >= length)) {
            return (-1);
        }
        int b = stream.read();
        if (b >= 0) {
            count++;
        }
        return (b);
    }

    public int read(byte[] b) throws IOException {
        return (read(b, 0, b.length));
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int toRead = len;
        if (length > 0) {
            if (count >= length) {
                return (-1);
            }
            if ((count + len) > length) {
                toRead = length - count;
            }
        }
        int actuallyRead = super.read(b, off, toRead);
        return (actuallyRead);
    }


}
