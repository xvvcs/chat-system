package main.chatsystem.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class PeopleLog implements Serializable {

    private static PeopleLog instance;
    private ArrayList<User> users;
    private ArrayList<String> nicknames;

    private PeopleLog(){
        this.users = new ArrayList<>();
        this.nicknames = new ArrayList<>();
    }

    public static synchronized PeopleLog getInstance(){
        if (instance == null)
        {
            instance = new PeopleLog();
        }
        return instance;
    }

    public synchronized void addUser(User user){
        if (!users.contains(user)) {
            users.add(user);
            nicknames.add(user.nickname());
        }
    }
    public synchronized int getSize()
    {
        return users.size();
    }

    public synchronized ArrayList<String> getNicknames(){
        return nicknames;
    }

    @Override
    public String toString() {
        String users = "";
        for(int i = 0; i < nicknames.size(); i++){
            users += nicknames.get(i) + "\n";
        }
        return users;
    }
}
