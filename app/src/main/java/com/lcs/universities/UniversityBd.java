package com.lcs.universities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UniversityBd extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UniversityBd.db";
    public UniversityBd(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE University ("+
                "NAME TEXT PRIMARY KEY , "+
                "IMAGE TEXT,"+
                "DESCRIPTION TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertarUniversity(UniversityDetail unidetail){

        SQLiteDatabase db = this.getReadableDatabase();
        if(db==null){
            db = this.getWritableDatabase();
        }

        ContentValues values = new ContentValues();

        values.put("NAME", unidetail.getName());
        values.put("IMAGE", unidetail.getImageUrl());
        values.put("DESCRIPTION", unidetail.getDescription());

        db.insert("University", null, values);
    }

    @SuppressLint("Range")
    public UniversityDetail getUniversity(String nombreuni){
        UniversityDetail unidetail= new UniversityDetail();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(
                "University",
                null,
                "NAME=?",
                new String[]{nombreuni},
                null,
                null,
                null
        );
        while(c.getCount()>0 && c.moveToNext()){
            unidetail.setName(c.getString(c.getColumnIndex("NAME")));
            unidetail.setImageUrl(c.getString(c.getColumnIndex("IMAGE")));
            unidetail.setDescription(c.getString(c.getColumnIndex("DESCRIPTION")));
        }
        return unidetail;
    }

    public void eliminarUniversity(String id) {
        SQLiteDatabase db=this.getWritableDatabase();
        if(db==null){
            db = getWritableDatabase();
        }
        db.execSQL("DELETE from University where NAME='"+id+"'");
    }

    public void actualizarUniversity(UniversityDetail unid){

        SQLiteDatabase db=this.getWritableDatabase();
        if(db==null){
            db = getWritableDatabase();
        }

        ContentValues values = new ContentValues();

        values.put("IMAGE", unid.getImageUrl());
        values.put("DESCRIPTION", unid.getDescription());

        db.update("University", values, "NOMBRE=?", new String[]{unid.getName()});
    }
}
