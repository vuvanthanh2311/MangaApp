package com.example.mangaapp;

import java.util.List;

public class truyen {
    public String id;
    public String tentruyen;
    public String linkhinh;
    public String banner;
    public Object category;
    public String tentacgia;

    public truyen() {
    }

    public truyen(String id, String tentruyen, String linkhinh, String banner, Object category, String tentacgia) {
        this.id = id;
        this.tentruyen = tentruyen;
        this.linkhinh = linkhinh;
        this.banner = banner;
        this.category = category;
        this.tentacgia = tentacgia;
    }
}
