package main.chatsystem.Server;

import java.io.*;
import java.net.Socket;

public class StreamsFactory {
    public static PrintWriter createWriter(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        return new PrintWriter(outputStream);
    }

    public static BufferedReader createReader(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }

    public static ObjectOutputStream createOutputStream(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        return new ObjectOutputStream(outputStream);
    }

    public static ObjectInputStream createOutputInputStream(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        return new ObjectInputStream(inputStream);
    }
}