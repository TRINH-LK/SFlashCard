package com.example.trinhle.sflashcard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Camera;

import com.example.trinhle.sflashcard.model.Collection;
import com.example.trinhle.sflashcard.utils.CollectionListener;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public class CollectionHandler extends SQLiteOpenHelper implements CollectionListener {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "collection.db";
    private static final String TABLE_NAME = "collection";
    private static final String KEY_COLLECTION_ID = "collection_id";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_COLLECTION_NAME = "collection_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NUMBER_BOOK = "number_book";
    private static final String KEY_THUMBLINK = "thumblink";
    private static final String KEY_URL = "url";


    String CREATE_TABLE = "  CREATE TABLE  "  +  TABLE_NAME  +  "( "
                        +  KEY_CATEGORY_ID  +  " TEXT,"
                        +  KEY_COLLECTION_ID  +  " INTEGER PRIMARY KEY,"
                        +  KEY_COLLECTION_NAME   +  " TEXT,"
                        +  KEY_DESCRIPTION   +  " TEXT,"
                        +  KEY_NUMBER_BOOK   +  " TEXT,"
                        +  KEY_THUMBLINK   +  " TEXT,"
                        +  KEY_URL  +  " TEXT )";

    String DROP_TABLE = " DROP TABLE IF EXISTS "  +  TABLE_NAME;

    public CollectionHandler(Context context) {
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
    public void addCollection(Collection collection, String categoryId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(KEY_CATEGORY_ID, categoryId);
            values.put(KEY_COLLECTION_ID, collection.getCollectionId());
            values.put(KEY_COLLECTION_NAME, collection.getCollectionName());
            values.put(KEY_DESCRIPTION, collection.getDescription());
            values.put(KEY_NUMBER_BOOK, collection.getNumberBook());
            values.put(KEY_THUMBLINK, collection.getThumbLink());
            values.put(KEY_URL, collection.getUrl());
            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Collection> getAllCollectionWithCategory(String categoryId) {
        ArrayList<Collection> collectionWithCategory = null;
        SQLiteDatabase db = getReadableDatabase();
        try {
            collectionWithCategory = new ArrayList<Collection>();
            String query = "  SELECT  *  FROM  "  +  TABLE_NAME  +  "  WHERE  "  +  KEY_CATEGORY_ID  +  " = "  +  categoryId;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Collection item = new Collection();
                    item.setCollectionId(cursor.getString(1));
                    item.setCollectionName(cursor.getString(2));
                    item.setDescription(cursor.getString(3));
                    item.setNumberBook(cursor.getString(4));
                    item.setThumbLink(cursor.getString(5));
                    item.setUrl(cursor.getString(6));

                    collectionWithCategory.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collectionWithCategory;
    }
}
