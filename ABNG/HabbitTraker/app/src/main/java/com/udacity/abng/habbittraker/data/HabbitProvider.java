package com.udacity.abng.habbittraker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by yul on 29.06.16.
 */
public class HabbitProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private HabbitDbHelper mOpenHelper;

    static final int HABBIT = 100;
    static final int HABBIT_WITH_CATEGORY = 101;
    static final int HABBIT_WITH_CATEGORY_AND_DATE = 102;
    static final int CATEGORY = 300;

    private static final SQLiteQueryBuilder sHabitByDateSettingQueryBuilder;

    static {
        sHabitByDateSettingQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //habbit INNER JOIN category ON habbit.category_id = category_id
        sHabitByDateSettingQueryBuilder.setTables(
                HabbitContract.HabbitEntry.TABLE_NAME + " INNER JOIN " +
                        HabbitContract.CategoryEntry.TABLE_NAME +
                        " ON " + HabbitContract.HabbitEntry.TABLE_NAME +
                        "." + HabbitContract.HabbitEntry.COLUMN_LOC_KEY +
                        " = " + HabbitContract.CategoryEntry.TABLE_NAME +
                        "." + HabbitContract.CategoryEntry._ID);
    }

    //category.category_type = ?
    private static final String sCategorySettingSelection =
            HabbitContract.CategoryEntry.TABLE_NAME+
                    "." + HabbitContract.CategoryEntry.COLUMN_CATEGORY + " = ? ";

    //category.category_type = ? AND date >= ?
    private static final String sCategorySettingWithStartDateSelection =
            HabbitContract.CategoryEntry.TABLE_NAME+
                    "." + HabbitContract.CategoryEntry.COLUMN_CATEGORY + " = ? AND " +
                    HabbitContract.HabbitEntry.COLUMN_DATE + " >= ? ";

    //category.category_type = ? AND date = ?
    private static final String sCategorySettingAndDaySelection =
            HabbitContract.CategoryEntry.TABLE_NAME +
                    "." + HabbitContract.CategoryEntry.COLUMN_CATEGORY + " = ? AND " +
                    HabbitContract.HabbitEntry.COLUMN_DATE + " = ? ";

    private Cursor getHabbitByCategorySetting(Uri uri, String[] projection, String sortOrder) {
        String categorySetting = HabbitContract.HabbitEntry.getCategorySettingFromUri(uri);
        long startDate = HabbitContract.HabbitEntry.getStartDateFromUri(uri);

        String[] selectionArgs;
        String selection;

        if (startDate == 0) {
            selection = sCategorySettingSelection;
            selectionArgs = new String[]{categorySetting};
        } else {
            selectionArgs = new String[]{categorySetting, Long.toString(startDate)};
            selection = sCategorySettingWithStartDateSelection;
        }

        return sHabitByDateSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getHabbitByCategorySettingAndDate(
            Uri uri, String[] projection, String sortOrder) {
        String categorySetting = HabbitContract.HabbitEntry.getCategorySettingFromUri(uri);
        long date = HabbitContract.HabbitEntry.getDateFromUri(uri);

        return sHabitByDateSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sCategorySettingAndDaySelection,
                new String[]{categorySetting, Long.toString(date)},
                null,
                null,
                sortOrder
        );
    }


//    This UriMatcher willmatch each URI to the HABBIT, HABBIT_WITH_CATEGORY, HABBIT_WITH_CATEGORY_AND_DATE,
//    and CATEGORY integer constants defined above.
    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = HabbitContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, HabbitContract.PATH_HABBIT, HABBIT);
        matcher.addURI(authority, HabbitContract.PATH_HABBIT + "/*", HABBIT_WITH_CATEGORY);
        matcher.addURI(authority, HabbitContract.PATH_HABBIT + "/*/#", HABBIT_WITH_CATEGORY_AND_DATE);

        matcher.addURI(authority, HabbitContract.PATH_CATEGORY, CATEGORY);
        return matcher;
    }

//    We  create a new HabbitDbHelper for later use here.
    @Override
    public boolean onCreate() {
        mOpenHelper = new HabbitDbHelper(getContext());
        return true;
    }

    //Here's the getType function that uses the UriMatcher.
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

             case HABBIT_WITH_CATEGORY_AND_DATE:
                return HabbitContract.HabbitEntry.CONTENT_ITEM_TYPE;
            case HABBIT_WITH_CATEGORY:
                return HabbitContract.HabbitEntry.CONTENT_TYPE;
            case HABBIT:
                return HabbitContract.HabbitEntry.CONTENT_TYPE;
            case CATEGORY:
                return HabbitContract.CategoryEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "habbit/*/*"
            case HABBIT_WITH_CATEGORY_AND_DATE:
            {
                retCursor = getHabbitByCategorySettingAndDate(uri, projection, sortOrder);
                break;
            }
            // "habbit/*"
            case HABBIT_WITH_CATEGORY: {
                retCursor = getHabbitByCategorySetting(uri, projection, sortOrder);
                break;
            }
            // "habbit"
            case HABBIT: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        HabbitContract.HabbitEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "category"
            case CATEGORY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        HabbitContract.CategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    //Add the ability to insert Categories to the implementation of this function.

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case HABBIT: {
                normalizeDate(values);
                long _id = db.insert(HabbitContract.HabbitEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = HabbitContract.HabbitEntry.buildHabbitUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case CATEGORY: {
                long _id = db.insert(HabbitContract.CategoryEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = HabbitContract.CategoryEntry.buildCategoryUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case HABBIT:
                rowsDeleted = db.delete(
                        HabbitContract.HabbitEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CATEGORY:
                rowsDeleted = db.delete(
                        HabbitContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(HabbitContract.HabbitEntry.COLUMN_DATE)) {
            long dateValue = values.getAsLong(HabbitContract.HabbitEntry.COLUMN_DATE);
            values.put(HabbitContract.HabbitEntry.COLUMN_DATE, HabbitContract.normalizeDate(dateValue));
        }
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case HABBIT:
                normalizeDate(values);
                rowsUpdated = db.update(HabbitContract.HabbitEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CATEGORY:
                rowsUpdated = db.update(HabbitContract.CategoryEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HABBIT:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(HabbitContract.HabbitEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
