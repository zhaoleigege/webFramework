package com.busekylin.web.app.web;

import com.busekylin.web.app.service.HelloServiceImpl;
import com.busekylin.web.ioc.annotations.Component;
import com.busekylin.web.ioc.annotations.Inject;

@Component
public class WebController {
    @Inject
    private HelloServiceImpl helloService;

    public void test() {
        helloService.sayHello();
    }
}
