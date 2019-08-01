package com.example.library6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.library6.Data.BooksContract.BooksEntry;

import com.example.library6.Data.BooksContract;
import com.example.library6.Data.BooksCursorAdapter;
import com.example.library6.Data.BooksDbHelper;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER = 1;

    BooksCursorAdapter booksCursorAdapter;

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.insert:

                insertBook();
                Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.editor:

                Intent intent = new Intent(this,Editor.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //booksDbHelper = new BooksDbHelper(this);
        ListView listView = (ListView) findViewById(R.id.list);
        booksCursorAdapter = new BooksCursorAdapter(this,null);

        listView.setAdapter(booksCursorAdapter);

        getSupportLoaderManager().initLoader(LOADER,null,this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,Editor.class);
                Uri uri = ContentUris.withAppendedId(BooksEntry.CONTENT_URI,l);
                intent.setData(uri);
                startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String [] projection = {BooksEntry._ID,BooksEntry.PRODUCT,BooksEntry.SUPPLIER_NAME};
        return new CursorLoader(this,BooksEntry.CONTENT_URI,projection,null,
                null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        booksCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        booksCursorAdapter.swapCursor(null);

    }

    public void insertBook(){

        ContentValues values = new ContentValues();

        values.put(BooksEntry.PRODUCT,"Buddha");
        values.put(BooksEntry.PRICE,35);
        values.put(BooksEntry.QUANTITY,10);
        values.put(BooksEntry.SUPPLIER_NAME,"Ahmed");
        values.put(BooksEntry.SUPPLIER_PHONE,123);

        Uri ins = getContentResolver().insert(BooksEntry.CONTENT_URI,values);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        display();
//    }
//
//    public void insertBook(){
//
//        SQLiteDatabase dbW = booksDbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        booksDbHelper = new BooksDbHelper(this);
//        values.put(BooksEntry.PRODUCT,"Buddha");
//        values.put(BooksEntry.PRICE,50);
//        values.put(BooksEntry.QUANTITY,10);
//        values.put(BooksEntry.SUPPLIER_NAME,"Ahmed");
//        values.put(BooksEntry.SUPPLIER_PHONE,123);
//
//        long ins = dbW.insert(BooksEntry.TABLE_NAME,null,values);
//    }
//
//    public void display() {
//
//        booksDbHelper = new BooksDbHelper(this);
//
//        SQLiteDatabase dbR = booksDbHelper.getReadableDatabase();
//
//        String [] projection = {BooksEntry.PRODUCT,BooksEntry.PRICE,BooksEntry.QUANTITY,
//                BooksEntry.SUPPLIER_NAME,BooksEntry.SUPPLIER_PHONE};
//
//        Cursor cursor = dbR.query(BooksEntry.TABLE_NAME,projection,null,null,
//                null,null,null);
//
//    }
}
