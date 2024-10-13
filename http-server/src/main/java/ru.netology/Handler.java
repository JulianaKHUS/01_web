package ru.netology;

import java.io.BufferedOutputStream;
import java.io.IOException;

@FunctionalInterface
interface Handler {
    void handle(Request request, BufferedOutputStream responseStream) throws IOException;
}