package com.example.trinhle.sflashcard.utils;

import com.example.trinhle.sflashcard.model.Category;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public interface CategoryListener {
    public void addCategory(Category category);
    public ArrayList<Category> getAllCategory();
    public int getCategoryCount();
}
