package com.udacity.abng.inventoryapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.udacity.abng.inventoryapp.Utility;
import com.udacity.abng.inventoryapp.data.ItemContract.ItemEntry;
import com.udacity.abng.inventoryapp.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yul on 29.06.16.
 */
public class ItemDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Items.db";

    // Table Creaction and deletion
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    //    ITEMS table
    private static final String SQL_CREATE_ITEMS =
            "CREATE TABLE " + ItemEntry.TABLE_NAME + " (" +
                    ItemEntry._ID + " INTEGER PRIMARY KEY," +
                    ItemEntry.ITEM_NAME + TEXT_TYPE + COMMA_SEP +
                    ItemEntry.ITEM_DESC + TEXT_TYPE + COMMA_SEP +
                    ItemEntry.ITEM_IMAGE + TEXT_TYPE + COMMA_SEP +
                    ItemEntry.ITEM_QUANT + " INTEGER" + COMMA_SEP +
                    ItemEntry.ITEM_PRICE + " INTEGER" + " )";

    private static final String SQL_DELETE_ITEMS =
            "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;

    private static final String[] COLUMNS = {ItemEntry._ID, ItemEntry.ITEM_NAME, ItemEntry.ITEM_DESC, ItemEntry.ITEM_IMAGE, ItemEntry.ITEM_QUANT, ItemEntry.ITEM_PRICE};
    private static final String TAG = "Helper.log";

    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ITEMS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(SQL_DELETE_ITEMS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new item
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemEntry.ITEM_NAME, item.getTitle());
        values.put(ItemEntry.ITEM_DESC, item.getDescription());
        values.put(ItemEntry.ITEM_IMAGE, Utility.getBytes(item.getImageBit()));
        values.put(ItemEntry.ITEM_PRICE, item.getPrice());
        values.put(ItemEntry.ITEM_QUANT, item.getQuantity());

        // Inserting Row
        db.insert(ItemEntry.TABLE_NAME, null, values);
        //       db.close(); // Closing database connection
    }

    // Deleting a item
    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ItemEntry.TABLE_NAME, ItemEntry._ID + " = ?",
                new String[]{String.valueOf(item.getId())});
//        db.close();
    }

    // Updating a item
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemEntry.ITEM_NAME, item.getTitle());
        values.put(ItemEntry.ITEM_DESC, item.getDescription());
        values.put(ItemEntry.ITEM_QUANT, item.getQuantity());
        values.put(ItemEntry.ITEM_PRICE, item.getPrice());
        values.put(ItemEntry.ITEM_IMAGE, Utility.getBytes(item.getImageBit()));

        // updating row
        return db.update(ItemEntry.TABLE_NAME, values, ItemEntry._ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public Cursor getAllItems() {
        List<Item> itemList = new ArrayList<Item>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ItemEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Long.parseLong(cursor.getString(0)));
                item.setTitle(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.setImageBit(Utility.getPhoto(cursor.getBlob(3)));
                item.setQuantity(cursor.getInt(4));
                item.setPrice(cursor.getDouble(5));
                // Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        return cursor;
    }

    public Item getItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(ItemEntry.TABLE_NAME, // a. table
                        COLUMNS, // b. column names
                        "_id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
        Log.d(TAG, "Item db.query= " + cursor.getCount());
        Log.d(TAG, "Item id= " + String.valueOf(id));

        Item item = new Item();
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            Log.d(TAG, "Item cursor move to first = " + cursor.getInt(0));

            Log.d(TAG, "Item cursor move to first = " + cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry._ID)));

            item.setId(cursor.getInt(0));
            item.setTitle(cursor.getString(1));
            item.setDescription(cursor.getString(2));
            item.setImageBit(Utility.getPhoto(cursor.getBlob(3)));
            item.setQuantity(cursor.getInt(4));
            item.setPrice(cursor.getDouble(5));
        }
        cursor.close();

        return item;
    }


    // Getting items Count
    public int getItemsCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + ItemEntry.TABLE_NAME;

        Cursor mcursor = db.rawQuery(countQuery, null);
        int count = 0;

        if (mcursor != null && !mcursor.isClosed()) {
            count = mcursor.getCount();
            mcursor.close();
        }
        // return count
        return count;
    }

}
