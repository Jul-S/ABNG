package com.udacity.abng.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.udacity.abng.inventoryapp.data.ItemDbHelper;
import com.udacity.abng.inventoryapp.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity.log";
    private ItemDbHelper mHelper;

    private ListView mItemListView;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new ItemDbHelper(this);
        mItemListView = (ListView) findViewById(R.id.list);
        TextView emptyTV = (TextView) findViewById(R.id.empty);

        if (mHelper.getItemsCount() == 0) {
            emptyTV.setVisibility(View.VISIBLE);
            mItemListView.setEmptyView(emptyTV);
//           fillDefaultDB();
        }
        updateUI();

        mItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    int id = Integer.parseInt(cursor.getString(0));
                    Log.d(TAG, "Item cursor.getString(0)= " + cursor.getString(0));
                    Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                    detailsIntent.putExtra("Item ID", id);
                    // Start the new activity
                    startActivity(detailsIntent);
                }
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUI();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    //use this to put default Item in your DB
    private void fillDefaultDB() {
        // Create a list of products
        final ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item(getString(R.string.item1_name), getString(R.string.item1_desc),
                BitmapFactory.decodeResource(getResources(), R.drawable.item1), Double.parseDouble(getString(R.string.item1_price)), 0));

        for (int i = 0; i < items.size(); i++) {
            mHelper.addItem(items.get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_product:
                Intent addIntent = new Intent(this, AddProductDialog.class);
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Start the new activity
                startActivity(addIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        // Get access to the underlying writeable database
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // Query for items from the database and get a cursor back
        Cursor itemCursor = db.rawQuery("SELECT  * FROM ITEMS", null);
        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.list);
        // Setup cursor adapter using cursor from last step
        ItemListAdapter itemAdapter = new ItemListAdapter(this, itemCursor);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(itemAdapter);

    }

}


