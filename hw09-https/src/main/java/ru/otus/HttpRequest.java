package ru.otus;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
class HttpRequest {
    String method;
    String uri;
    private Map<String, String> headers;
    private Map<String, String> parameters;

    HttpRequest(String method, String uri, Map<String, String> headers, Map<String, String> parameters) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.parameters = parameters;
    }

    public HttpRequest(String method, String uri, Map<String, String> headers, String string) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.parameters = new HashMap<>();
        this.parameters.put(string, string);
    }
}
