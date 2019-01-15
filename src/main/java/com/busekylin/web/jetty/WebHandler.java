package com.busekylin.web.jetty;

import com.busekylin.web.server.WebAction;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


public class WebHandler extends AbstractHandler {
    private WebAction action;

    public WebHandler(WebAction action) {
        this.action = action;
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        try {
            action.action(new JettyRequest(request), new JettyResponse(httpServletResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
