package com.example.trinhle.sflashcard.utils;

import com.example.trinhle.sflashcard.model.Collection;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public interface CollectionListener {

    void addCollection(Collection collection, String categoryId);
    ArrayList<Collection> getAllCollectionWithCategory(String categoryId);
}
