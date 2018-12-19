package com.busekylin.web.server.http.request;

import java.io.InputStream;

public interface Part {
    String getName();

    String getFileName();

    String getValue();

    InputStream getFile();

    String getContentType();
}
