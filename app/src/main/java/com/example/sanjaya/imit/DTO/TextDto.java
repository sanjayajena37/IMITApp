package com.example.sanjaya.imit.DTO;

public class TextDto {


    private String notice;
    private String date;

    public TextDto(String notice,String date){
        this.notice=notice;
        this.date=date;
    }

    public String getNotice() { return notice; }

    public String getDate() { return date; }

}



