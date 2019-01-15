package com.busekylin.web.mvc;

import com.busekylin.web.mvc.enums.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class HttpUrlMappingPool {
    private static class HttpUrlMappingPoolSingleton {
        private static final HttpUrlMappingPool INSTANCE = new HttpUrlMappingPool();
    }

    private HttpUrlMappingPool() {
        this.mappingList = new ArrayList<>();
    }

    public static HttpUrlMappingPool getInstance() {
        return HttpUrlMappingPoolSingleton.INSTANCE;
    }

    private List<HttpUrlMapping> mappingList;

    public void setMapping(HttpUrlMapping urlMapping) {
        mappingList.add(urlMapping);
    }

    public HttpUrlMapping getMapping(String url, RequestMethod requestMethod) {
        for (HttpUrlMapping mapping : mappingList) {
            if (mapping.getUrl().equals(url) && mapping.getRequestMethod() == requestMethod)
                return mapping;
        }

        return null;
    }
}
