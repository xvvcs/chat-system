module main.chatsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    opens main.chatsystem to javafx.fxml;
    exports main.chatsystem;
}