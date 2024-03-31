package main.chatsystem.Viewmodel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.chatsystem.Model.Model;
import main.chatsystem.Model.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel implements PropertyChangeListener{
    private final Model model;
    private final StringProperty userName;
    private final StringProperty password;
    private StringProperty message;


    private final PropertyChangeSupport support;

    public LoginViewModel(Model model)
    {
        this.model = model;
        this.userName = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
        this.support = new PropertyChangeSupport(this);
    }
    public void login()
    {
        try
        {
            model.login(userName.get(), password.get());  // model login method may be String instead of void (Olivier example)

        }
        catch (Exception e)
        {
            message.setValue(e.getMessage());
        }
    }
    public void bindUsername(StringProperty property)
    {
        userName.bindBidirectional(property);
    }
    public void bindPassword(StringProperty property)
    {
        password.bindBidirectional(property);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if ("UserLoggedIn".equals(evt.getPropertyName())){
                support.firePropertyChange("UserLoggedIn", null, evt.getNewValue());
            }
        });
    }
}
