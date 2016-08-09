package com.example.trinhle.sflashcard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trinh Le on 08/08/2016.
 */
public class Book {
    @SerializedName("bookid")
    private String bookId;
    @SerializedName("bookname")
    private String bookName;
    @SerializedName("description")
    private String description;
    @SerializedName("numcard")
    private int numCard;
    @SerializedName("numread")
    private int numRead;
    @SerializedName("url")
    private String url;

    public Book() {}

    public Book(String bookId, String bookName, String description, int numCard, int numRead, String url) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.description = description;
        this.numCard = numCard;
        this.numRead = numRead;
        this.url = url;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumCard() {
        return numCard;
    }

    public void setNumCard(int numCard) {
        this.numCard = numCard;
    }

    public int getNumRead() {
        return numRead;
    }

    public void setNumRead(int numRead) {
        this.numRead = numRead;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
