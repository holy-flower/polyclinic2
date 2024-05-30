package com.example.polyclinic2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PatientRecord {
    private String userId;
    private String doctorId;
    private String name;
    private String registeredDate;
    private String registeredTime;

    public PatientRecord() {
    }

    public PatientRecord(String userId, String doctorId, String name, String registeredDate, String registeredTime) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.name = name;
        this.registeredDate = registeredDate;
        this.registeredTime = registeredTime;
    }

    public PatientRecord(String doctorId, String name, String registeredDate, String registeredTime) {
        this.doctorId = doctorId;
        this.name = name;
        this.registeredDate = registeredDate;
        this.registeredTime = registeredTime;
    }

    public PatientRecord(String name, String registeredDate, String registeredTime) {
        this.name = name;
        this.registeredDate = registeredDate;
        this.registeredTime = registeredTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public PatientRecord(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegisteredTime(String registeredTime) {
        this.registeredTime = registeredTime;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getName() {
        return name;
    }

    public String getRegisteredTime() {
        return registeredTime;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    @Override
    public String toString() {
        return "PatientRecord{" +
                "userId='" + userId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", registeredDate='" + registeredDate + '\'' +
                ", registeredTime='" + registeredTime + '\'' +
                '}';
    }
}