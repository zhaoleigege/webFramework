package com.busekylin.web.server;

import com.busekylin.web.ioc.BeansPool;
import com.busekylin.web.mvc.HttpUrlMapping;
import com.busekylin.web.mvc.HttpUrlMappingPool;
import com.busekylin.web.mvc.annotations.Param;
import com.busekylin.web.mvc.enums.RequestMethod;
import com.busekylin.web.server.http.request.HttpRequest;
import com.busekylin.web.server.http.response.HttpResponse;
import com.busekylin.web.util.memoryupload.MemoryMultiPartParse;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class WebAction {
    public void action(HttpRequest request, HttpResponse response) throws Exception {
        String url = request.getUrl();
        RequestMethod requestMethod = RequestMethod.getEnum(request.getMethod());

        HttpUrlMapping httpUrlMapping = HttpUrlMappingPool.getInstance().getMapping(url, requestMethod);

        if (httpUrlMapping == null) {
            throw new Exception("找不到对应的处理方法");
        }

        List<Object> params = new ArrayList<>();
        Method method = httpUrlMapping.getMethod();

        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Param.class) {
                    String[] paramArr = paramGetOrNull(request, ((Param) annotation).value());
                    Class paramClazz = parameter.getType();
                    if (paramClazz == String.class) {
                        params.add(paramArr[0]);
                    } else if (paramClazz == Integer.class) {
                        params.add(Integer.valueOf(paramArr[0]));
                    } else {
                        params.add(null);
                    }
                }
            }
        }

        params.forEach(item -> System.out.println(item));

        Object object = BeansPool.getInstance().getObject(httpUrlMapping.getClazz());
        Object result = method.invoke(object, params.toArray());

        String hello = result.toString();


        response.setContentLength(hello.getBytes(Charset.forName("UTF-8")).length);
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().append(hello);
    }

    private String[] paramGetOrNull(HttpRequest request, String name) {
        String[] result = request.getParams().get(name);
        return result == null ? new String[]{null} : result;
    }

    private void testAction(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getContentType().contains("multipart/form-data")) {
            new MemoryMultiPartParse(request.getStream(), request.getContentType().substring(request.getContentType().indexOf("boundary=") + 9))
                    .getParams().forEach((k, v) -> {
                System.out.println(k);
                System.out.println(v.getName());
                System.out.println(v.getValue());
                System.out.println(v.getFileName());
                System.out.println(v.getContentType());
                InputStream stream = v.getFile();

                if (stream != null) {
                    try {
                        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(v.getFileName()));
                        byte[] read = new byte[1024];
                        int hasRead;
                        while ((hasRead = stream.read(read)) != -1)
                            outputStream.write(read, 0, hasRead);

                        outputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        System.out.println(request.getParams());
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
