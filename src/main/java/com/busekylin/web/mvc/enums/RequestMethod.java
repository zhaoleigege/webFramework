package com.busekylin.web.mvc.enums;

public enum RequestMethod {
    GET("GET"),

    HEAD("HEAD"),

    POST("POST"),

    PUT("PUT"),

    DELETE("DELETE");

    private String value;

    RequestMethod(String value) {
        this.value = value.toUpperCase();
    }

    public String getValue() {
        return value;
    }

    public static RequestMethod getEnum(String value) {
        for (RequestMethod method : RequestMethod.values()) {
            if (method.getValue().equals(value))
                return method;
        }

        return null;
    }
}
