package main.chatsystem.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String args[]) throws IOException
    {

        System.out.println("S   tarting Server...");
        System.out.println("Server IP: " + InetAddress.getLocalHost().getHostAddress() + " with port: 5678");

        // Creating server socket
        ServerSocket serverSocket = new ServerSocket(5678);
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
