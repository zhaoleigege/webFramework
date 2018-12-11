package com.busekylin.web.server;

import com.busekylin.web.server.http.request.HttpRequest;
import com.busekylin.web.server.http.response.HttpResponse;

import java.io.IOException;

public class WebAction {
    public void action(HttpRequest request, HttpResponse response) {
        System.out.println(request.getContentType());
        String hello = "hello jetty";

        response.setContentLength(hello.length());
        try {
            response.getWriter().append(hello);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
