package com.busekylin.web.server.http.response;

import java.io.IOException;
import java.io.PrintWriter;

public interface HttpResponse {
    void setStatus(int status);

    PrintWriter getWriter() throws IOException;

    void setContentType(String contentType);

    void setContentLength(int length);

    void setContentLengthLong(long length);
}
