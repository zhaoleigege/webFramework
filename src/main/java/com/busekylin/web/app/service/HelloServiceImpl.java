package com.busekylin.web.app.service;

import com.busekylin.web.ioc.annotations.Component;

@Component
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
