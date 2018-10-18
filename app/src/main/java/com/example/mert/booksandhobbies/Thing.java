package com.example.mert.booksandhobbies;

import java.io.Serializable;

/**
 * Created by mert on 17.02.2017.
 */

public class Thing implements Serializable{
    private int thingId;
    private String name;
    private int timeSpent;
    private String dateStarted;
    private String lastUpdate;
    private long timeStarted;
    private long timeOfLastUpdate;
    private boolean isHobby;


    public Thing() {
        name="";
        timeSpent=0;
        dateStarted="";
        lastUpdate="";
        timeStarted=0;
        timeOfLastUpdate=0;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }

    public long getTimeOfLastUpdate() {
        return timeOfLastUpdate;
    }

    public void setTimeOfLastUpdate(long timeOfLastUpdate) {
        this.timeOfLastUpdate = timeOfLastUpdate;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDateStarted() {
        return dateStarted;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getThingId() {
        return thingId;
    }

    public void setThingId(int thingId) {
        this.thingId = thingId;
    }

    public boolean isHobby() {
        return isHobby;
    }

    public void setHobby(boolean hobby) {
        isHobby = hobby;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "name='" + name + '\'' +
                ", timeSpent=" + timeSpent +
                '}';
    }
}
