package com.busekylin.web.server;

public interface WebServer {
    void start(int port, WebAction action);
}
