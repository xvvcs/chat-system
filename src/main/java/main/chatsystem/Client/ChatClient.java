package main.chatsystem.Client;

import main.chatsystem.Model.User;

import java.io.IOException;
import java.beans.PropertyChangeListener;

public interface ChatClient {
    void disconnect() throws IOException;
    void login(String username, String password);
    void sendMessage(String message, User user);
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    void receiveBroadcast(Object object);
}
