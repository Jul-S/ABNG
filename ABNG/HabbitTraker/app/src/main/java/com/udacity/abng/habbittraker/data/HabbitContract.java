package com.udacity.abng.habbittraker.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by yul on 29.06.16.
 */
public class HabbitContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.
    public static final String CONTENT_AUTHORITY = "com.udacity.abng.habbittraker";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_HABBIT = "habbit";
    public static final String PATH_CATEGORY = "category";


    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    /* Inner class that defines the table contents of the habbits tracked table */
    public static final class HabbitEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_HABBIT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HABBIT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HABBIT;
        public static final String TABLE_NAME = "habbit";

        // Column with the foreign key into the category table.
        public static final String COLUMN_LOC_KEY = "category_id";

        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_DATE = "date";

        // Short description and long description of the habbits
        public static final String COLUMN_SHORT_DESC = "short_desc";
        // If Habbit tracking done not done
        public static final String COLUMN_DONE = "done";

        public static Uri buildHabbitUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    /* Inner class that defines the table contents of the habbit category table
    * each habbit goes to special category - "Mind", "Body", SelfImprovement"..etc */
    public static final class CategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_HABBIT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HABBIT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HABBIT;

        // Table name
        public static final String TABLE_NAME = "category";

        // The Category string is what type of category does habbit belong
        public static final String COLUMN_CATEGORY = "type";

        // Short desc habbit string
        public static final String COLUMN_HABBIT_DESC = "habbit_desc";

        public static Uri buildCategoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
