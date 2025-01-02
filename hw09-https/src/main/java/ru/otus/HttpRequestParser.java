package ru.otus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class HttpRequestParser { //Выделил парсер в отдельный класс

    private static final ThreadLocal<char[]> buffer = ThreadLocal.withInitial(() -> new char[8192]); // Example size

    public static HttpRequest parseRequest(InputStream inputStream) throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Map<String, String> headers = new HashMap<>();
        StringBuilder body = new StringBuilder();

        if ((line = reader.readLine()) == null) {
            return null;
        }

        int firstSpaceIndex = line.indexOf(' ');
        int secondSpaceIndex = line.indexOf(' ', firstSpaceIndex + 1);

        if (firstSpaceIndex == -1 || secondSpaceIndex == -1) {
            return null;
        }

        String method = line.substring(0, firstSpaceIndex);
        String uri = line.substring(firstSpaceIndex + 1, secondSpaceIndex);

        while (!(line = reader.readLine()).isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex == -1) {
                // Invalid header
                return null;
            }
            String headerName = line.substring(0, colonIndex).trim();
            String headerValue = line.substring(colonIndex + 1).trim();
            headers.put(headerName, headerValue);
        }

        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] readBuffer = buffer.get();
            if (readBuffer.length < contentLength) {
                readBuffer = new char[contentLength];
                buffer.set(readBuffer);
            }
            reader.read(readBuffer, 0, contentLength);
            body.append(readBuffer, 0, contentLength);
        }
        return new HttpRequest(method, uri, headers, body.toString());
    }
}