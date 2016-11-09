package com.egco428.a13288;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Thammarit on 9/11/2559.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_FORTUNE = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FORTUNE = "fortune";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_PRIME = "prime";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FORTUNE + " (" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_FORTUNE + " text not null, " +
            COLUMN_TIME + " text not null, " +
            COLUMN_PRIME + " text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORTUNE);
        onCreate(db);
    }
}
