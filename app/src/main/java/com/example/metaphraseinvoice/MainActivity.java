package com.example.metaphraseinvoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.metaphraseinvoice.Invoices.AngenbotActivity;
import com.example.metaphraseinvoice.Invoices.DolmetscherRechnung;
import com.example.metaphraseinvoice.Invoices.PrivatkundenRechnung;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_item;
    ItemAdapter itemAdapter;
    ArrayList<ItemModel> itemModelArrayList = new ArrayList<>();
    EditText et_desc, et_quantity, et_price, et_advance_payments;

    Button btn_add_item, btn_next;
    String fromIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromIntent = getIntent().getStringExtra("from");
        initComponents();

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModelArrayList.size() < 10)
                    validateFields();
                else
                    Toast.makeText(MainActivity.this, "Sie können nur 10 Elemente gleichzeitig hinzufügen!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemModelArrayList.size() > 0)
                    showAdvancePaymentDialog();
                else
                    Toast.makeText(MainActivity.this, "Fügen Sie mindestens einen Artikel hinzu!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showAdvancePaymentDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_advance_payment);
        dialog.setTitle("Title...");
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_advance_payments = dialog.findViewById(R.id.ed_advancePayment);
        Button btn_checkout = dialog.findViewById(R.id.btn_checkout);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (fromIntent.equalsIgnoreCase("tv_DOLMETSCHER")) {
                        Gson gson = new Gson();
                        String jsonCars = gson.toJson(itemModelArrayList);
                        Intent intent = new Intent(MainActivity.this, DolmetscherRechnung.class);
                        intent.putExtra("array", jsonCars);
                        intent.putExtra("advance", et_advance_payments.getText().toString());
                        startActivity(intent);
                    } else if (fromIntent.equalsIgnoreCase("tv_angebot")) {
                        Gson gson = new Gson();
                        String jsonCars = gson.toJson(itemModelArrayList);
                        Intent intent = new Intent(MainActivity.this, AngenbotActivity.class);
                        intent.putExtra("array", jsonCars);
                        intent.putExtra("advance", et_advance_payments.getText().toString());
                        startActivity(intent);

                    } else {
                        Gson gson = new Gson();
                        String jsonCars = gson.toJson(itemModelArrayList);
                        Intent intent = new Intent(MainActivity.this, PrivatkundenRechnung.class);
                        intent.putExtra("array", jsonCars);
                        intent.putExtra("advance", et_advance_payments.getText().toString());
                        startActivity(intent);
                    }
                    dialog.dismiss();
            }
        });
        LinearLayout close_dialog = dialog.findViewById(R.id.close_donation);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void validateFields() {
        if (et_desc.getText().toString().isEmpty()) {
            et_desc.setError("\n" +
                    "Beschreibung erforderlich!");
        } else if (et_quantity.getText().toString().isEmpty()) {
            et_quantity.setError("\n" +
                    "Benoetigte Menge!");
        } else if (et_price.getText().toString().isEmpty()) {
            et_price.setError("\n" + "Preis erforderlich!");
        }
        /*else if(et_advance_payments.getText().toString().isEmpty()){
            et_advance_payments.setError("advance payment required!");
        }*/
        else {
            addItem();
        }
    }

    private void addItem() {
        itemModelArrayList.add(new ItemModel(
                        et_desc.getText().toString(),
                        et_quantity.getText().toString(),
                        et_price.getText().toString()
                )
        );
        itemAdapter = new ItemAdapter(MainActivity.this, itemModelArrayList);
        rv_item.setAdapter(itemAdapter);

        et_desc.setText("");
        et_quantity.setText("");
        et_price.setText("");

    }

    private void initComponents() {
        rv_item = findViewById(R.id.rv_item);
        rv_item.setLayoutManager(new LinearLayoutManager(this));
        et_desc = findViewById(R.id.et_desc);
        et_quantity = findViewById(R.id.et_quantity);
        et_price = findViewById(R.id.et_price);
        btn_add_item = findViewById(R.id.btn_add_item);
        btn_next = findViewById(R.id.btn_next);
    }
}
