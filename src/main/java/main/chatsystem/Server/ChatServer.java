package main.chatsystem.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String args[]) throws IOException
    {
        final int PORT = 8080;
        System.out.println("Starting Server...");
        System.out.println("Server IP: " + InetAddress.getLocalHost().getHostAddress());

        // Creating server socket
        ServerSocket serverSocket = new ServerSocket(PORT);
        UDPBroadcaster broadcaster = new UDPBroadcaster("230.0.0.0",8888);
        while(true)
        {
            System.out.println("Waiting for a client...");
            // Waiting for contacting with the client
            Socket socket = serverSocket.accept();
            ChatCommunicator communicator = new ChatCommunicator(socket, broadcaster);
            Thread thread = new Thread(communicator);
            thread.start();
        }
    }
}
