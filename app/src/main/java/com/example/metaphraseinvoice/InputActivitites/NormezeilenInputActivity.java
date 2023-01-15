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
import com.example.metaphraseinvoice.InputActivitites.Adapter.NormzelienItemAdapter;
import com.example.metaphraseinvoice.InputActivitites.Model.DezimalModel;
import com.example.metaphraseinvoice.InputActivitites.Model.NormezelienModel;
import com.example.metaphraseinvoice.Invoices.AngenbotActivity;
import com.example.metaphraseinvoice.Invoices.DezimalRechnung;
import com.example.metaphraseinvoice.Invoices.DolmetscherRechnung;
import com.example.metaphraseinvoice.Invoices.NormzeilenRechnung;
import com.example.metaphraseinvoice.Invoices.PrivatkundenRechnung;
import com.example.metaphraseinvoice.MainActivity;
import com.example.metaphraseinvoice.R;
import com.google.gson.Gson;

import java.text.Normalizer;
import java.util.ArrayList;

public class NormezeilenInputActivity extends AppCompatActivity {
    RecyclerView rv_norm;
    EditText ed_desc_norm, ed_word_norm, ed_price_norm, ed_advancePayment_norm;
    Button btn_addItem_norm, btn_checkout_norm;
    NormzelienItemAdapter normzelienItemAdapter;
    ArrayList<NormezelienModel> normezelienModelArrayList = new ArrayList<>();
    String fromIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normezeilen_input);
        fromIntent = getIntent().getStringExtra("from");
        initComponents();


        btn_addItem_norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (normezelienModelArrayList.size() < 10)
                    validateFields();
                else
                    Toast.makeText(NormezeilenInputActivity.this, "\n" + "Sie können nur 10 Elemente gleichzeitig hinzufügen!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_checkout_norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (normezelienModelArrayList.size() > 0)
                    showAdvancePaymentDialog();
                else
                    Toast.makeText(NormezeilenInputActivity.this, "\n" +
                            "Fügen Sie mindestens einen Artikel hinzu!", Toast.LENGTH_SHORT).show();
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
        ed_advancePayment_norm = dialog.findViewById(R.id.ed_advancePayment);
        Button btn_checkout = dialog.findViewById(R.id.btn_checkout);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Gson gson = new Gson();
                    String jsonCars = gson.toJson(normezelienModelArrayList);
                    Intent intent = new Intent(NormezeilenInputActivity.this, NormzeilenRechnung.class);
                    intent.putExtra("array", jsonCars);
                    intent.putExtra("advance", ed_advancePayment_norm.getText().toString());
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
        if (ed_desc_norm.getText().toString().isEmpty()) {
            ed_desc_norm.setError("\n" +"Beschreibung erforderlich!");
        } else if (ed_word_norm.getText().toString().isEmpty()) {
            ed_word_norm.setError("Worte erforderlich!");
        } else if (ed_price_norm.getText().toString().isEmpty()) {
            ed_price_norm.setError("\n" + "Preis erforderlich!");
        } else {
            addItem();
        }
    }

    private void addItem() {
        normezelienModelArrayList.add(new NormezelienModel(
                ed_desc_norm.getText().toString(),
                ed_word_norm.getText().toString(),
                ed_price_norm.getText().toString())
        );
        normzelienItemAdapter = new NormzelienItemAdapter(NormezeilenInputActivity.this, normezelienModelArrayList);
        rv_norm.setAdapter(normzelienItemAdapter);

        ed_desc_norm.setText("");
        ed_word_norm.setText("");
        ed_price_norm.setText("");
    }

    private void initComponents() {
        rv_norm = findViewById(R.id.rv_norm);
        rv_norm.setLayoutManager(new LinearLayoutManager(this));
        ed_desc_norm = findViewById(R.id.ed_description_norm);
        ed_word_norm = findViewById(R.id.ed_word_norm);
        ed_price_norm = findViewById(R.id.ed_price_norm);
        btn_addItem_norm = findViewById(R.id.btn_additem_norm);
        btn_checkout_norm = findViewById(R.id.btn_checkout_norm);
    }
}
