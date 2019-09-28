package com.kenbay.shane.inventoryquickly;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shaneyboi on 9/27/2019.
 */

public class dbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Users.db";
    private static final String DB_TABLE = "Users_Table";

    //columns
    private static final String ID = "ID";
    private static final String NAME = "NAME";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE+" ("+ ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME+ " TEXT "+ ")";

    public dbHelper(android.content.Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);

        onCreate(db);
    }

    public boolean insertData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1; //if result = -1, data doesn't insert
    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor searchUsers(String text) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+DB_TABLE+" WHERE "+NAME+" Like '%"+text+"%'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
