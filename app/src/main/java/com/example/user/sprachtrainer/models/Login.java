package com.example.user.sprachtrainer.models;

import com.google.gson.annotations.SerializedName;

public class Login {

    /*@SerializedName("id")
    private String Name;*/
    private String Email;
    private String Password;

    public Login(String Email,String Password){
        this.Email= Email;
        this.Password=Password;
    }

  /*  public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }*/

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
