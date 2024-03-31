package main.chatsystem.Viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.Model;
import main.chatsystem.Model.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChatViewModel implements PropertyChangeListener {
    private final Model model;
    private User user;
    private final ListProperty<Message> messages;
    private final SimpleStringProperty message;
    private StringProperty error;
    private final PropertyChangeSupport support;
    public ChatViewModel(Model model)
    {
        this.model = model;
        this.user = null;
        this.error = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
        this.messages = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.model.addPropertyChangeListener(this);
        this.model.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("UserLoggedIn")){
                user = (User) evt.getNewValue();
            }
        });
        this.support = new PropertyChangeSupport(this);
    }

    public String getNickname(){
        if(user!= null){
            return user.getNickname();
        }
        else{
            return "";
        }
    }
    public void sendMessage()
    {
        try
        {
            model.sendMessage(message.get(), user);
            messages.add(new Message(getNickname()+": "+message.get()));
            message.set("");
        }
        catch (Exception e)
        {
            error.setValue(e.getMessage());
        }
    }
    public void addMessage(String message){
        Message messageNew = new Message(message);
        messages.add(messageNew);
    }
    public void disconnect()
    {
        try
        {
            model.disconnect();
            message.set("");
        }
        catch (Exception e)
        {
            error.setValue(e.getMessage());
        }
    }
    public void bindMessageList(ObjectProperty<ObservableList<Message>> property)
    {
        messages.bindBidirectional(property);
    }

    public StringProperty errorProperty() {
        return error;
    }

    public void bindMessage(StringProperty property)
    {
        message.bindBidirectional(property);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (evt.getPropertyName().equals("UserLoggedIn")){
                user = (User) evt.getNewValue();
                support.firePropertyChange("UserLoggedIn", null, user);
            }
        });
    }

}
