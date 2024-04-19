package main.chatsystem.Model;

import java.io.Serializable;

public class Counter implements Serializable {
    private static Counter instance;
    private int counter;

    private Counter() {
        this.counter = 0;
    }

    public static synchronized Counter getInstance() {
        if (instance == null)
        {
            instance = new Counter();
        }
        return instance;
    }

    public synchronized void increase(){
        counter++;
    }
    public synchronized void decrease(){
        counter--;
    }
    public synchronized int getCounter(){
        return counter;
    }
}
