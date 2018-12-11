package com.busekylin.web.jetty;

import com.busekylin.web.server.http.request.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JettyRequest implements HttpRequest {
    private HttpServletRequest httpServletRequest;
    private Map<String, List<String>> headers;

    public JettyRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;

        headers = new HashMap<>();
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();

            headers.put(header, Collections.list(httpServletRequest.getHeaders(header)));
        }
    }

    @Override
    public String getMethod() {
        return httpServletRequest.getMethod();
    }

    @Override
    public String getUrl() {
        return httpServletRequest.getPathInfo();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public Map<String, String[]> getParams() {
        return httpServletRequest.getParameterMap();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return httpServletRequest.getReader();
    }

    @Override
    public InputStream getStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    @Override
    public String getContentType() {
        return httpServletRequest.getContentType();
    }
}
