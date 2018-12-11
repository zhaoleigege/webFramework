package com.busekylin.web;

import com.busekylin.web.server.WebAction;
import com.busekylin.web.server.WebServer;
import com.busekylin.web.jetty.JettyServer;

public class Web {
    public static void main(String[] args) {
        WebAction action = new WebAction();

        WebServer server = new JettyServer();

        server.start(89, action);
    }
}
