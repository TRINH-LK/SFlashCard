package com.example.trinhle.sflashcard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trinhle.sflashcard.model.FrontLanguage;
import com.example.trinhle.sflashcard.utils.FrontLanguageListener;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 08/16/16.
 */
public class FrontLanguageHandler extends SQLiteOpenHelper implements FrontLanguageListener{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "frontlanguage.db";
    private static final String TABLE_NAME = "frontlanguage";
    private static final String KEY_LANGUAGE_ID = "language_id";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_SHORT_NAME = "short_name";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_BOOK_ID = "book_id";

    String CREATE_TABLE = "  CREATE  TABLE  "  +  TABLE_NAME  +  "( "
            +  KEY_LANGUAGE_ID   +  " INTEGER PRIMARY KEY,"
            +  KEY_LANGUAGE    +   " TEXT,"
            +  KEY_SHORT_NAME   +   " TEXT,"
            +  KEY_FULL_NAME   +   " TEXT,"
            +  KEY_BOOK_ID   +  " TEXT )";

    String DROP_TABE = " DROP  TABLE  IF  EXISTS "  +  TABLE_NAME;


    public FrontLanguageHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addFrontLanguage(FrontLanguage language, String bookId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(KEY_LANGUAGE_ID, language.getFrontId());
            values.put(KEY_LANGUAGE, language.getFrontLanguage());
            values.put(KEY_SHORT_NAME, language.getShortName());
            values.put(KEY_FULL_NAME, language.getFullName());
            values.put(KEY_BOOK_ID, bookId);
            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<FrontLanguage> getListFrontLanguage(String bookId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<FrontLanguage> list = null;
        String query = "  SELECT  *  FROM  "  +  TABLE_NAME   +  "  WHERE  "  +  KEY_BOOK_ID  +  " = "  + bookId;

        try {
            list = new ArrayList<FrontLanguage>();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    FrontLanguage language = new FrontLanguage();

                    language.setFrontId(cursor.getString(0));
                    language.setFrontLanguage(cursor.getString(1));
                    language.setShortName(cursor.getString(2));
                    language.setFullName(cursor.getString(3));

                    list.add(language);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
