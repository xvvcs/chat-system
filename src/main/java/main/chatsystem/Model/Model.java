package main.chatsystem.Model;

import java.beans.PropertyChangeListener;
import java.io.IOException;

public interface Model {
    void disconnect() throws IOException;
    void login(String username, String password);
    void sendMessage(String message, User user);
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
}
