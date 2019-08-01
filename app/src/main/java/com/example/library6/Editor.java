package com.example.library6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.library6.Data.BooksContract.BooksEntry;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class Editor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public boolean fieldHasChanged = false;

    View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            fieldHasChanged = true;
            return false;
        }
    };


    public void showAlertDialog(DialogInterface.OnClickListener discard) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do You want to Discard changes?");
        builder.setPositiveButton("Discard",discard);
        builder.setNegativeButton("Keep Editing!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null){

                    dialogInterface.dismiss();

                }
            }

        });

        AlertDialog create = builder.create();
        create.show();
    }

    public static final int LOADER = 1;

    Uri currentBook;

    EditText productE;
    EditText priceE;
    EditText quantityE;
    EditText nameE;
    EditText phoneE;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {



            case R.id.save:

                if (currentBook != null) {

                    updateBook();
                    finish();
                    return true;

                } else {

                    save();
                    finish();
                    return true;
                }

            case R.id.delete:

                if (currentBook != null) {

                    deleteOrNot();
                    return true;

                }else {


                }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        productE = (EditText) findViewById(R.id.product);
        priceE = (EditText) findViewById(R.id.price);
        quantityE = (EditText) findViewById(R.id.quantity);
        nameE = (EditText) findViewById(R.id.name);
        phoneE = (EditText) findViewById(R.id.phone);

        productE.setOnTouchListener(listener);
        priceE.setOnTouchListener(listener);
        quantityE.setOnTouchListener(listener);
        nameE.setOnTouchListener(listener);
        phoneE.setOnTouchListener(listener);

        Intent intent = getIntent();
        currentBook = intent.getData();

        if (currentBook != null) {

            setTitle("Edit Book");

            getSupportLoaderManager().initLoader(LOADER, null, this);

        }else {




            setTitle("Insert Book");
        }



    }

    public void save() {


        String product = productE.getText().toString().trim();
        String price = priceE.getText().toString().trim();
        String quantity = quantityE.getText().toString().trim();
        String name = nameE.getText().toString().trim();
        String phone = phoneE.getText().toString().trim();

        ContentValues values = new ContentValues();

        values.put(BooksEntry.PRODUCT,product);
        values.put(BooksEntry.PRICE,price);
        values.put(BooksEntry.QUANTITY,quantity);
        values.put(BooksEntry.SUPPLIER_NAME,name);
        values.put(BooksEntry.SUPPLIER_PHONE,phone);


        Uri save = getContentResolver().insert(BooksEntry.CONTENT_URI,values);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String [] projection = {BooksEntry._ID,BooksEntry.PRODUCT, BooksEntry.PRICE,
                BooksEntry.QUANTITY,BooksEntry.SUPPLIER_NAME,BooksEntry.SUPPLIER_PHONE};

        return new CursorLoader(this,currentBook,projection,null,
                null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {




        if (data.moveToFirst()) {

            int currentProductColumn = data.getColumnIndex(BooksEntry.PRODUCT);
            int currentPriceColumn = data.getColumnIndex(BooksEntry.PRICE);
            int currentQuantityColumn = data.getColumnIndex(BooksEntry.QUANTITY);
            int currentNameColumn = data.getColumnIndex(BooksEntry.SUPPLIER_NAME);
            int currentPhoneColumn = data.getColumnIndex(BooksEntry.SUPPLIER_PHONE);

            String product = data.getString(currentProductColumn);
            String price = data.getString(currentPriceColumn);
            String quantity = data.getString(currentQuantityColumn);
            String name = data.getString(currentNameColumn);
            String phone = data.getString(currentPhoneColumn);

            productE.setText(product);
            priceE.setText(price);
            quantityE.setText(quantity);
            nameE.setText(name);
            phoneE.setText(phone);

        }



    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        productE.setText("");
        priceE.setText("");
        quantityE.setText("");
        nameE.setText("");
        phoneE.setText("");

    }

    public void updateBook() {

        ContentValues values = new ContentValues();

        String product = productE.getText().toString().trim();
        String price = priceE.getText().toString().trim();
        String quantity = quantityE.getText().toString().trim();
        String name = nameE.getText().toString().trim();
        String phone = phoneE.getText().toString().trim();

        values.put(BooksEntry.PRODUCT,product);
        values.put(BooksEntry.PRICE,price);
        values.put(BooksEntry.QUANTITY,quantity);
        values.put(BooksEntry.SUPPLIER_NAME,name);
        values.put(BooksEntry.SUPPLIER_PHONE,phone);

        getContentResolver().update(currentBook,values,null,null);
    }
//
//    public void deleteBook() {
//
//
//        deleteOrNot();
//    }

    public void deleteOrNot() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do You Really Want to Delete this Book?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                getContentResolver().delete(currentBook,null,null);
                finish();

            }
        });

        builder.setNegativeButton("Don't Delete the Book", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog create = builder.create();
        create.show();
    }

    @Override
    public void onBackPressed() {

        if (!fieldHasChanged){

            super.onBackPressed();

            return;
        } else {

            DialogInterface.OnClickListener discard = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    finish();
                }
            };

            showAlertDialog(discard);
        }
    }
}
