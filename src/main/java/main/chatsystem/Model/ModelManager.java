
package main.chatsystem.Model;

import javafx.application.Platform;
import main.chatsystem.Client.ChatClient;
import main.chatsystem.Client.ChatClientImplementation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Model, PropertyChangeListener {
    private final PropertyChangeSupport support;
    private final ChatClient client;

    public ModelManager() throws IOException {
        this.client = new ChatClientImplementation("localhost",5678);
        this.support = new PropertyChangeSupport(this);
        this.client.addPropertyChangeListener(this);
    }

    @Override
    public void disconnect() throws IOException {
        try{
            this.client.disconnect();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public void login(String username, String password) throws IOException {

        client.login(username, password);
    }

    @Override
    public void sendMessage(String content, User user) throws IOException, InterruptedException {
        Message message = new Message(content);
        client.sendMessage(content, user);
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
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if ("UserAdded".equals(evt.getPropertyName())) {
                support.firePropertyChange("PersonAdded", null, evt.getNewValue());
            } else if ("MessageSent".equals(evt.getPropertyName())) {
                support.firePropertyChange("MessageSent", null, evt.getNewValue());
            }
        });
    }

}
