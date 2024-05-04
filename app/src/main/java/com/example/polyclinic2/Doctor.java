package com.example.polyclinic2;

import java.io.Serializable;

public class Doctor implements Serializable {
    String id, fioDoc, license, specialization, phone;

    public Doctor() {
    }

    public Doctor(String fioDoc) {
        this.fioDoc = fioDoc;
    }

    public Doctor(String id, String fioDoc, String license, String specialization, String phone) {
        this.id = id;
        this.fioDoc = fioDoc;
        this.license = license;
        this.specialization = specialization;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFioDoc() {
        return fioDoc;
    }

    public void setFioDoc(String fioDoc) {
        this.fioDoc = fioDoc;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
