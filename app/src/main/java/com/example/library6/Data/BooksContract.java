package com.example.library6.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class BooksContract {

    public BooksContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.library6";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "books";

    public static class BooksEntry implements BaseColumns{

        public static final String LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH;

        public static final String ITEM_TYPE = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH);

        public static final String TABLE_NAME = "Books";
        public static final String _ID = BaseColumns._ID;
        public static final String PRODUCT = "Product";
        public static final String PRICE = "Price";
        public static final String QUANTITY = "Quantity";
        public static final String SUPPLIER_NAME = "Name";
        public static final String SUPPLIER_PHONE = "Phone";
    }
}
