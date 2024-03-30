package main.chatsystem.View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import main.chatsystem.Model.Message;
import main.chatsystem.Viewmodel.LoginViewModel;

public class ChatViewController {
    @FXML private Label usernameLabel;
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private Button informationButton;
    @FXML private Button disconnectButton;
    @FXML private ListView<Message> chatArea;
    private ViewHandler viewHandler;
    private ChatViewModel chatViewModel;
    private Region root;
    public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root)
    {

        this.viewHandler = viewHandler;
        this.chatViewModel = chatViewModel;
        this.root = root;

        // loginViewModel.addPropertyChangeListener(this);
        //
        // loginViewModel.bindUsername(userNameField.textProperty());
        // loginViewModel.bindPassword(passwordField.textProperty());
    }
    @FXML public void onSend()
    {
        chatViewModel.sendMessage();
    }
    @FXML public void onDisconnect()
    {
        chatViewModel.disconnect();
    }
    @FXML public void onInformation()
    {
        // ???
    }
    public Region getRoot(){
        return root;
    }
}
