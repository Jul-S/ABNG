package com.udacity.abng.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.abng.inventoryapp.data.ItemContract;
import com.udacity.abng.inventoryapp.data.ItemDbHelper;
import com.udacity.abng.inventoryapp.model.Item;

public class ItemListAdapter extends CursorAdapter {
    private final Context context;
    //	private final List<Item> items;


    public ItemListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_list, parent, false);
        bindView(v, context, cursor);
        return v;
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        // Find fields to populate in inflated template
        TextView prodName = (TextView) view.findViewById(R.id.il_prod_name);
        //       TextView proDesc = (TextView) view.findViewById(R.id.il_prod_desc);
        TextView proPrice = (TextView) view.findViewById(R.id.il_prod_price);
        TextView proQuantity = (TextView) view.findViewById(R.id.il_prod_quant);
        ImageView prodImage = (ImageView) view.findViewById(R.id.il_prod_image);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.ITEM_NAME));
        //       String desc = cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.ITEM_DESC));
        String quant = cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.ITEM_QUANT));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.ITEM_PRICE));
        byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry.ITEM_IMAGE));

        // Populate fields with extracted properties
        prodName.setText(name);
        //      proDesc.setText(desc);
        proPrice.setText(price);
        proQuantity.setText(quant);
        prodImage.setImageBitmap(Utility.getPhoto(img));

        //Getting row id
        final int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(ItemContract.ItemEntry._ID));

        Button sellBut = (Button) view.findViewById(R.id.sellBtn);

        sellBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDbHelper mHelper = new ItemDbHelper(context);

                Item item = mHelper.getItemById(rowId);

                int quant = item.getQuantity();
                if (quant > 0) {
                    quant = quant - 1;
                }
                item.setQuantity(quant);

                mHelper.updateItem(item);

                Cursor itemCursor = mHelper.getAllItems();
                swapCursor(itemCursor);
            }
        });

    }


}
