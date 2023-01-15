package com.example.metaphraseinvoice.HeaderFooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.metaphraseinvoice.R;

public class HeaderNormzeilenActivity extends AppCompatActivity {
    Button btn_savenormezeilen;
    EditText ed_1_header_normezeilen,ed_2_header_normezeilen,ed_3_header_normezeilen,ed_4_header_normezeilen,ed_5_header_normezeilen,
            ed_6_header_normezeilen,ed_7_header_normezeilen,ed_8_header_normezeilen,ed_9_header_normezeilen,ed_10_header_normezeilen
            ,ed_11_header_normezeilen,ed_12_header_normezeilen,ed_13_header_normezeilen,ed_14_header_normezeilen,ed_15_header_normezeilen,ed_16_header_normezeilen
            ,tv17,tv18;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_normzeilen);
        ed_1_header_normezeilen = findViewById(R.id.tv1);
        ed_2_header_normezeilen = findViewById(R.id.tv2);
        ed_3_header_normezeilen = findViewById(R.id.tv3);
        ed_4_header_normezeilen = findViewById(R.id.tv4);
        ed_5_header_normezeilen = findViewById(R.id.tv5);
        ed_6_header_normezeilen = findViewById(R.id.tv6);
        ed_7_header_normezeilen = findViewById(R.id.tv7);
        ed_8_header_normezeilen = findViewById(R.id.tv8);
        ed_9_header_normezeilen = findViewById(R.id.tv9);
        ed_10_header_normezeilen = findViewById(R.id.tv10);
        ed_11_header_normezeilen = findViewById(R.id.tv11);
        ed_12_header_normezeilen = findViewById(R.id.tv12);
        ed_13_header_normezeilen = findViewById(R.id.tv13);
        ed_14_header_normezeilen = findViewById(R.id.tv14);
        ed_15_header_normezeilen = findViewById(R.id.tv15);
        ed_16_header_normezeilen = findViewById(R.id.tv16);
        tv17 = findViewById(R.id.tv17);
        tv18 = findViewById(R.id.tv18);
        btn_savenormezeilen = findViewById(R.id.btn_savenorm);
        //to retreive data from shared prefrence
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String st1 = preferences.getString("11", "Hauptsitz");
        String st2 = preferences.getString("12", "Metaphrase");
        String st3 = preferences.getString("13", "Dolmetscher und Übersetzungsbüro");
        String st4 = preferences.getString("14", "Avenue Imam Muslim Ibn El Hajjej,");
        String st5 = preferences.getString("15", "4051 Sousse Sahloul, Tunisia");
        String st6 = preferences.getString("16", "Auftragsannahmestelle");
        String st7 = preferences.getString("17", "Metaphrase");
        String st8 = preferences.getString("18", "Dolmetscher und Übersetzungsbüro");
        String st9 = preferences.getString("19", "Berlinerstr. 300b");
        String st10 = preferences.getString("20", "63065 Offenbach am Main");
        String st11 = preferences.getString("21", "Kundenadresse");
        String st12 = preferences.getString("22", "Herr");
        String st13 = preferences.getString("23", "Youssef Ahmo");
        String st14 = preferences.getString("24", "Altenbrunch strt 11");
        String st15 = preferences.getString("25", "27472");
        String st16 = preferences.getString("26", "Rechnung");
        String st17 = preferences.getString("27", "01 2345 678");
        String st18 = preferences.getString("100", "20/11/2021");
        ed_1_header_normezeilen.setText(st1);
        ed_2_header_normezeilen.setText(st2);
        ed_3_header_normezeilen.setText(st3);
        ed_4_header_normezeilen.setText(st4);
        ed_5_header_normezeilen.setText(st5);
        ed_6_header_normezeilen.setText(st6);
       ed_7_header_normezeilen.setText(st7);
        ed_8_header_normezeilen.setText(st8);
        ed_9_header_normezeilen.setText(st9);
              ed_10_header_normezeilen.setText(st10);
        ed_11_header_normezeilen.setText(st11);
        ed_12_header_normezeilen.setText(st12);
        ed_13_header_normezeilen.setText(st13);
        ed_14_header_normezeilen.setText(st14);
        ed_15_header_normezeilen.setText(st15);
        ed_16_header_normezeilen.setText(st16);
        tv17.setText(st17);
        tv18.setText(st18);
        btn_savenormezeilen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ed_1_header_norm = ed_1_header_normezeilen.getText().toString();
                String ed_2_header_norm = ed_2_header_normezeilen.getText().toString();
                String ed_3_header_norm = ed_3_header_normezeilen.getText().toString();
                String ed_4_header_norm = ed_4_header_normezeilen.getText().toString();
                String ed_5_header_norm = ed_5_header_normezeilen.getText().toString();
                String ed_6_header_norm = ed_6_header_normezeilen.getText().toString();
                String ed_7_header_norm = ed_7_header_normezeilen.getText().toString();
                String ed_8_header_norm = ed_8_header_normezeilen.getText().toString();
                String ed_9_header_norm = ed_9_header_normezeilen.getText().toString();
                String ed_10_header_norm = ed_10_header_normezeilen.getText().toString();
                String ed_11_header_norm = ed_11_header_normezeilen.getText().toString();
                String ed_12_header_norm = ed_12_header_normezeilen.getText().toString();
                String ed_13_header_norm = ed_13_header_normezeilen.getText().toString();
                String ed_14_header_norm = ed_14_header_normezeilen.getText().toString();
                String ed_15_header_norm = ed_15_header_normezeilen.getText().toString();
                String ed_16_header_norm = ed_16_header_normezeilen.getText().toString();
                String ed_17_header_norm = tv17.getText().toString();
                String ed18 = tv18.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HeaderNormzeilenActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("28",ed_1_header_norm);
                editor.putString("29",ed_2_header_norm);
                editor.putString("30",ed_3_header_norm);
                editor.putString("31",ed_4_header_norm);
                editor.putString("32",ed_5_header_norm);
                editor.putString("33",ed_6_header_norm);
                editor.putString("34",ed_7_header_norm);
                editor.putString("35",ed_8_header_norm);
                editor.putString("36",ed_9_header_norm);
                editor.putString("37",ed_10_header_norm);
                editor.putString("38",ed_11_header_norm);
                editor.putString("39",ed_12_header_norm);
                editor.putString("40",ed_13_header_norm);
                editor.putString("41",ed_14_header_norm);
                editor.putString("42",ed_15_header_norm);
                editor.putString("43",ed_16_header_norm);
                editor.putString("44",ed_17_header_norm);
                editor.putString("300",ed18);
                editor.apply();
                Intent intent = new Intent();
                intent.putExtra("result1",ed_1_header_norm);
                intent.putExtra("result2",ed_2_header_norm);
                intent.putExtra("result3",ed_3_header_norm);
                intent.putExtra("result4",ed_4_header_norm);
                intent.putExtra("result5",ed_5_header_norm);
                intent.putExtra("result6",ed_6_header_norm);
                intent.putExtra("result7",ed_7_header_norm);
                intent.putExtra("result8",ed_8_header_norm);
                intent.putExtra("result9",ed_9_header_norm);
                intent.putExtra("result10",ed_10_header_norm);
                intent.putExtra("result11",ed_11_header_norm);
                intent.putExtra("result12",ed_12_header_norm);
                intent.putExtra("result13",ed_13_header_norm);
                intent.putExtra("result14",ed_14_header_norm);
                intent.putExtra("result15",ed_15_header_norm);
                intent.putExtra("result16",ed_16_header_norm);
                intent.putExtra("result17",ed_17_header_norm);
                intent.putExtra("date",ed18);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
