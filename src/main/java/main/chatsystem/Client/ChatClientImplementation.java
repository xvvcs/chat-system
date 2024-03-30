package main.chatsystem.Client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.User;
import main.chatsystem.Server.StreamsFactory;
import main.chatsystem.Server.UDPBroadcaster;

import javax.xml.transform.Result;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientImplementation implements ChatClient {
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final Gson gson;
    private final PropertyChangeSupport support;
    private final MessageListener listener;


    public ChatClientImplementation(String host, int port) throws IOException{
        socket = new Socket(host,port);
        writer = StreamsFactory.createWriter(socket);
        reader = StreamsFactory.createReader(socket);
        gson = new Gson();

        support = new PropertyChangeSupport(this);
        
         listener = new MessageListener(this, "230.0.0.0" , 8888);
         Thread thread = new Thread(listener);
         thread.start();
    }

    @Override
    public void disconnect() throws IOException {
        writer.println("Disconnect");
        writer.flush();
        String reply = reader.readLine();
        if (!reply.equals("Disconnected")) {
            throw new IOException("Protocol failure");
        }

        socket.close();
    }

    @Override
    public boolean login(String username, String password) throws IOException{
        writer.println("connect");
        writer.flush();
        String reply = reader.readLine();
        if (!reply.equals("login required")) {
            throw new IOException("Protocol failure");
        }
        User userLogin = new User(username, password);
        String loginJSON = gson.toJson(userLogin);
        writer.println(loginJSON);
        writer.flush();
        reply = reader.readLine();

        addUser(userLogin);

        return reply.equals("Approved");
    }

    @Override
    public void sendMessage(String messageContent, User user) throws IOException{

        writer.println("Send message");
        writer.flush();

        if (user == null){
            throw new IllegalArgumentException("Error while sending message: user is null");
        }
        if (messageContent == null){
            throw new IllegalArgumentException("Error while sending message: message is empty");
        }

        Message message = new Message(messageContent);

        String messageJSON = gson.toJson(message + "from: " + user.getNickname() + " from ip: " + socket.getInetAddress());
        writer.println(messageJSON);
        writer.flush();

        this.support.firePropertyChange("MessageSent", null, message.getMessage());
    }

    @Override
    public void addUser(User user) throws IOException {
        String userJSON = gson.toJson( user + "joined chat with ip: " + socket.getInetAddress());

        writer.println(userJSON);
        writer.flush();


       support.firePropertyChange("UserAdded", null, user.getNickname());
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void receiveBroadcast(String message) {
        try {
            // Print the received message for debugging
            System.out.println("Received message: " + message);

            // Attempt to parse the message as JSON
            Message messageObject = gson.fromJson(message, Message.class);

            // If parsing succeeds, fire property change event
            support.firePropertyChange("result", null, messageObject);
        } catch (JsonSyntaxException e) {
            // If parsing fails, print the error and handle accordingly
            e.printStackTrace();
            // Add your error handling logic here
        }
    }
}
