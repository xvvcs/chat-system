package main.chatsystem.Viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.chatsystem.Model.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel{
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
            model.login(userName.getName(), password.get());  // model login method may be String instead of void (Olivier example)

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
}
