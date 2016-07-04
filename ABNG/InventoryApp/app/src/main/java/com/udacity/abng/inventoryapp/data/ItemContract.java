package com.udacity.abng.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by yul on 29.06.16.
 */
public class ItemContract {

    // The "Content authority" is a name for the entire content provider
    public static final String CONTENT_AUTHORITY = "com.udacity.abng.inventoryapp";

    /* Inner class that defines the table contents */
    public static abstract class ItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "ITEMS";
        public static final String ITEM_NAME = "NAME";
        public static final String ITEM_DESC = "Description";
        public static final String ITEM_IMAGE = "Image";
        public static final String ITEM_PRICE = "Price";
        public static final String ITEM_QUANT = "Quantity";

    }


}
