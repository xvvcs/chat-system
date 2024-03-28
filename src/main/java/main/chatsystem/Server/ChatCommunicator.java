package main.chatsystem.Server;

import java.net.Socket;
import com.google.gson.GSON;

public class ChatCommunicator implements Runnable {
    private final Socket socket;
    private final UDPBroadcaster broadcaster;
    private final Gson gson;
    private final Model model; // nie wiadomo czy trzeba tu model
    public ChatCommunicator(Socket socket, UDPBroadcaster broadcaster){
        this.socket = socket;
        this.broadcaster = broadcaster;
        this.gson =  new Gson();
    }
}
