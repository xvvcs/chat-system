package main.chatsystem.Client;

import com.google.gson.Gson;
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
        
         listener = new MessageListener(this, "230.0.0.0" , 888);
         Thread thead = new Thread(listener);
         thead.start();
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
    public void login(String username, String password) {

    }

    @Override
    public void sendMessage(String message, User user) {

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
