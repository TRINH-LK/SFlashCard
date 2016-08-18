package com.example.trinhle.sflashcard.utils;

import com.example.trinhle.sflashcard.model.FrontLanguage;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 08/16/16.
 */
public interface FrontLanguageListener {
    void addFrontLanguage(FrontLanguage language, String bookId);
    ArrayList<FrontLanguage> getListFrontLanguage(String bookId);
}
