module main.chatsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens main.chatsystem to javafx.fxml;
    exports main.chatsystem;
}