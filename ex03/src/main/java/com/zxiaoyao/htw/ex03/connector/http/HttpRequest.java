package com.zxiaoyao.htw.ex03.connector.http;

import org.apache.catalina.util.Enumerator;
import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/18 17:08
 */
public class HttpRequest implements HttpServletRequest {

    private InputStream input;
    private String contentType;
    private int contentLength;
    private InetAddress inetAddress;
    private String method;
    private String protocol;
    private String queryString;
    private String requestURI;
    private String serverName;
    private int serverPort;
    private Socket socket;
    private boolean requestedSessionCookie;
    private String requestedSessionId;
    private boolean requestedSessionURL;

    protected HashMap attributes = new HashMap();
    protected String authorization = null;
    protected String contextPath = "";
    protected ArrayList cookies = new ArrayList();
    protected ArrayList empty = new ArrayList();

    protected SimpleDateFormat[] formats = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };

    protected HashMap headers = new HashMap();

    protected ParameterMap parameters = null;
    protected boolean parsed = false;
    protected String pathInfo = null;
    protected BufferedReader reader = null;
    protected ServletInputStream stream = null;


    public HttpRequest(InputStream input) {
        this.input = input;
    }

    public void addHeader(String name, String value) {
        name = name.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(name);
            if (values == null) {
                values = new ArrayList();
                headers.put(name, values);
            }
            values.add(value);
        }
    }

    protected void parseParameters() {
        if (parsed) {
            return;
        }
        ParameterMap results = parameters;
        if (results == null) {
            results = new ParameterMap();
        }
        results.setLocked(false);
        String encoding = getCharacterEncoding();
        if (encoding == null) {
            encoding = "ISO-8859-1";
        }
        String queryString = getQueryString();
        try {
            RequestUtil.parseParameters(results, queryString, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String contentType = getContentType();
        if (contentType == null) {
            contentType = "";
        }
        int semicolon = contentType.indexOf(';');
        if (semicolon > 0) {
            contentType = contentType.substring(0, semicolon).trim();
        } else {
            contentType = contentType.trim();
        }
        if ("POST".equals(getMethod()) && (getContentLength() > 0 && "application/x-www-form-urlencoded".equals(contentType))) {
            try {
                int max = getContentLength();
                int len = 0;
                byte buf[] = new byte[getContentLength()];
                ServletInputStream is = getInputStream();
                while (len < max) {
                    int next = is.read(buf, len, max - len);
                    if (next < 0) {
                        break;
                    }
                    len += next;
                }
                is.close();
                if (len < max) {
                    throw new RuntimeException("Content length mismatch");
                }
                RequestUtil.parseParameters(results, buf, encoding);
            } catch (UnsupportedEncodingException ue) {

            } catch (IOException e) {
                throw new RuntimeException("Content read fail");
            }
        }
        results.setLocked(true);
        parsed = true;
        parameters = results;

    }

    public void addCookie(Cookie cookie) {
        synchronized (cookies) {
            cookies.add(cookie);
        }
    }

    public ServletInputStream createInputStream() {
        return (new RequestStream(this));
    }

    public InputStream getStream() {
        return input;
    }

    public void setContentLength(int length) {
        this.contentLength = length;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setInet(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void setContextPath(String path) {
        this.contextPath = path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setPathInfo(String path) {
        this.pathInfo = path;
    }

    public void setRequestedSessionCookie(boolean requestedSessionCookie) {
        this.requestedSessionCookie = requestedSessionCookie;
    }

    public void setRequestedSessionId(String requestedSessionId) {
        this.requestedSessionId = requestedSessionId;
    }

    public void setRequestedSessionURL(boolean requestedSessionURL) {
        this.requestedSessionURL = requestedSessionURL;
    }

    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        synchronized (cookies) {
            if (cookies.size() < 1) {
                return (null);
            }
            Cookie[] result = new Cookie[cookies.size()];
            return ((Cookie[]) cookies.toArray(result));
        }
    }


    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value == null) {
            return (-1L);
        }
        value += " ";

        for (int i = 0; i < formats.length; i++) {
            try {
                Date date = formats[i].parse(value);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalStateException(value);
    }

    public String getHeader(String name) {
        String lName = name.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(lName);
            return values != null ? (String) values.get(0) : null;
        }
    }

    public Enumeration getHeaders(String s) {
        String name = s.toLowerCase();
        synchronized (headers) {
            ArrayList values = (ArrayList) headers.get(name);
            return values == null ? new Enumerator(empty) : new Enumerator(values);
        }
    }

    public Enumeration getHeaderNames() {
        synchronized (headers) {
            return new Enumerator(headers.keySet());
        }
    }

    public int getIntHeader(String s) {
        String value = getHeader(s);
        return value == null ? -1 : Integer.parseInt(value);
    }

    public String getMethod() {
        return method;
    }

    public String getPathInfo() {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String s) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getServletPath() {
        return null;
    }

    public HttpSession getSession(boolean b) {
        return null;
    }

    public HttpSession getSession() {
        return null;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    /**
     * @deprecated
     */
    public boolean isRequestedSessionIdFromUrl() {
        return isRequestedSessionIdFromURL();
    }

    public Object getAttribute(String s) {
        synchronized (attributes) {
            return (attributes.get(s));
        }
    }

    public Enumeration getAttributeNames() {
        synchronized (attributes) {
            return (new Enumerator(attributes.entrySet()));
        }
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    public int getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public ServletInputStream getInputStream() throws IOException {
        if (reader != null) {
            throw new IllegalStateException("getInputStream has been called");
        }
        if (stream == null) {
            stream = createInputStream();
        }
        return stream;
    }

    public String getParameter(String s) {
        parseParameters();
        String[] values = (String[]) parameters.get(s);
        return values == null ? null : values[0];
    }

    public Enumeration getParameterNames() {
        parseParameters();
        return new Enumerator(parameters.keySet());
    }

    public String[] getParameterValues(String s) {
        parseParameters();
        return (String[]) parameters.get(s);
    }

    public Map getParameterMap() {
        parseParameters();
        return this.parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        if (stream != null) {
            throw new IllegalStateException("getInputStream has been called.");
        }
        if (reader == null) {
            String encoding = getCharacterEncoding();
            if (encoding == null) {
                encoding = "ISO-8859-1";
            }
            InputStreamReader isr = new InputStreamReader(createInputStream(), encoding);
            reader = new BufferedReader(isr);
        }
        return reader;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String s, Object o) {

    }

    public void removeAttribute(String s) {

    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    public String getRealPath(String s) {
        return null;
    }
}
