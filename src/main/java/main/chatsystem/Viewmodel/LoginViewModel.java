package main.chatsystem.Viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.chatsystem.Model.Model;

import java.beans.PropertyChangeSupport;

public class LoginViewModel {
    private final Model model;
    private final StringProperty userName;
    private final StringProperty password;
    private StringProperty message;


    //private final PropertyChangeSupport support;  idk czy potrzeba tu supportu

    public LoginViewModel(Model model)
    {
        this.model = model;
        this.userName = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
        //this.support = new PropertyChangeSupport(this); idk czy potrzeba tu supportu
    }
    public void login()
    {
        try
        {
            model.login(userName.getName(), password.get());
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
