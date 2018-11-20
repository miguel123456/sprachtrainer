package com.example.user.sprachtrainer.models;

public class User {

    private Integer Cod_User;
    private String Name;
    private String Email;
    private String Password;
    private String Ip_Adresse;
    private  String Imei;
    private  String Date_Of_Birth;
    public   String Token;
    private  Integer Status;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Integer getCod_User() {
        return Cod_User;
    }

    public void setCod_User(Integer cod_User) {
        Cod_User = cod_User;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

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

    public String getIp_Adresse() {
        return Ip_Adresse;
    }

    public void setIp_Adresse(String ip_Adresse) {
        Ip_Adresse = ip_Adresse;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }
}
