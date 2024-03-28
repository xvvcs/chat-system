package main.chatsystem.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import main.chatsystem.Viewmodel.ViewModelFactory;

import java.io.IOError;
import java.io.IOException;

public class ViewFactory { // Oliwier zrobił z tego Singleton, ale nie wiem czy to potrzebne
    public static final String LOGIN = "login";
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private LoginViewController loginViewController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.loginViewController = null;
    }

    public Region loadStartView(){
        if(loginViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main.chatsystem/StartView.fxml"));
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


    public Region loadView(String id){
        return switch (id){
            case LOGIN -> loadStartView();
            default -> throw new IllegalArgumentException("Unknow view: " + id);

        };
    }

}
