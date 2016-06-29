package com.udacity.abng.habbittraker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.udacity.abng.habbittraker.data.HabbitContract.CategoryEntry;
import com.udacity.abng.habbittraker.data.HabbitContract.HabbitEntry;

/**
 * Created by yul on 29.06.16.
 */
public class HabbitDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "habbit.db";

    public HabbitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold categories.
        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                HabbitContract.CategoryEntry._ID + " INTEGER PRIMARY KEY," +
                CategoryEntry.COLUMN_CATEGORY + " TEXT UNIQUE NOT NULL, " +
                CategoryEntry.COLUMN_HABBIT_DESC + " TEXT NOT NULL, " +
                " );";
        // Create a table to hold habbits.
        final String SQL_CREATE_HABBIT_TABLE = "CREATE TABLE " + HabbitEntry.TABLE_NAME + " (" +
                HabbitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                // the ID of the category entry associated with this habbit data
                HabbitEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                HabbitEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                HabbitEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                HabbitEntry.COLUMN_DONE + " INTEGER, " +

                // Set up the category column as a foreign key to category table.
                " FOREIGN KEY (" + HabbitEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                CategoryEntry.TABLE_NAME + " (" + CategoryEntry._ID + "), " +

                // To assure the application have just one habbit entry per day
                // per category, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + HabbitEntry.COLUMN_DATE + ", " +
                HabbitEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_HABBIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // addthis  upgrade policy to simply to discard the data and start over
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HabbitEntry.TABLE_NAME);
//        onCreate(sqLiteDatabase);
    }
}
