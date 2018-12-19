package com.busekylin.web;

import com.busekylin.web.server.http.request.MultiPartParse;
import com.busekylin.web.util.memoryupload.MemoryMultiPartParse;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MultiPartParseTest {
    @Test
    public void parseTest() throws IOException {
        InputStream stream = new FileInputStream("....");

        MultiPartParse partParse = new MemoryMultiPartParse(stream, "--------------------------243514804779801432872874");

        partParse.getParams().forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v.getName());
            System.out.println(v.getValue());
            System.out.println(v.getFileName());
            System.out.println(v.getContentType());
            System.out.println(v.getFile());
        });
    }
}
