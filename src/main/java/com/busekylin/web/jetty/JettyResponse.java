package com.busekylin.web.jetty;

import com.busekylin.web.server.http.response.HttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JettyResponse implements HttpResponse {
    private HttpServletResponse httpServletResponse;

    public JettyResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void setStatus(int status) {
        httpServletResponse.setStatus(status);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return httpServletResponse.getWriter();
    }

    @Override
    public void setContentType(String contentType) {
        httpServletResponse.setContentType(contentType);
    }

    @Override
    public void setContentLength(int length) {
        httpServletResponse.setContentLength(length);
    }

    @Override
    public void setContentLengthLong(long length) {
        httpServletResponse.setContentLengthLong(length);
    }
}
