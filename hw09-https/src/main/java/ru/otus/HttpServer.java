package ru.otus;

import lombok.extern.java.Log;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log
public class HttpServer {
    private final ServerConfig config;
    private final ExecutorService executor;
    private boolean running = true;


    public HttpServer(ServerConfig config) {
        this.config = config;
        this.executor = Executors.newFixedThreadPool(config.poolSize);
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(config.port);
        System.out.println("Server started on port " + config.port);

        while (running) {
            Socket socket = serverSocket.accept();
            executor.submit(() -> handleRequest(socket));
        }
        serverSocket.close();
        executor.shutdown();
    }

    private void handleRequest(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

            HttpRequest request = parseRequest(reader);

            if (request == null) {
                sendError(writer, 500, "Failed to parse request");
                return;
            }

            if ("/shutdown".equals(request.uri) && "GET".equals(request.method)) {
                running = false;
                sendResponse(writer, 200, "Shutting down...");
                return;
            }

            sendResponse(writer, 200, "OK");
        } catch (IOException e) {
            try {
                PrintWriter writer = null;
                sendError(writer, 500, "Internal Server Error");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpRequest parseRequest(BufferedReader reader) throws IOException{
        HttpRequest request = HttpRequestParser.parseRequest(reader);
        log.info("Received request: " + request.method + " " + request.uri);
        return request;
    }


    public void sendResponse(PrintWriter writer, int statusCode, String message) {
        writer.println("HTTP/1.1 " + statusCode + " " + getStatusCodeMessage(statusCode));
        writer.println("Content-Type: text/plain");
        writer.println("Content-Length: " + message.length());
        writer.println();
        writer.println(message);
        writer.flush();
        log.info("Sending response: " + message + " to server");
        log.info("Sending response: " + statusCode + " " + getStatusCodeMessage(statusCode));
    }

    private void sendError(PrintWriter writer, int statusCode, String message) throws IOException {
        sendResponse(writer, statusCode, message);
    }

    private final Map<Integer, String> statusCodeMessages = Map.of(
            200, "OK",
            400, "Bad Request",
            404, "Not Found",
            500, "Internal Server Error",
            501, "Not Implemented"
    );

    private String getStatusCodeMessage(int statusCode) {
        return statusCodeMessages.getOrDefault(statusCode, "Unknown Status Code");
    }
}
