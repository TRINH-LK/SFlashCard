package com.example.trinhle.sflashcard.utils;

import com.example.trinhle.sflashcard.model.BackLanguage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trinh Le on 08/16/16.
 */
public interface BackLanguageListener {
    void addLanguage(BackLanguage language, String bookId);
    ArrayList<BackLanguage> getListBackLanguage(String bookId);
}
