package com.ispm.medicare2.model;

public class UserSignIn {
    private String Email;
    private String Password;

    public UserSignIn(){

    }

    public UserSignIn(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail () {
        return Email;
    }

    public void setEmail (String email) {
        Email = email;
    }

    public String getPassword () {
        return Password;
    }

    public void setPassword (String password) {
        Password = password;
    }



}
