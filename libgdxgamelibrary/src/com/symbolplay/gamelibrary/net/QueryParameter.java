package com.symbolplay.gamelibrary.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class QueryParameter {
    
    private final String name;
    private final String value;
    
    public QueryParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    
    public String getParameterString(String charset) throws UnsupportedEncodingException {
        return name + "=" + URLEncoder.encode(value, charset);
    }
}
