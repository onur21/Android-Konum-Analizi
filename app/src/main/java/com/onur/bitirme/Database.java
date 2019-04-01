package com.onur.bitirme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Onur on 27.04.2017.
 */

public class Database extends SQLiteOpenHelper{
     static final int DATABASE_VERSION = 1;
     static final String DATABASE_NAME = "sqllite_database";
     static final String TABLE_NAME ="konum_listesi1";
     static final String KONUM_ID ="id";
     static final String ENLEM ="enlem";
     static final String BOYLAM ="boylam";
    static final String TARIH="tarih";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            +KONUM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +ENLEM+ " TEXT,"
            +BOYLAM+ " TEXT,"
            +TARIH+" DATETIME DEFAULT CURRENT_TIMESTAMP"
        +")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void konumEkle(String enlem1,String boylam1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ENLEM,enlem1);
        values.put(BOYLAM,boylam1);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<HashMap<String, String>> konumlar() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> konumlist = new ArrayList<HashMap<String, String>>();
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                konumlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        // return kitap liste
        return konumlist;

    }

    public HashMap<String, String> konumListele(int id){
        HashMap<String,String> konum = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            konum.put(ENLEM,cursor.getString(1));
            konum.put(BOYLAM,cursor.getString(2));
            konum.put(TARIH,cursor.getString(3));

        }
        cursor.close();
        db.close();
        return konum;
}
    public void konumSil(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KONUM_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


}

