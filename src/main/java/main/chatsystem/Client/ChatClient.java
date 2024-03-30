package main.chatsystem.Client;

import main.chatsystem.Model.User;

import java.io.IOException;
import java.beans.PropertyChangeListener;

public interface ChatClient {
    void disconnect() throws IOException;
    boolean login(String username, String password) throws IOException;
    void sendMessage(String message, User user) throws IOException;

    void addUser(User user) throws IOException;
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    void receiveBroadcast(String message);
}
