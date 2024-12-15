package ru.otus;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerConfig config = new ServerConfig("hw09-https/src/main/resources/server.properties");
        HttpServer server = new HttpServer(config);
        server.start();
    }
}