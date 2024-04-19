package main.chatsystem.View;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import main.chatsystem.Model.Counter;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.PeopleLog;
import main.chatsystem.Viewmodel.ChatViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatViewController implements PropertyChangeListener {
    @FXML private Label usernameLabel;
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private Button informationButton;
    @FXML private Button disconnectButton;
    @FXML private ListView<Message> chatArea;
    private ViewHandler viewHandler;
    private ChatViewModel chatViewModel;
    private Region root;
    private int size;

    public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root)
    {

        this.viewHandler = viewHandler;
        this.chatViewModel = chatViewModel;
        this.root = root;
        this.size = 0;


        chatViewModel.bindMessage(messageField.textProperty());
        chatViewModel.bindMessageList(chatArea.itemsProperty());

        chatViewModel.addPropertyChangeListener(evt -> {
            if ("UserLoggedIn".equals(evt.getPropertyName())) {
                usernameLabel.setText(chatViewModel.getNickname());
            }
        });

        this.chatViewModel.addPropertyChangeListener(this);
    }
    @FXML public void onSend()
    {
        chatViewModel.sendMessage();
        messageField.setText("");
    }
    @FXML public void onDisconnect()
    {
        chatViewModel.disconnect();
        viewHandler.closeView();
    }
    @FXML public void onInformation()
    {
        chatViewModel.addMessage(String.valueOf(size));
    }
    public Region getRoot(){
        return root;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("UserCount")) {
            this.size = (int) evt.getNewValue();
        }
    }
}
