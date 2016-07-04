package com.udacity.abng.inventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.abng.inventoryapp.data.ItemDbHelper;
import com.udacity.abng.inventoryapp.model.Item;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "Details.log";
    private ItemDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mHelper = new ItemDbHelper(this);

        int id = getIntent().getIntExtra("Item ID", 0);
        Log.d(TAG, "Details gain id= " + id);

        final Item item = mHelper.getItemById(id);

        updateUI(item);
        final EditText howManyEdTxt = (EditText) findViewById(R.id.prod_howmuch);


        Button incBut = (Button) findViewById(R.id.incremQuant);
        incBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howManyEdTxt.getText().length() > 0) {
                    int howMany = Integer.parseInt(String.valueOf(howManyEdTxt.getText()));
                    int quant = item.getQuantity();
                    quant = quant + howMany;
                    item.setQuantity(quant);
                    mHelper.updateItem(item);
                    updateUI(item);
                } else {
                    Toast.makeText(DetailsActivity.this, "Please, enter how many Items You want to Recieve", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button decBut = (Button) findViewById(R.id.decremQuant);
        decBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howManyEdTxt.getText().length() > 0) {
                    int howMuch = Integer.parseInt(String.valueOf(howManyEdTxt.getText()));
                    int quant = item.getQuantity();
                    quant = quant - howMuch;
                    if (quant >= 0) {
                        item.setQuantity(quant);
                        mHelper.updateItem(item);
                        updateUI(item);
                    } else {
                        Toast.makeText(DetailsActivity.this, "You don`t have enough Items for SALE", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "Please, enter how many Items You want to SELL", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button delBut = (Button) findViewById(R.id.deletBtn);
        delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(DetailsActivity.this)
                        //set message, title, and icon
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete this Item?")
                        .setIcon(R.drawable.delete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                //your deleting code
                                mHelper.deleteItem(item);
                                Intent detailsIntent = new Intent(DetailsActivity.this, MainActivity.class);
                                startActivity(detailsIntent);
                                updateUI(item);

                                dialog.dismiss();
                            }

                        })


                        .setNegativeButton("cancel", null)
                        .create();
                dialog.show();
            }
        });

        Button orderBut = (Button) findViewById(R.id.orderBtn);
        orderBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderInt = new Intent(Intent.ACTION_SEND);
                orderInt.setType("text/html");
                orderInt.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                orderInt.putExtra(Intent.EXTRA_SUBJECT, "Order for" + item.getTitle());
                startActivity(Intent.createChooser(orderInt, "Send Email"));
            }
        });

    }

    private void updateUI(Item item) {
        TextView prodName = (TextView) findViewById(R.id.prod_name);
        TextView proDesc = (TextView) findViewById(R.id.prod_desc);
        TextView proPrice = (TextView) findViewById(R.id.prod_price);
        TextView proQuantity = (TextView) findViewById(R.id.prod_quant);
        ImageView prodImage = (ImageView) findViewById(R.id.prod_image);

        prodName.setText(item.getTitle());
        proDesc.setText(item.getDescription());
        proPrice.setText(Double.toString(item.getPrice()));
        proQuantity.setText(Integer.toString(item.getQuantity()));
        prodImage.setImageBitmap(item.getImageBit());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.close();
    }

}
