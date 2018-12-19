package com.busekylin.web.server.http.request;

import java.io.IOException;
import java.util.Map;

public interface MultiPartParse {
    Map<String, Part> getParams() throws IOException;
}
