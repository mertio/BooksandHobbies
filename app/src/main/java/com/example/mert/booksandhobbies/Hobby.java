package com.example.mert.booksandhobbies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mert on 17.02.2017.
 */

public class Hobby extends Thing implements Serializable {
    private int hobbyId;
    ArrayList<String> toDo;
    ArrayList<String> done;

    public Hobby() {
        super();
        hobbyId = getThingId();
        toDo = new ArrayList<String>();
        done = new ArrayList<String>();
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "name=" + getName() +
                " toDo=" + toDo +
                ", done=" + done +
                '}';
    }

    public int getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(int hobbyId) {
        this.hobbyId = hobbyId;
    }
}
