package com.example.contentproviders.models;

public class ContactModel {
    private String name;
    private String phone;
    private String phoneWork;
    private String numberHome;
    private String numberWithSpaces;

    public String getNumberWithSpaces() {
        return numberWithSpaces;
    }

    public void setNumberWithSpaces(String numberWithSpaces) {
        this.numberWithSpaces = numberWithSpaces;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getPhoneWorkMobile() {
        return numberHome;
    }

    public void setPhoneWorkMobile(String phoneWorkMobile) {
        this.numberHome = phoneWorkMobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
