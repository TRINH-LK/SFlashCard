package com.example.trinhle.sflashcard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trinh Le on 08/16/16.
 */
public class FrontLanguage {
    @SerializedName("lid")
    private String frontId;
    @SerializedName("language")
    private String frontLanguage;
    @SerializedName("shortname")
    private String shortName;
    @SerializedName("fullname")
    private String fullName;

    public FrontLanguage() {
    }

    public FrontLanguage(String frontId, String frontLanguage, String shortName, String fullName) {
        this.frontId = frontId;
        this.frontLanguage = frontLanguage;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getFrontId() {
        return frontId;
    }

    public void setFrontId(String frontId) {
        this.frontId = frontId;
    }

    public String getFrontLanguage() {
        return frontLanguage;
    }

    public void setFrontLanguage(String frontLanguage) {
        this.frontLanguage = frontLanguage;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
