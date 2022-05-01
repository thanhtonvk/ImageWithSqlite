package com.example.imagewithsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBContext extends SQLiteOpenHelper {
    public DBContext(@Nullable Context context) {
        super(context, "database.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table HinhAnh(" +
                "Ten nvarchar(50)," +
                "Anh blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<HinhAnh> getHinhAnhs() {
        List<HinhAnh> hinhAnhs = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from HinhAnh", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HinhAnh hinhAnh = new HinhAnh(cursor.getString(0), cursor.getBlob(1));
            hinhAnhs.add(hinhAnh);
            cursor.moveToNext();
        }
        database.close();
        return hinhAnhs;
    }

    public void add(HinhAnh hinhAnh) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ten", hinhAnh.getTen());
        values.put("Anh", hinhAnh.getAnh());
        database.insert("HinhAnh", null, values);
        database.close();
    }
}
