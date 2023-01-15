package com.example.metaphraseinvoice.InputActivitites;

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

import com.example.metaphraseinvoice.InputActivitites.Adapter.DezimalItemAdapter;
import com.example.metaphraseinvoice.InputActivitites.Model.DezimalModel;
import com.example.metaphraseinvoice.Invoices.AngenbotActivity;
import com.example.metaphraseinvoice.Invoices.DezimalRechnung;
import com.example.metaphraseinvoice.Invoices.DolmetscherRechnung;
import com.example.metaphraseinvoice.Invoices.NormzeilenRechnung;
import com.example.metaphraseinvoice.Invoices.PrivatkundenRechnung;
import com.example.metaphraseinvoice.ItemAdapter;
import com.example.metaphraseinvoice.ItemModel;
import com.example.metaphraseinvoice.MainActivity;
import com.example.metaphraseinvoice.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DezimalINputActivity extends AppCompatActivity {
    RecyclerView rv_dezimal;
    EditText ed_desc_dezimal, ed_hours_dezimal, ed_minute_dezimal, ed_price_dezimal, ed_advancePayment_dezimal;
    Button btn_addItem_dezimal, btn_checkout_dezimal;
    DezimalItemAdapter dezimalItemAdapter;
    ArrayList<DezimalModel> dezimalModelArrayList = new ArrayList<>();
    String fromIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dezimal_input);
        fromIntent = getIntent().getStringExtra("from");
        initComponents();
        btn_addItem_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dezimalModelArrayList.size() < 10)
                    validateFields();
                else
                    Toast.makeText(DezimalINputActivity.this, "Sie können nur 10 Elemente gleichzeitig hinzufügen!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_checkout_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dezimalModelArrayList.size() > 0)
                    showAdvancePaymentDialog();
                else
                    Toast.makeText(DezimalINputActivity.this, "Fügen Sie mindestens einen Artikel hinzu!", Toast.LENGTH_SHORT).show();
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
        ed_advancePayment_dezimal = dialog.findViewById(R.id.ed_advancePayment);
        Button btn_checkout = dialog.findViewById(R.id.btn_checkout);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Gson gson = new Gson();
                    String jsonCars = gson.toJson(dezimalModelArrayList);
                    Intent intent = new Intent(DezimalINputActivity.this, DezimalRechnung.class);
                    intent.putExtra("array", jsonCars);
                    intent.putExtra("advance", ed_advancePayment_dezimal.getText().toString());
                    startActivity(intent);
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
        if (ed_desc_dezimal.getText().toString().isEmpty()) {
            ed_desc_dezimal.setError("\n" +
                    "Beschreibung erforderlich!");
        } else if (ed_hours_dezimal.getText().toString().isEmpty()) {
            ed_hours_dezimal.setError("\n" + "Stunden erforderlich!");
        } else if (ed_minute_dezimal.getText().toString().isEmpty()) {
            ed_minute_dezimal.setError("\n" + "Minute erforderlich!");
        } else if (ed_price_dezimal.getText().toString().isEmpty()) {
            ed_price_dezimal.setError("\n" + "Preis erforderlich!");
        } else {
            addItem();
        }
    }

    private void addItem() {
        dezimalModelArrayList.add(new DezimalModel(
                ed_desc_dezimal.getText().toString(),
                ed_hours_dezimal.getText().toString(),
                ed_minute_dezimal.getText().toString(),
                ed_price_dezimal.getText().toString())
        );
        dezimalItemAdapter = new DezimalItemAdapter(DezimalINputActivity.this, dezimalModelArrayList);
        rv_dezimal.setAdapter(dezimalItemAdapter);

        ed_desc_dezimal.setText("");
        ed_hours_dezimal.setText("");
        ed_minute_dezimal.setText("");
        ed_price_dezimal.setText("");
    }

    private void initComponents() {
        rv_dezimal = findViewById(R.id.rv_dezimal);
        rv_dezimal.setLayoutManager(new LinearLayoutManager(this));
        ed_desc_dezimal = findViewById(R.id.ed_desc_dezimal);
        ed_hours_dezimal = findViewById(R.id.ed_hours_dezimal);
        ed_minute_dezimal = findViewById(R.id.ed_minute_dezimal);
        ed_price_dezimal = findViewById(R.id.ed_price_dezimal);
        ed_advancePayment_dezimal = findViewById(R.id.ed_advancePayment_dezimal);
        btn_addItem_dezimal = findViewById(R.id.btn_additem_dezimal);
        btn_checkout_dezimal = findViewById(R.id.btn_checkout_dezimal);
    }
}
