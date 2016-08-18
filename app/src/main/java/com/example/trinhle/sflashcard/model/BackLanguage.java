package com.example.trinhle.sflashcard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trinh Le on 08/16/16.
 */
public class BackLanguage {
    @SerializedName("lid")
    private String backId;
    @SerializedName("language")
    private String backLanguage;
    @SerializedName("shortname")
    private String shortName;
    @SerializedName("fullname")
    private String fullName;

    public BackLanguage() {}

    public BackLanguage(String backId, String backLanguage, String shortName, String fullName) {
        this.backId = backId;
        this.backLanguage = backLanguage;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public String getBackLanguage() {
        return backLanguage;
    }

    public void setBackLanguage(String backLanguage) {
        this.backLanguage = backLanguage;
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
