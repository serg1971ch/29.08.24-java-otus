package ru.otus;

import java.util.Map;

class HttpRequest {
    String method;
    String uri;
    Map<String, String> headers;
    Map<String, String> parameters;

    HttpRequest(String method, String uri, Map<String, String> headers, Map<String, String> parameters) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.parameters = parameters;
    }
}
