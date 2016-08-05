package com.example.trinhle.sflashcard.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public class Category {
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("description")
    private String description;
    @SerializedName("num_col")
    private int numCol;

    // Constructor
    public Category() {}

    public Category(String categoryId, String categoryName, String description, int numCol) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.numCol = numCol;
    }

    // Getter and Setter

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }
}
