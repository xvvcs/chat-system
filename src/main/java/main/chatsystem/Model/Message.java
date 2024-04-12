package main.chatsystem.Model;

import java.io.Serializable;

public record Message(String message) implements Serializable
{

    @Override public String toString()
    {
        return message;
    }
}
