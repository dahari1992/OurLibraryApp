package com.example.matan.library;


public class User {
    //vars
    private String username , password ,email,address,phone;

    public User(String username, String password , String email, String phone , String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;

    }

    //getters
    /**
     * get username
     * **/
    public String getUsername() {
        return username;
    }
    /**
     * get password
     * **/
    public String getPassword() {
        return password;
    }
    /**
     * get email
     * **/
    public String getEmail() {
        return email;
    }
    /**
     * get address
     * **/
    public String getAddress() {
        return address;
    }
    /**
     * get phone
     * **/
    public String getPhone() {
        return phone;
    }
}