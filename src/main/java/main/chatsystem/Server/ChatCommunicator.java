package main.chatsystem.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import main.chatsystem.File.FileLog;
import main.chatsystem.Model.Counter;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.PeopleLog;
import main.chatsystem.Model.User;

public class ChatCommunicator implements Runnable {
    private final Socket socket;
    private final UDPBroadcaster broadcaster;
    private final Gson gson;
    private final Counter counter;

    private FileLog fileLog;
    private File file;
    private final PeopleLog peopleLog;
    private String username;

    public ChatCommunicator(Socket socket, UDPBroadcaster broadcaster) {
        this.socket = socket;
        this.broadcaster = broadcaster;
        this.gson = new Gson();

        this.peopleLog = PeopleLog.getInstance();
        this.file = new File("src/main/java/main/chatsystem/File/ChatLog");
        this.fileLog = FileLog.getInstance(file);
        this.counter = Counter.getInstance();
    }

    @Override
    public synchronized void run() {
        Gson gson = new Gson();
        try {
            while (true) {
                BufferedReader reader = StreamsFactory.createReader(socket);
                PrintWriter writer = StreamsFactory.createWriter(socket);
                String firstReply = reader.readLine();

                if (firstReply.equals("connect")) {
                    System.out.println("CONNECTED");
                    fileLog.log("User from " + socket.getInetAddress() + " has connected.");
                    writer.println("login required");
                    writer.flush();
                    String loginData = reader.readLine();
                    try {
                        User login = gson.fromJson(loginData, User.class);
                        username = login.nickname();
                        if (!login.nickname().isEmpty() || !login.password().isEmpty())
                        {
                            writer.println("Approved");
                            System.out.println("Logged successfully");
                            writer.flush();
                            counter.increase();

                            Message message = new Message(username + " has joined the chat");
                            String joinBroadcast = gson.toJson("M" + message);
                            broadcaster.broadcast(joinBroadcast);
                            String jsonCounter = gson.toJson(counter.getCounter());
                            broadcaster.broadcast("C" + jsonCounter);

                            fileLog.log(socket.getInetAddress() + " User " + login.nickname() + " has joined the chat.");
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
                if (firstReply.equals("Disconnect"))
                {

                    writer.println("Disconnected");
                    writer.flush();
                    counter.decrease();

                    String leftBroadcast = reader.readLine();
                    broadcaster.broadcast("M" + leftBroadcast);

                    String jsonCounter = gson.toJson(counter.getCounter());
                    broadcaster.broadcast("C" + jsonCounter);

                    fileLog.log(socket.getInetAddress() + " User " + username + " has left the chat.");
                    break;
                }
                if (firstReply.equals("Send message")) {
                    writer.println("Provide message content");
                    writer.flush();
                    String messageContent = reader.readLine();
                    Message jSonMessage = gson.fromJson(messageContent, Message.class);
                    broadcaster.broadcast(messageContent);

                    fileLog.log(socket.getInetAddress() + " User " + username + " has sent a message: "+jSonMessage.message());

                }
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
}
