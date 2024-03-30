package main.chatsystem.Viewmodel;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.Model;
import main.chatsystem.Model.User;

import java.beans.PropertyChangeListener;

public class ChatViewModel implements PropertyChangeListener {
    private final Model model;
    private User user;
    private final SimpleListProperty<Message> messages;
    private final SimpleStringProperty message;
    private StringProperty error;
    public ChatViewModel(Model model)
    {
        this.model = model;
        this.user = null;
        this.error = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
        this.messages = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.model.addPropertyChangeListener(this);
    }
    public void sendMessage()
    {
        try
        {
            model.sendMessage(message.get(), user);
            message.set("");
        }
        catch (Exception e)
        {
            error.setValue(e.getMessage());
        }
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
    public void bindMessageList(StringProperty property)
    {
        messages.bindBidirectional(property);
    }
    public void bindMessage(StringProperty property)
    {
        message.bindBidirectional(property);
    }

}
