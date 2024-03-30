module main.chatsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    opens main.chatsystem to javafx.fxml;
    opens main.chatsystem.View to javafx.fxml;

    opens main.chatsystemResource to javafx.fxml;

    opens main.chatsystem.Model to javafx.base;
    opens main.chatsystem.Viewmodel to javafx.base;

    exports main.chatsystem;
}