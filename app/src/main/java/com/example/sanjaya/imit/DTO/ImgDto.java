package com.example.sanjaya.imit.DTO;

public class ImgDto {

    private String img;
    private String date;
    private String title;

    public ImgDto(String img,String date,String title) {

        this.img=img;
        this.date=date;
        this.title=title;
    }

    public String getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }



}
