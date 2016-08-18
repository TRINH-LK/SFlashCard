package com.example.trinhle.sflashcard.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Trinh Le on 08/16/16.
 */
public class BookInfo {
    @SerializedName("bookid")
    private String bookId;
    @SerializedName("bookname")
    private String bookName;
    @SerializedName("description")
    private String descBook;
    @SerializedName("url")
    private String url;
    @SerializedName("num_card")
    private int numCard;
    @SerializedName("num_read")
    private int numRead;

    @SerializedName("backlanguage")
    private List<BackLanguage> backLanguages;
    @SerializedName("frontlanguage")
    private List<FrontLanguage> frontLanguages;

    public BookInfo() {}

    public BookInfo(String bookId, String bookName, String descBook, String url, int numCard, int numRead, List<BackLanguage> backLanguages, List<FrontLanguage> frontLanguages) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.descBook = descBook;
        this.url = url;
        this.numCard = numCard;
        this.numRead = numRead;
        this.backLanguages = backLanguages;
        this.frontLanguages = frontLanguages;
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

    public String getDescBook() {
        return descBook;
    }

    public void setDescBook(String descBook) {
        this.descBook = descBook;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<BackLanguage> getBackLanguages() {
        return backLanguages;
    }

    public void setBackLanguages(List<BackLanguage> backLanguages) {
        this.backLanguages = backLanguages;
    }

    public List<FrontLanguage> getFrontLanguages() {
        return frontLanguages;
    }

    public void setFrontLanguages(List<FrontLanguage> frontLanguages) {
        this.frontLanguages = frontLanguages;
    }
}





