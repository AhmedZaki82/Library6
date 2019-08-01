package com.example.library6.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.library6.Data.BooksContract.BooksEntry;

public class BooksDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";
    public static final int DATABASE_VERSION = 1;

    public BooksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_SQL = " CREATE TABLE " + BooksEntry.TABLE_NAME + " ("
                + BooksEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BooksEntry.PRODUCT + " TEXT NOT NULL, "
                + BooksEntry.PRICE + " INTEGER NOT NULL, "
                + BooksEntry.QUANTITY + " INTEGER NOT NULL, "
                + BooksEntry.SUPPLIER_NAME + " TEXT NOT NULL, "
                + BooksEntry.SUPPLIER_PHONE + " INTEGER NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
