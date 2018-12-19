package com.busekylin.web.util.memoryupload;

import com.busekylin.web.server.http.request.Part;

import java.io.InputStream;

public class MemoryPart implements Part {
    private String name;
    private String fileName;
    private String value;
    private InputStream file;
    private String contentType;

    public void setName(String name) {
        this.name = name;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public InputStream getFile() {
        return this.file;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }
}
