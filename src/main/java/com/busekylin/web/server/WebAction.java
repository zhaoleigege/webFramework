package com.busekylin.web.server;

import com.busekylin.web.server.http.request.HttpRequest;
import com.busekylin.web.server.http.response.HttpResponse;
import com.busekylin.web.util.memoryupload.MemoryMultiPartParse;

import java.io.*;

public class WebAction {
    public void action(HttpRequest request, HttpResponse response) throws IOException {

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
