package com.example.library6.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.library6.Data.BooksContract.BooksEntry;

public class BooksProvirer extends ContentProvider {

    BooksDbHelper booksDbHelper;
    private static final int BOOKS = 0;
    private static final int BOOK_ID = 1;

   public static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(BooksContract.CONTENT_AUTHORITY,BooksContract.PATH,BOOKS);
        sUriMatcher.addURI(BooksContract.CONTENT_AUTHORITY,BooksContract.PATH + "/#",
                BOOK_ID);
    }

    @Override
    public boolean onCreate() {

        booksDbHelper = new BooksDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        SQLiteDatabase dbR = booksDbHelper.getReadableDatabase();
        int matcher = sUriMatcher.match(uri);
        Cursor cursor = null;

        switch (matcher) {

            case BOOKS:
                cursor = dbR.query(BooksEntry.TABLE_NAME,strings,s,strings1,null,
                        null,null);
                break;

            case BOOK_ID:

                s = BooksEntry._ID + "=?";
                strings1 = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = dbR.query(BooksEntry.TABLE_NAME,strings,s,strings1,null,
                        null,null);
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Query in Here!");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

        }

        cursor.setNotificationUri(getContext().getContentResolver(),BooksEntry.CONTENT_URI);


        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        int matcher = sUriMatcher.match(uri);
        String type = null;

        switch (matcher) {

            case BOOKS:

                type = BooksEntry.LIST_TYPE;
                break;

            case BOOK_ID:

                type = BooksEntry.ITEM_TYPE;
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Type in Here!");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        }

        return type;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        Uri uri1 = null;
        int matcher = sUriMatcher.match(uri);
        SQLiteDatabase dbW = booksDbHelper.getWritableDatabase();

        switch (matcher) {

            case BOOKS:

                long id = dbW.insert(BooksEntry.TABLE_NAME,null,contentValues);
                uri1 = ContentUris.withAppendedId(uri,id);
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Insertion Here!");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        }

        getContext().getContentResolver().notifyChange(uri,null);


        return uri1;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        Integer del = null;
        int matcher = sUriMatcher.match(uri);
        SQLiteDatabase dbW = booksDbHelper.getWritableDatabase();

        switch (matcher) {

            case BOOKS:
                del = dbW.delete(BooksEntry.TABLE_NAME,s,strings);
                break;

            case BOOK_ID:

                s = BooksEntry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                del = dbW.delete(BooksEntry.TABLE_NAME,s,strings);
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Deletion Here!");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        }

        getContext().getContentResolver().notifyChange(BooksEntry.CONTENT_URI,null);

        return del;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        int matcher = sUriMatcher.match(uri);
        Integer upd = null;

        switch (matcher) {

            case BOOKS:
                upd = updateBook(uri,contentValues,s,strings);
                break;

            case BOOK_ID:
                s = BooksEntry._ID + "=?";
                strings = new String[] {String.valueOf(ContentUris.parseId(uri))};
                upd = updateBook(uri,contentValues,s,strings);
                break;

                default:
                    try {
                        throw new IllegalAccessException("No Update Here!");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


        }

        return upd;
    }

    public int updateBook(Uri uri, ContentValues contentValues, String s, String[] strings) {

        SQLiteDatabase dbW = booksDbHelper.getWritableDatabase();
        int up = dbW.update(BooksEntry.TABLE_NAME,contentValues,s,strings);

        if (up != 0) {

            getContext().getContentResolver().notifyChange(BooksEntry.CONTENT_URI,null);
        }
        return up;
    }
}
