package ru.netology;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final String method;
    private final String path;
    private final String headers;
    private final String body;
    private final List<NameValuePair> queryParams;
public Request(String requestLine, String headers, String body, List<NameValuePair> queryParams) {
    String[] parts = requestLine.split(" ");
    this.method = parts[0];
    this.path = parts[1];
    this.headers = headers;
    this.body = body;
    this.queryParams = queryParams;
}

    public String getMethod() {return method;}

    public String getPath() {return path;}

    public String getHeaders() {return headers;}

    public String getBody() {return body;}

public List<NameValuePair> getQueryParam(String name) {
return queryParams.stream()
        .filter(p -> p.getName().equals(name))
        .collect(Collectors.toList());
}
    public List<NameValuePair> getQueryParam() {
return queryParams;
    }
    public static Request parse(BufferedReader in) throws IOException {
    String requestLine = in.readLine();

    if (requestLine == null) {
        throw new IOException("Invalid request line");
    }


    var path = requestLine.split("");

    if (parts.lenght !=3) {
        throw new IOException("Invalid request line");

    }

    var path = parts[1];
        var params = URLEncodedUtils.parse(path, StandardCharsets.UTF_8);

        StringBuilder headers = new StringBuilder();
        String line;

        while (!(line = in.readLine()).isEmpty()) {
           headers.append(line).append("\r\n");
        }
        String body = in.toString();
        return new Request(requestLine, headers.toString(), body, params);

    }
}
