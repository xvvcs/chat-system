package main.chatsystem.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPBroadcaster {
    private final InetAddress group;
    private final int port;

    public UDPBroadcaster(String groupAddress, int port) throws IOException {
        this.group = InetAddress.getByName(groupAddress);
        this.port = port;
    }

    public synchronized void broadcast(String message) throws IOException {
        try(DatagramSocket socket = new DatagramSocket()) {
            byte[] content = message.getBytes();
            DatagramPacket packet = new DatagramPacket(content, content.length, group, port);
            socket.send(packet);
        }
    }
}