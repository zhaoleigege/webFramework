package com.busekylin.web;

import com.busekylin.web.app.web.WebController;
import com.busekylin.web.ioc.BeansPool;
import com.busekylin.web.ioc.Context;
import com.busekylin.web.mvc.RequestScanner;
import com.busekylin.web.server.WebAction;
import com.busekylin.web.server.WebServer;
import com.busekylin.web.jetty.JettyServer;

import java.lang.reflect.InvocationTargetException;

public class Web {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Context.init();
        RequestScanner.init();
        BeansPool.getInstance().allClass().forEach((k, v) -> System.out.println("class: " + k + ", object: " + v));

        WebController service = (WebController) Context.getBean(WebController.class);
        service.test();

        WebAction action = new WebAction();

        WebServer server = new JettyServer();

        server.start(89, action);
    }
}
