package com.intelness.fgmsongs.beans;

public class Song {

    private int    id;
    private int    number;
    private String title;
    private String second;
    private String author;
    private String createdDate;

    public Song() {
    }

    public Song( int id, int number, String title, String second, String author, String createdDate ) {
        this.id = id;
        this.number = number;
        this.title = title;
        this.second = second;
        this.author = author;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber( int number ) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond( String second ) {
        this.second = second;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor( String author ) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate( String createdDate ) {
        this.createdDate = createdDate;
    }

}
