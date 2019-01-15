package com.busekylin.web.app.service;

import com.busekylin.web.ioc.annotations.Component;
import com.busekylin.web.mvc.annotations.Controller;
import com.busekylin.web.mvc.annotations.Param;
import com.busekylin.web.mvc.annotations.RequestMapping;

@Component
@Controller
public class WebController {
    @RequestMapping("/test")
    public String test(@Param("name") String name, @Param("age") Integer age) {
        return "访问test: " + name + ", " + age;
    }
}
