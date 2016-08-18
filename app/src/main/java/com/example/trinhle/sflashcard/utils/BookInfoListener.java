package com.example.trinhle.sflashcard.utils;

import com.example.trinhle.sflashcard.model.BookInfo;

/**
 * Created by Trinh Le on 08/16/16.
 */
public interface BookInfoListener {
    void addBookInfo(BookInfo bookInfo);
    BookInfo getBookInfo(String bookId);
}
