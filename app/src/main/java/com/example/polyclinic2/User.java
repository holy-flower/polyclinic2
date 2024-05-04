package com.example.polyclinic2;

import java.io.Serializable;

public class User implements Serializable {
    public String id, fio, date, policy, serial, place;

    public User() {
    }

    public User (String fio) {
        this.fio = fio;
    }

    public User(String id, String fio, String date, String policy, String serial, String place) {
        this.id = id;
        this.fio = fio;
        this.date = date;
        this.policy = policy;
        this.serial = serial;
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
