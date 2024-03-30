package main.chatsystem.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import main.chatsystem.Viewmodel.ViewModelFactory;

import java.io.IOError;
import java.io.IOException;

public class ViewFactory { // Oliwier zrobił z tego Singleton, ale nie wiem czy to potrzebne
    public static final String LOGIN = "login";
    public static final String CHAT = "chat";
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private LoginViewController loginViewController;
    private ChatViewController chatViewController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.loginViewController = null;
        this.chatViewController = null;
    }

    public Region loadStartView(){
        if(loginViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/chatsystemResource/LoginView.fxml"));
            try {
                Region root = loader.load();
                loginViewController = loader.getController();
                loginViewController.init(viewHandler, viewModelFactory.getLoginViewModel(), root);
                return root;
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        return loginViewController.getRoot();
    }

    public Region loadChatView(){
        if(chatViewController == null){ // braKUJE VIEWMODELU
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main/chatsystemResource/ChatView.fxml"));
            try {
                Region root = loader.load();
                chatViewController = loader.getController(); // braKUJE VIEWMODELU
                chatViewController.init(viewHandler, viewModelFactory.getChatViewModel(), root); // braKUJE VIEWMODELU
                return root;
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        return chatViewController.getRoot(); // braKUJE VIEWMODELU
    }


    public Region loadView(String id){
        return switch (id){
            case LOGIN -> loadStartView();
            case CHAT -> loadChatView();
            default -> throw new IllegalArgumentException("Unknow view: " + id);

        };
    }

}
