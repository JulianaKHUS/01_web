package ru.netology;

import java.io.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService executorService;
    private final Map<String, Map<String, Handler>> handlers;

    public Server() {
        this.executorService = Executors.newFixedThreadPool(64);
        this.handlers = new ConcurrentHashMap<>();
    }

    public void start(int port) {
        try (var serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                var socket = serverSocket.accept();
                executorService.submit(() -> handleClient(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public void addHandler(String method, String path, Handler handler) {
        handlers.computeIfAbsent(method, k -> new ConcurrentHashMap<>()).put(path, handler);
        //if (!handlers.containsKey(method)) {
        //   handlers.put(method, new ConcurrentHashMap<>());
        //  }

        //var methods = handlers.get(method);

        // methods.put(path. handler);
    }

    private void handleClient(Socket socket) {
        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new BufferedOutputStream(socket.getOutputStream())) {
            Request request = Request.parse(in);

            String method = request.getMethod();
            String path = request.getPath();

            Handler handler = handlers.getOrDefault(method, Map.of()).get(path);
            if (handler != null) {
                try {
                    handler.handle(request, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                NotFound(out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void notFound(BufferedOutputStream out) throws IOException {
        out.write(("HTTP/1.1 404 Not Found\r\n" +
                "Content-Lenght: 0\r\n" +
                "Connection: close\r\n\r\n").getBytes());
        out.flush();
    }
}
