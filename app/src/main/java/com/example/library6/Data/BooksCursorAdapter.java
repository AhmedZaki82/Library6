package com.example.library6.Data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.library6.Data.BooksContract.BooksEntry;

import com.example.library6.R;

public class BooksCursorAdapter extends CursorAdapter {
    public BooksCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_list,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int currentNameColumnIndex = cursor.getColumnIndex(BooksEntry.PRODUCT);
        int currentSummColumnIndex = cursor.getColumnIndex(BooksEntry.SUPPLIER_NAME);

        String name = cursor.getString(currentNameColumnIndex);
        String summ = cursor.getString(currentSummColumnIndex);

        TextView nameT = (TextView) view.findViewById(R.id.name);
        TextView summT = (TextView) view.findViewById(R.id.summary);

        nameT.setText(name);
        summT.setText(summ);

    }
}
