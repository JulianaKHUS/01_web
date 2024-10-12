package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class Request {
    private final String method;
    private final String path;
    private final String headers;
    private final String body;
public Request(String requestLine, String headers, String body) {
    String[] parts = requestLine.split(" ");
    this.method = parts[0];
    this.path = parts[1];
    this.headers = headers;
    this.body = body;
}

    public String getMethod() {return method;}

    public String getPath() {return path;}

    public String getHeaders() {return headers;}

    public String getBody() {return body;}

    public static Request parse(BufferedReader in) throws IOException {
    String requestLine = in.readLine();
        StringBuilder headers = new StringBuilder();
        String line;

        while (!(line = in.readLine()).isEmpty()) {
           headers.append(line).append("\r\n");
        }
        String body = in.toString();
        return new Request(requestLine, headers.toString(), body);

    }
}
