package ru.otus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser { //Выделил парсер в отдельный класс

    public static HttpRequest parseRequest(BufferedReader reader) throws IOException {
        String line;
        StringBuilder requestLine = new StringBuilder();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        StringBuilder body = new StringBuilder();

        // Читаем первую строку (метод, URI, версия HTTP)
        if ((line = reader.readLine()) == null) {
            return null; // Неверный запрос
        }
        requestLine.append(line);

        // Читаем заголовки
        while (!(line = reader.readLine()).isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex == -1) {
                // Неверный заголовок
                return null;
            }
            String headerName = line.substring(0, colonIndex).trim();
            String headerValue = line.substring(colonIndex + 1).trim();
            headers.put(headerName, headerValue);
        }

        // Извлекаем метод и URI из первой строки
        String[] requestLineParts = requestLine.toString().split(" ");
        if (requestLineParts.length != 3) {
            return null; // Неверный формат первой строки
        }
        String method = requestLineParts[0];
        String uri = requestLineParts[1];


        // Извлекаем параметры из URI (простой парсинг)
        int questionMarkIndex = uri.indexOf('?');
        if (questionMarkIndex != -1) {
            String queryString = uri.substring(questionMarkIndex + 1);
            String[] paramsArray = queryString.split("&");
            for (String param : paramsArray) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
            uri = uri.substring(0, questionMarkIndex); // Убираем параметры из URI
        }


        // Читаем тело запроса (если есть) - это упрощенное чтение, для больших тел нужно использовать потоки
        if(headers.containsKey("Content-Length")){
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] buffer = new char[contentLength];
            reader.read(buffer, 0, contentLength);
            body.append(buffer);
        }

        return new HttpRequest(method, uri, headers, params);
    }
}