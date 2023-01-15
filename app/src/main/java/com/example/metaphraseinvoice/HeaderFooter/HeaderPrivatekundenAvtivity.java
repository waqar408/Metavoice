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

public class HeaderPrivatekundenAvtivity extends AppCompatActivity {
    Button btn_save_private;
    EditText ed_1_header_dezimal,ed_2_header_dezimal,ed_3_header_dezimal,ed_4_header_dezimal,ed_5_header_dezimal,
            ed_6_header_dezimal,ed_7_header_dezimal,ed_8_header_dezimal,ed_9_header_dezimal,ed_10_header_dezimal
            ,ed_11_header_dezimal,ed_12_header_dezimal,ed_13_header_dezimal,ed_14_header_dezimal,ed_15_header_dezimal,ed_16_header_dezimal
            ,tv17,tv18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_privatekunden_avtivity);
        ed_1_header_dezimal = findViewById(R.id.tv1);
        ed_2_header_dezimal = findViewById(R.id.tv2);
        ed_3_header_dezimal = findViewById(R.id.tv3);
        ed_4_header_dezimal = findViewById(R.id.tv4);
        ed_5_header_dezimal = findViewById(R.id.tv5);
        ed_6_header_dezimal = findViewById(R.id.tv6);
        ed_7_header_dezimal = findViewById(R.id.tv7);
        ed_8_header_dezimal = findViewById(R.id.tv8);
        ed_9_header_dezimal = findViewById(R.id.tv9);
        ed_10_header_dezimal = findViewById(R.id.tv10);
        ed_11_header_dezimal = findViewById(R.id.tv11);
        ed_12_header_dezimal = findViewById(R.id.tv12);
        ed_13_header_dezimal = findViewById(R.id.tv13);
        ed_14_header_dezimal = findViewById(R.id.tv14);
        ed_15_header_dezimal = findViewById(R.id.tv15);
        ed_16_header_dezimal = findViewById(R.id.tv16);
        tv17 = findViewById(R.id.tv17);
        tv18 = findViewById(R.id.tv18);
        btn_save_private = findViewById(R.id.btn_save_private);
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
        ed_1_header_dezimal.setText(st1);
        ed_2_header_dezimal.setText(st2);
        ed_3_header_dezimal.setText(st3);
        ed_4_header_dezimal.setText(st4);
        ed_5_header_dezimal.setText(st5);
        ed_6_header_dezimal.setText(st6);
        ed_7_header_dezimal.setText(st7);
        ed_8_header_dezimal.setText(st8);
        ed_9_header_dezimal.setText(st9);
        ed_10_header_dezimal.setText(st10);
        ed_11_header_dezimal.setText(st11);
        ed_12_header_dezimal.setText(st12);
        ed_13_header_dezimal.setText(st13);
        ed_14_header_dezimal.setText(st14);
        ed_15_header_dezimal.setText(st15);
        ed_16_header_dezimal.setText(st16);
        tv17.setText(st17);
        tv18.setText(st18);
        btn_save_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ed_1_header_dezima = ed_1_header_dezimal.getText().toString();
                String ed_2_header_dezima = ed_2_header_dezimal.getText().toString();
                String ed_3_header_dezima = ed_3_header_dezimal.getText().toString();
                String ed_4_header_dezima = ed_4_header_dezimal.getText().toString();
                String ed_5_header_dezima = ed_5_header_dezimal.getText().toString();
                String ed_6_header_dezima = ed_6_header_dezimal.getText().toString();
                String ed_7_header_dezima = ed_7_header_dezimal.getText().toString();
                String ed_8_header_dezima = ed_8_header_dezimal.getText().toString();
                String ed_9_header_dezima = ed_9_header_dezimal.getText().toString();
                String ed_10_header_dezima = ed_10_header_dezimal.getText().toString();
                String ed_11_header_dezima = ed_11_header_dezimal.getText().toString();
                String ed_12_header_dezima = ed_12_header_dezimal.getText().toString();
                String ed_13_header_dezima = ed_13_header_dezimal.getText().toString();
                String ed_14_header_dezima = ed_14_header_dezimal.getText().toString();
                String ed_15_header_dezima = ed_15_header_dezimal.getText().toString();
                String ed_16_header_dezima = ed_16_header_dezimal.getText().toString();
                String ed_17_header_dezima = tv18.getText().toString();
                String ed18 = tv18.getText().toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HeaderPrivatekundenAvtivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("45",ed_1_header_dezima);
                editor.putString("46",ed_2_header_dezima);
                editor.putString("47",ed_3_header_dezima);
                editor.putString("48",ed_4_header_dezima);
                editor.putString("49",ed_5_header_dezima);
                editor.putString("50",ed_6_header_dezima);
                editor.putString("51",ed_7_header_dezima);
                editor.putString("52",ed_8_header_dezima);
                editor.putString("53",ed_9_header_dezima);
                editor.putString("54",ed_10_header_dezima);
                editor.putString("55",ed_11_header_dezima);
                editor.putString("56",ed_12_header_dezima);
                editor.putString("57",ed_13_header_dezima);
                editor.putString("58",ed_14_header_dezima);
                editor.putString("59",ed_15_header_dezima);
                editor.putString("60",ed_16_header_dezima);
                editor.putString("61",ed_17_header_dezima);
                editor.putString("400",ed18);
                editor.apply();
                Intent intent = new Intent();
                intent.putExtra("result1",ed_1_header_dezima);
                intent.putExtra("result2",ed_2_header_dezima);
                intent.putExtra("result3",ed_3_header_dezima);
                intent.putExtra("result4",ed_4_header_dezima);
                intent.putExtra("result5",ed_5_header_dezima);
                intent.putExtra("result6",ed_6_header_dezima);
                intent.putExtra("result7",ed_7_header_dezima);
                intent.putExtra("result8",ed_8_header_dezima);
                intent.putExtra("result9",ed_9_header_dezima);
                intent.putExtra("result10",ed_10_header_dezima);
                intent.putExtra("result11",ed_11_header_dezima);
                intent.putExtra("result12",ed_12_header_dezima);
                intent.putExtra("result13",ed_13_header_dezima);
                intent.putExtra("result14",ed_14_header_dezima);
                intent.putExtra("result15",ed_15_header_dezima);
                intent.putExtra("result16",ed_16_header_dezima);
                intent.putExtra("result17",ed_17_header_dezima);
                intent.putExtra("date",ed18);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
