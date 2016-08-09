package com.example.trinhle.sflashcard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trinhle.sflashcard.model.Book;
import com.example.trinhle.sflashcard.utils.BookListener;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 09/08/2016.
 */
public class BookHandler extends SQLiteOpenHelper implements BookListener {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "book.db";
    private static final String TABLE_NAME = "book";
    private static final String KEY_COLLECTION_ID = "collection_id";
    private static final String KEY_BOOK_ID = "book_id";
    private static final String KEY_BOOK_NAME = "book_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NUM_CARD = "num_card";
    private static final String KEY_NUM_READ = "num_read";
    private static final String KEY_URL = "url";

    String CREATE_TABLE  = "  CREATE TABLE  "  +  TABLE_NAME  +   "("
                         +  KEY_COLLECTION_ID  +  " TEXT,"
                         +  KEY_BOOK_ID  +  " INTEGER PRIMARY KEY,"
                         +  KEY_BOOK_NAME   +  " TEXT,"
                         +  KEY_DESCRIPTION   +  " TEXT,"
                         +  KEY_NUM_CARD   +  " INTEGER,"
                         +  KEY_NUM_READ   +  " INTEGER,"
                         +  KEY_URL   +   " TEXT )";

    String DROP_TABLE  =  "  DROP TABLE IF EXISTS  "   +   TABLE_NAME;

    public BookHandler(Context context) {
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
    public void addBook(Book book, String collectionId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(KEY_COLLECTION_ID, collectionId);
            values.put(KEY_BOOK_ID, book.getBookId());
            values.put(KEY_BOOK_NAME, book.getBookName());
            values.put(KEY_DESCRIPTION, book.getDescription());
            values.put(KEY_NUM_CARD, book.getNumCard());
            values.put(KEY_NUM_READ, book.getNumRead());
            values.put(KEY_URL, book.getUrl());

            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Book> getListBookWithCollectionId(String collectionId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Book> listBook = null;
        String query =  " SELECT  *  FROM "  +  TABLE_NAME   +   "  WHERE  "  +  KEY_COLLECTION_ID  +   " = "   + collectionId;

        try {
            listBook = new ArrayList<Book>();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Book item = new Book();
                    item.setBookId(cursor.getString(1));
                    item.setBookName(cursor.getString(2));
                    item.setDescription(cursor.getString(3));
                    item.setNumCard(cursor.getInt(4));
                    item.setNumRead(cursor.getInt(5));
                    item.setUrl(cursor.getString(6));
                    listBook.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listBook;
    }
}
