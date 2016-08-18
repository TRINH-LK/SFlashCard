package com.example.trinhle.sflashcard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trinhle.sflashcard.model.BookInfo;
import com.example.trinhle.sflashcard.utils.BookInfoListener;

/**
 * Created by Trinh Le on 08/16/16.
 */
public class BookInfoHandler extends SQLiteOpenHelper implements BookInfoListener{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bookinfo.db";
    private static final String TABLE_NAME = "bookinfo";
    private static final String KEY_BOOK_ID = "book_id";
    private static final String KEY_BOOK_NAME = "book_name";
    private static final String KEY_BOOK_DESC = "book_desc";
    private static final String KEY_URL = "url";
    private static final String KEY_NUM_CARD = "num_card";
    private static final String KEY_NUM_READ = "num_read";

    String CREATE_TABLE =  "  CREATE  TABLE  "   +  TABLE_NAME   +   "( "
            +   KEY_BOOK_ID  +   " INTEGER PRIMARY KEY,"
            +   KEY_BOOK_NAME   +   " TEXT,"
            +   KEY_BOOK_DESC   +   " TEXT,"
            +   KEY_URL   +   " TEXT,"
            +   KEY_NUM_CARD   +   " TEXT,"
            +   KEY_NUM_READ   +   " TEXT )";


    String DROP_TABLE =  "  DROP  TABLE  IF  EXISTS  "   +   TABLE_NAME;

    public BookInfoHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addBookInfo(BookInfo bookInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(KEY_BOOK_ID, bookInfo.getBookId());
            values.put(KEY_BOOK_NAME, bookInfo.getBookName());
            values.put(KEY_BOOK_DESC, bookInfo.getDescBook());
            values.put(KEY_URL, bookInfo.getUrl());
            values.put(KEY_NUM_CARD, bookInfo.getNumCard());
            values.put(KEY_NUM_READ, bookInfo.getNumRead());

            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BookInfo getBookInfo(String bookId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "  SELECT  *  FROM  "  +  TABLE_NAME  +  " WHERE "  +  KEY_BOOK_ID  +   "="   +  bookId;
        BookInfo result = null;

        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    BookInfo item = new BookInfo();
                    item.setBookId(cursor.getString(0));
                    item.setBookName(cursor.getString(1));
                    item.setDescBook(cursor.getString(2));
                    item.setUrl(cursor.getString(3));
                    item.setNumCard(cursor.getInt(4));
                    item.setNumRead(cursor.getInt(5));
                    result = item;
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
