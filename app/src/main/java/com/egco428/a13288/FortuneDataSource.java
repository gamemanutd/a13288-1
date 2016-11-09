package com.egco428.a13288;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;




import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thammarit on 9/11/2559.
 */
public class FortuneDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_FORTUNE, DatabaseHelper.COLUMN_TIME, DatabaseHelper.COLUMN_PRIME};

    public FortuneDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Fortune createFortune(String fortune, String time, String prime){
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_FORTUNE, fortune);
        values.put(DatabaseHelper.COLUMN_TIME, time);
        values.put(DatabaseHelper.COLUMN_PRIME, prime);

        long insertID = database.insert(DatabaseHelper.TABLE_FORTUNE, null,values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_FORTUNE,allColumns, DatabaseHelper.COLUMN_ID+ " = "+insertID, null, null, null, null);
        cursor.moveToFirst();
        Fortune newFortune = cursorToFortune(cursor);
        cursor.close();
        return newFortune;
    }

    public void deleteFortune(Fortune fortune){
        long id = fortune.getId();
        System.out.println("Comment deleted with id: "+id);
        database.delete(DatabaseHelper.TABLE_FORTUNE, DatabaseHelper.COLUMN_ID+" = "+id, null);

    }

    public List<Fortune> getAllFortune(){
        List<Fortune> fortunes = new ArrayList<Fortune>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_FORTUNE, allColumns,null,null, null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Fortune fortune = cursorToFortune(cursor);
            fortunes.add(fortune);
            cursor.moveToNext();
        }
        cursor.close();
        return fortunes;
    }

    public Fortune cursorToFortune(Cursor cursor){
        Fortune fortune= new Fortune();
        fortune.setId(cursor.getLong(0));
        fortune.setFortune(cursor.getString(1));
        fortune.setTime(cursor.getString(2));
        fortune.setPrime(cursor.getString(3));
        return fortune;
    }
}
