package com.example.ambulance1.Model;

public class User {
    private String email,name,password,phone_number;
    public User(){

    }
    public User(String email, String name, String password, String phone_number) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
