package com.example.user.sprachtrainer.models;

public class Themen {

    private Integer Cod_Themen;
    private String Name;
    private String Name_Esp;
    private String Content;
    private String Content_Esp;
    private String Video_path;

    public Integer getCod_Themen() {
        return Cod_Themen;
    }

    public void setCod_Themen(Integer cod_Themen) {
        Cod_Themen = cod_Themen;
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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContent_Esp() {
        return Content_Esp;
    }

    public void setContent_Esp(String content_Esp) {
        Content_Esp = content_Esp;
    }

    public String getVideo_path() {
        return Video_path;
    }

    public void setVideo_path(String video_path) {
        Video_path = video_path;
    }
}
