module main.chatsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.chatsystem to javafx.fxml;
    exports main.chatsystem;
}