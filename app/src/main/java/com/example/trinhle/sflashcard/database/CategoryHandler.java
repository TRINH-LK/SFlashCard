package com.example.trinhle.sflashcard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.volley.toolbox.StringRequest;
import com.example.trinhle.sflashcard.model.Category;
import com.example.trinhle.sflashcard.utils.CategoryListener;

import java.util.ArrayList;

/**
 * Created by Trinh Le on 05/08/2016.
 */
public class CategoryHandler extends SQLiteOpenHelper implements CategoryListener {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "category.db";
    private static final String TABLE_NAME = "category";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NUM_COL = "num_col";

    String CREATE_TABLE = " CREATE TABLE "  +  TABLE_NAME  +  "("
            +  KEY_CATEGORY_ID  +  " INTEGER PRIMARY KEY, "
            +  KEY_CATEGORY_NAME  +  " TEXT,"
            +  KEY_DESCRIPTION  +  " TEXT,"
            +  KEY_NUM_COL  +  " INTEGER )";


    String DROP_TABLE = " DROP TABLE IF EXISTS "  +  TABLE_NAME;

    public CategoryHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Create Category table.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    // Update the Category table.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    // Insert a category into Category table;
    @Override
    public void addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(KEY_CATEGORY_ID, category.getCategoryId());
            values.put(KEY_CATEGORY_NAME, category.getCategoryName());
            values.put(KEY_DESCRIPTION, category.getDescription());
            values.put(KEY_NUM_COL, category.getNumCol());
            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all categories in Category table.
    @Override
    public ArrayList<Category> getAllCategory() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Category> categories = null;

        try {
            categories = new ArrayList<Category>();
            String query = "  SELECT  *  FROM  "  +  TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setCategoryId(cursor.getString(0));
                    category.setCategoryName(cursor.getString(1));
                    category.setDescription(cursor.getString(2));
                    category.setNumCol(cursor.getInt(3));
                    categories.add(category);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }


    // Get the number of category.
    @Override
    public int getCategoryCount() {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        try {
            String query = "  SELECT  *  FROM  " + TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            count = cursor.getCount();
            db.close();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
