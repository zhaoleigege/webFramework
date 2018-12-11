package com.busekylin.web.server.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface HttpRequest {
    String getMethod();

    String getUrl();

    Map<String, List<String>> getHeaders();

    Map<String, String[]> getParams();

    BufferedReader getReader() throws IOException;

    InputStream getStream() throws IOException;

    String getContentType();

}
