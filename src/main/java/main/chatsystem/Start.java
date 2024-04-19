package main.chatsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import main.chatsystem.Client.ChatClient;
import main.chatsystem.Client.ChatClientImplementation;
import main.chatsystem.Model.Model;
import main.chatsystem.Model.ModelManager;
import main.chatsystem.View.ViewHandler;
import main.chatsystem.Viewmodel.ViewModelFactory;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ChatClient client = new ChatClientImplementation("localhost", 5679);
        Model model = new ModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);
    }
}
