package main.chatsystem.Client;

import java.io.IOException;
import java.beans.PropertyChangeListener;

public interface ChatClient {
    void disconnect() throws IOException;
    void login(String username, String password);
    void sendMessage(String message, Person person);
    void addPropertyChangeListener(PropertyChangeListener listener);
    void receiveBroadcast(Object object);
}
