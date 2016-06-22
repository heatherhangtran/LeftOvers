package com.randybiglow.leftovers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

/**
 * Created by DarrellG on 6/13/16.
 */
public class LocalDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FRIDGE.db";
    public static final String INGREDIENT_TABLE = "INGREDIENTS";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "Name";
    public static final String COL_EXP = "Expiration";
    public static final String COL_ADDED = "Added";
    public static final String COL_PHOTO = "Photo";
    public static final String[] INGREDIENT_COLUMNS = {COL_ID, COL_NAME, COL_EXP, COL_ADDED, COL_PHOTO};
    private static final String CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + INGREDIENT_TABLE +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT, " +
                    COL_EXP + " TEXT, " +
                    COL_ADDED + " TEXT, " +
                    COL_PHOTO + " BLOB )";

    private static LocalDBHelper instance;


    //Creating new instance of helper if it doesn't exist yet
    public static LocalDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDBHelper(context);
        }
        return instance;
    }

    private LocalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INGREDIENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENT_TABLE);
        this.onCreate(db);
    }
    //Adding methods to populate listview with sort logic
    public Cursor getIngredients(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + INGREDIENT_TABLE + "." + COL_ID + ", " +
                COL_NAME + ", " +
                COL_ADDED + ", " +
                COL_EXP + " FROM " + INGREDIENT_TABLE,null, null);

        return cursor;
    }
    public void addItem(String name, String exp, String date, Blob blob){
        SQLiteDatabase myDB = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EXP, exp);
        values.put(COL_ADDED, date);
        values.put(COL_PHOTO, String.valueOf(blob));
        myDB.insert(INGREDIENT_TABLE, null, values);

    }
    //Adding method for detail view
    public Cursor getDescriptionById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(INGREDIENT_TABLE,
                new String[]{COL_ID, COL_NAME, COL_ADDED, COL_EXP, COL_PHOTO},
                COL_ID+" = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            return cursor;
        } else {
            return null;
        }
    }
    public void deleteIngredient(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(INGREDIENT_TABLE, COL_ID + " = " + id, null );
    }

    //Adding method for searching
    public Cursor searchIngredients(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(INGREDIENT_TABLE,
                INGREDIENT_COLUMNS,
                COL_NAME + " LIKE ? OR " + COL_EXP + " LIKE ? OR " + COL_ADDED + " LIKE ? ",
                new String[]{"%"+ query + "%" , "%" + query + "%" , "%" + query + "%"},
                null,
                null,
                null,
                null);
        return cursor;
    }
}

