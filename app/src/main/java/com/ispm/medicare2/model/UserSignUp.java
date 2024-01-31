package com.ispm.medicare2.model;

public class UserSignUp {
    private String FullName;
    private String Username;
    private String Email;
    private String Phone;
    private String Address;
    private String Password;

    public UserSignUp(){

    }

    public UserSignUp(String fullName, String username, String email, String phone, String address, String password) {
        this.FullName = fullName;
        this.Username = username;
        this.Email = email;
        this.Phone = phone;
        this.Address = address;
        this.Password = password;
    }


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
