package main.chatsystem.Model;

import java.io.Serializable;

public class Message implements Serializable {
    private final String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
