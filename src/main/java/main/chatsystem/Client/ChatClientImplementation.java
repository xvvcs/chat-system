package main.chatsystem.Client;

import com.google.gson.Gson;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.User;
import main.chatsystem.Server.StreamsFactory;

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
        String loginJSON = gson.toJson(userLogin + "logged in with ip: " + socket.getInetAddress());
        writer.println(loginJSON);
        writer.flush();
        reply = reader.readLine();

        addUser(userLogin);

        return reply.equals("Approved");
    }

    @Override
    public void sendMessage(String messageContent, User user) throws IOException{

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

        this.support.firePropertyChange("Message sent", null, message.getMessage());
    }

    @Override
    public void addUser(User user) throws IOException {
        String userJSON = gson.toJson( user + "joined chat with ip: " + socket.getInetAddress());

        writer.println(userJSON);
        writer.flush();

        this.support.firePropertyChange("User added", null, user.getNickname());
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
    public void receiveBroadcast(Object object) {
        Result result = gson.fromJson(object.toString(), Result.class);
        support.firePropertyChange("result", null, result);
    }
}
