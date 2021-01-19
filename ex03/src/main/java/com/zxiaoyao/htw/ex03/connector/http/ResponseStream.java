package com.zxiaoyao.htw.ex03.connector.http;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/19 17:05
 */
public class ResponseStream extends ServletOutputStream {

    protected boolean closed = false;
    protected boolean commit = false;
    protected int count = 0;
    protected int length = -1;
    protected HttpResponse response = null;
    protected OutputStream stream;

    public ResponseStream(HttpResponse response) {
        super();
        closed = false;
        commit = false;
        count = 0;
        this.response = response;
    }


    public boolean getCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    public void close() throws IOException {
        if (closed) {
            throw new IOException("responseStream.flush.closed");
        }
        if (commit) {
            response.flushBuffer();
        }

    }

    public void write(int b) throws IOException {
        if (closed) {
            throw new IOException("responseStream.write.closed");
        }
        if ((length > 0) && (count >= length)) {
            throw new IOException("responseStream.write.count");
        }
        response.write(b);
        count++;
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (closed) {
            throw new IOException("responseStream.write.closed");
        }
        int actual = len;
        if ((length > 0) && ((count + len) >= length)) {
            actual = length - count;
        }
        response.write(b, off, actual);
        count += actual;
        if (actual < len) {
            throw new IOException("responseStream.write.count");
        }
    }

    boolean closed() {
        return this.closed;
    }

    void reset() {
        count = 0;
    }
}
