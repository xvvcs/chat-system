package main.chatsystem.View;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import main.chatsystem.Viewmodel.LoginViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginViewController implements PropertyChangeListener {
    @FXML private TextField userNameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;
    private ViewHandler viewHandler;
    private LoginViewModel loginViewModel;
    private Region root;

    public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.loginViewModel = loginViewModel;
        this.root = root;

        loginViewModel.addPropertyChangeListener(this);
        loginViewModel.bindUsername(userNameField.textProperty());
        loginViewModel.bindPassword(passwordField.textProperty());
    }
    @FXML public void onStart(){
        if(!userNameField.getText().isEmpty() || !passwordField.getText().isEmpty())
        {
            viewHandler.openView(ViewFactory.CHAT);
            loginViewModel.login();
        }
        else
        {
            errorLabel.setText("FILL OUT THE TEXT FIELDS");
            errorLabel.setVisible(true);
        }
    }
    public Region getRoot(){
        return root;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if(evt.getPropertyName().equals("UserLoggedIn"));
            viewHandler.openView(ViewFactory.CHAT);
        });
    }
}
