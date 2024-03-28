package main.chatsystem.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import main.chatsystem.Model.User;

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

    @Override
    public void run() {
        Gson gson = new Gson();
        try {
            while (true) {
                BufferedReader reader = StreamsFactory.createReader(socket);
                PrintWriter writer = StreamsFactory.createWriter(socket);
                String method = reader.readLine();
                if (!method.equals("connect")) {
                    writer.println("Disconnected");
                    writer.flush();
                    break;
                }
                writer.println("login required");
                writer.flush();
                String loginData = reader.readLine();
                try{
                    User login = gson.fromJson(loginData,User.class);
                    if(!login.getNickname().isEmpty() || !login.getPassword().isEmpty()) {
                        writer.println("Approved");
                        System.out.println("Logged successfully");
                        writer.flush();
                    }
                    else
                    {
                        writer.println("Disapproved");
                        System.out.println("Logging procedure failure");
                        writer.flush();
                    }
                }
                catch (JsonSyntaxException e)
                {
                    writer.println("Disconnected");
                    writer.flush();
                    e.printStackTrace();
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
