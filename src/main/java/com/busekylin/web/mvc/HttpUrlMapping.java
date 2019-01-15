package com.busekylin.web.mvc;

import com.busekylin.web.mvc.enums.RequestMethod;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

@Builder
@Data
public class HttpUrlMapping {
    private String url;
    private RequestMethod requestMethod;

    private Class clazz;
    private Method method;
}
