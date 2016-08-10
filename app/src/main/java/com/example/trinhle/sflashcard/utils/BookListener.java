package com.example.trinhle.sflashcard.utils;

import com.example.trinhle.sflashcard.model.Book;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 09/08/2016.
 */
public interface BookListener {
    void addBook(Book book, String collectionId);
    ArrayList<Book> getListBookWithCollectionId(String collectionId);
}
