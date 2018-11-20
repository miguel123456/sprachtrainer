package com.example.user.sprachtrainer.models;

public class Kategorie {

    private Integer Cod_Kategorie;
    private String Name;
    private String Name_Esp;
    private  String Description;

    public Integer getCod_Kategorie() {
        return Cod_Kategorie;
    }

    public void setCod_Kategorie(Integer cod_Kategorie) {
        Cod_Kategorie = cod_Kategorie;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName_Esp() {
        return Name_Esp;
    }

    public void setName_Esp(String name_Esp) {
        Name_Esp = name_Esp;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
