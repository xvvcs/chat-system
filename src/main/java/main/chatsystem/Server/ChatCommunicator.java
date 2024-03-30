package main.chatsystem.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import main.chatsystem.File.FileLog;
import main.chatsystem.Model.Model;
import main.chatsystem.Model.PeopleLog;
import main.chatsystem.Model.User;

public class ChatCommunicator implements Runnable {
    private final Socket socket;
    private final UDPBroadcaster broadcaster;
    private final Gson gson;

    private FileLog fileLog;
    private File file;
    private final PeopleLog peopleLog;
    public ChatCommunicator(Socket socket, UDPBroadcaster broadcaster){
        this.socket = socket;
        this.broadcaster = broadcaster;
        this.gson =  new Gson();

        this.peopleLog = PeopleLog.getInstance();
        this.file = new File("src/main/java/main/chatsystem/File/ChatLog");
        this.fileLog = FileLog.getInstance(file);
    }

    @Override
    public synchronized void run() {
        Gson gson = new Gson();
        try {
            while (true) {
                BufferedReader reader = StreamsFactory.createReader(socket);
                PrintWriter writer = StreamsFactory.createWriter(socket);
                String method = reader.readLine();
                System.out.println("PROBUHJE POLACZYC");
                if (method == null || !method.equals("connect")) {
                    writer.println("Disconnected");
                    writer.flush();
                    break;
                }
                System.out.println("POLACZYLO");
                fileLog.log("User " + socket.getInetAddress() + " has connected.");
                writer.println("login required");
                writer.flush();
                String loginData = reader.readLine();
                try{
                    User login = gson.fromJson(loginData,User.class);
                    if(!login.getNickname().isEmpty() || !login.getPassword().isEmpty()) {
                        writer.println("Approved");
                        System.out.println("Logged successfully");
                        writer.flush();
                        broadcaster.broadcast(loginData);

                        //dodaje ziuta do Userow - PEWNIE NIE DZIALA
                        peopleLog.addUser(login);

                        fileLog.log(socket.getInetAddress()+" User " + login.getNickname() + " has joined the chat."); // <<<<< TU POWINIEN BYC MESSAGE WYSLANY JSONEM
                    }
                    else
                    {
                        writer.println("Disapproved");
                        System.out.println("Logging procedure failure");
                        writer.flush();
                    }
                    String reply = reader.readLine();
                    if(reply.equals("Disconnect"))
                    {
                        writer.println("Disconnected");
                        writer.flush();
                        broadcaster.broadcast(login.getNickname() + " has disconnected.");
                        fileLog.log(socket.getInetAddress()+" User " + login.getNickname() + " has left the chat.");
                        break;
                    }
                    else if(reply.equals("Send message")) // Może się to zmienić podczas implementacji Java FX
                    {
                        System.out.println("Message from client: " + reply);
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
