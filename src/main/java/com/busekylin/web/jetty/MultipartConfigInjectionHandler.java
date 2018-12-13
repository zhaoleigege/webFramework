package com.busekylin.web.jetty;

import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.MultiPartFormInputStream;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MultipartConfigInjectionHandler extends HandlerWrapper {
    private static final String MULTIPART_FORMDATA_TYPE = "multipart/form-data";

    private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement(
            System.getProperty("java.io.tmpdir"));

    private static boolean isMultipartRequest(ServletRequest request) {
        return request.getContentType() != null
                && request.getContentType().startsWith(MULTIPART_FORMDATA_TYPE);
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean multipartRequest = HttpMethod.POST.is(request.getMethod())
                && isMultipartRequest(request);
        if (multipartRequest) {
            enableMultipartSupport(request);
        }

        try {
            super.handle(target, baseRequest, request, response);
        } finally {
            if (multipartRequest) {
                String MULTIPART = "org.eclipse.jetty.servlet.MultiPartFile.multiPartInputStream";
                MultiPartFormInputStream multipartInputStream = (MultiPartFormInputStream) request.getAttribute(MULTIPART);
                if (multipartInputStream != null) {
                    multipartInputStream.deleteParts();
                }
            }
        }
    }

    private static void enableMultipartSupport(HttpServletRequest request) {
        request.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
    }
}
