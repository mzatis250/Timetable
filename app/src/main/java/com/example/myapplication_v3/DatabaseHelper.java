package com.example.myapplication_v3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME ="orak_tabla";
    private static final String COL1="ID";
    private static final String COL2="nev";
    private static final String COL3="nap";
    private static final String COL4="orakezdet";
    private static final String COL5="oraveg";
    private static final String COL6="ABhet";
    private static final String COL7="kirajzolt";
    private static final String COL8="tanarneve";
    private static final String COL9="terem";
    DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "  + COL2 + " TEXT," + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT,  " + COL6 + " TEXT, " + COL7 +" TEXT, " + COL8 + " TEXT, " + COL9 + " TEXT);";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    boolean addData(String item, String item2, String item3, String item4, String item5, String item6, String item7, String item8) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);
        contentValues.put(COL5, item4);
        contentValues.put(COL6, item5);
        contentValues.put(COL7, item6);
        contentValues.put(COL8, item7);
        contentValues.put(COL9, item8);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }
    Cursor getData3(String nap, String het){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "SELECT ID, orakezdet, oraveg FROM " + TABLE_NAME + " WHERE "+ COL3 + " = "+"'"+nap+"'" +" AND "+ COL6 +" = " + "'"+het+"'";
        return db.rawQuery(query, null);
    }
    Cursor getData2(){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY "+ COL4 + " ASC";
        return db.rawQuery(query, null);
    }
    Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    Cursor getID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        return db.rawQuery(query, null);
    }
    void updateData(String newName, int id, String oldName, String new_nap, String old_nap, String new_startTime, String old_startTime, String new_endTime, String old_endTime, String new_het, String old_het,String new_negyzet,String old_negyzet, String new_tanar, String old_tanar, String new_terem, String old_terem){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        String query2 = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + new_nap + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + old_nap + "'";
        String query3 = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + new_startTime + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + old_startTime + "'";
        String query4 = "UPDATE " + TABLE_NAME + " SET " + COL5 +
                " = '" + new_endTime + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL5 + " = '" + old_endTime + "'";
        String query5 = "UPDATE " + TABLE_NAME + " SET " + COL6 +
                " = '" + new_het + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL6 + " = '" + old_het + "'";
        String query6 = "UPDATE " + TABLE_NAME + " SET " + COL7 +
                " = '" + new_negyzet + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL7 + " = '" + old_negyzet + "'";
        String query7 = "UPDATE " + TABLE_NAME + " SET " + COL8 +
                " = '" + new_tanar + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL8 + " = '" + old_tanar + "'";
        String query8 = "UPDATE " + TABLE_NAME + " SET " + COL9 +
                " = '" + new_terem + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL9 + " = '" + old_terem + "'";
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        db.execSQL(query6);
        db.execSQL(query7);
        db.execSQL(query8);
    }
    void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
