package com.example.mangaapp.comment;

import java.util.Date;

public class commentclass {
    public String chapter;
    public String content;
    public String user;
    public Date time;

    public commentclass() {
    }

    public commentclass(String chapter, String content, String user, Date time) {
        this.chapter = chapter;
        this.content = content;
        this.user = user;
        this.time = time;
    }
}
