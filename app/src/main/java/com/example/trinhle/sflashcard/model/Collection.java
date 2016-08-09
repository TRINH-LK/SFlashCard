package com.example.trinhle.sflashcard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public class Collection {

    @SerializedName("collectionid")
    private String collectionId;
    @SerializedName("collectionname")
    private String collectionName;
    @SerializedName("description")
    private String description;
    @SerializedName("numberbook")
    private String numberBook;
    @SerializedName("thumblink")
    private String thumbLink;
    @SerializedName("url")
    private String url;

    // Constructor
    public Collection() {}

    public Collection(String collectionId, String collectionName, String description, String numberBook, String thumbLink, String url) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.description = description;
        this.numberBook = numberBook;
        this.thumbLink = thumbLink;
        this.url = url;
    }

    // Getter and Setter

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberBook() {
        return numberBook;
    }

    public void setNumberBook(String numberBook) {
        this.numberBook = numberBook;
    }

    public String getThumbLink() {
        return thumbLink;
    }

    public void setThumbLink(String thumbLink) {
        this.thumbLink = thumbLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
