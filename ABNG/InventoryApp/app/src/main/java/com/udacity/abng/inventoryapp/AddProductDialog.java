package com.udacity.abng.inventoryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.udacity.abng.inventoryapp.data.ItemDbHelper;
import com.udacity.abng.inventoryapp.model.Item;

import java.io.IOException;

public class AddProductDialog extends AppCompatActivity {
    private static final String TAG = "AddDialog.log";
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);


        final EditText itemNewName = (EditText) findViewById(R.id.product_name_edittext);
        final EditText itemNewDesc = (EditText) findViewById(R.id.product_desc_edittext);
        final EditText itemNewPrice = (EditText) findViewById(R.id.product_price_edittext);
        final EditText itemNewQuantity = (EditText) findViewById(R.id.product_quant_edittext);
        final ImageView itemNewImage = (ImageView) findViewById(R.id.product_new_image);

        Button btnLoadImage = (Button) findViewById(R.id.product_load_btn);
        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                Log.d(TAG, "startActivityForResult");
            }
        });
        Button btnAdd = (Button) findViewById(R.id.add_btn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((itemNewName.getText().toString().length() == 0) ||
                        (itemNewDesc.getText().toString().length() == 0) ||
                        (itemNewPrice.getText().toString().length() == 0) ||
                        (itemNewQuantity.getText().toString().length() == 0)) {
                    Toast.makeText(getApplicationContext(), "Fill in all product fields!", Toast.LENGTH_LONG).show();
                } else {
                    String name = itemNewName.getText().toString();
                    String desc = itemNewDesc.getText().toString();
                    int quant = Integer.parseInt(itemNewQuantity.getText().toString());
                    double price = Double.parseDouble(itemNewPrice.getText().toString());
                    Bitmap imgBit = (((BitmapDrawable) itemNewImage.getDrawable()).getBitmap());

                    Item itemNew = new Item(name, desc, imgBit, price, quant);
                    ItemDbHelper mHelper = new ItemDbHelper(getApplicationContext());
                    mHelper.addItem(itemNew);

                    Intent mainIntent = new Intent(AddProductDialog.this, MainActivity.class);
                    // Start the new activity
                    startActivity(mainIntent);
                }
            }
        });

        Button btnCancel = (Button) findViewById(R.id.cancel_btn);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult" + resultCode);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.product_new_image);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}