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

public class HeaderAngebotActivity extends AppCompatActivity {
    Button btn_saveangebot;
    EditText ed_1_header_angebot, ed_2_header_angebot, ed_3_header_angebot, ed_4_header_angebot, ed_5_header_angebot,
            ed_6_header_angebot, ed_7_header_angebot, ed_8_header_angebot, ed_9_header_angebot,
            ed_10_header_angebot, ed_11_header_angebot, ed_12_header_angebot,
            ed_13_header_angebot, ed_14_header_angebot, ed_15_header_angebot,
            ed_16_header_angebot, tv17, tv18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_angebot);
        ed_1_header_angebot = findViewById(R.id.tv1);
        ed_2_header_angebot = findViewById(R.id.tv2);
        ed_3_header_angebot = findViewById(R.id.tv3);
        ed_4_header_angebot = findViewById(R.id.tv4);
        ed_5_header_angebot = findViewById(R.id.tv5);
        ed_6_header_angebot = findViewById(R.id.tv6);
        ed_7_header_angebot = findViewById(R.id.tv7);
        ed_8_header_angebot = findViewById(R.id.tv8);
        ed_9_header_angebot = findViewById(R.id.tv9);
        ed_10_header_angebot = findViewById(R.id.tv10);
        ed_11_header_angebot = findViewById(R.id.tv11);
        ed_12_header_angebot = findViewById(R.id.tv12);
        ed_13_header_angebot = findViewById(R.id.tv13);
        ed_14_header_angebot = findViewById(R.id.tv14);
        ed_15_header_angebot = findViewById(R.id.tv15);
        ed_16_header_angebot = findViewById(R.id.tv16);
        tv17 = findViewById(R.id.tv17);
        tv18 = findViewById(R.id.tv18);
        btn_saveangebot = findViewById(R.id.btn_saveangebot);
        //to retreive data from shared prefrence
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String st1 = preferences.getString("11", "Hauptsitz");
        String st2 = preferences.getString("12", "Metaphrase");
        String st3 = preferences.getString("13", "Dolmetscher und ??bersetzungsb??ro");
        String st4 = preferences.getString("14", "Avenue Imam Muslim Ibn El Hajjej,");
        String st5 = preferences.getString("15", "4051 Sousse Sahloul, Tunisia");
        String st6 = preferences.getString("16", "Auftragsannahmestelle");
        String st7 = preferences.getString("17", "Metaphrase");
        String st8 = preferences.getString("18", "Dolmetscher und ??bersetzungsb??ro");
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
        ed_1_header_angebot.setText(st1);
        ed_2_header_angebot.setText(st2);
        ed_3_header_angebot.setText(st3);
        ed_4_header_angebot.setText(st4);
        ed_5_header_angebot.setText(st5);
        ed_6_header_angebot.setText(st6);
        ed_7_header_angebot.setText(st7);
        ed_8_header_angebot.setText(st8);
        ed_9_header_angebot.setText(st9);
        ed_10_header_angebot.setText(st10);
        ed_11_header_angebot.setText(st11);
        ed_12_header_angebot.setText(st12);
        ed_13_header_angebot.setText(st13);
        ed_14_header_angebot.setText(st14);
        ed_15_header_angebot.setText(st15);
        ed_16_header_angebot.setText(st16);
        tv17.setText(st17);
        tv18.setText(st18);
        btn_saveangebot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ed_1_header_angebo = ed_1_header_angebot.getText().toString();
                String ed_2_header_angebo = ed_2_header_angebot.getText().toString();
                String ed_3_header_angebo = ed_3_header_angebot.getText().toString();
                String ed_4_header_angebo = ed_4_header_angebot.getText().toString();
                String ed_5_header_angebo = ed_5_header_angebot.getText().toString();
                String ed_6_header_angebo = ed_6_header_angebot.getText().toString();
                String ed_7_header_angebo = ed_7_header_angebot.getText().toString();
                String ed_8_header_angebo = ed_8_header_angebot.getText().toString();
                String ed_9_header_angebo = ed_9_header_angebot.getText().toString();
                String ed_10_header_angebo = ed_10_header_angebot.getText().toString();
                String ed_11_header_angebo = ed_11_header_angebot.getText().toString();
                String ed_12_header_angebo = ed_12_header_angebot.getText().toString();
                String ed_13_header_angebo = ed_13_header_angebot.getText().toString();
                String ed_14_header_angebo = ed_14_header_angebot.getText().toString();
                String ed_15_header_angebo = ed_15_header_angebot.getText().toString();
                String ed_16_header_angebo = ed_16_header_angebot.getText().toString();
                String ed_17_header_angebo = tv17.getText().toString();
                String ed18 = tv18.getText().toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HeaderAngebotActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("11", ed_1_header_angebo);
                editor.putString("12", ed_2_header_angebo);
                editor.putString("13", ed_3_header_angebo);
                editor.putString("14", ed_4_header_angebo);
                editor.putString("15", ed_5_header_angebo);
                editor.putString("16", ed_6_header_angebo);
                editor.putString("17", ed_7_header_angebo);
                editor.putString("18", ed_8_header_angebo);
                editor.putString("19", ed_9_header_angebo);
                editor.putString("20", ed_10_header_angebo);
                editor.putString("21", ed_11_header_angebo);
                editor.putString("22", ed_12_header_angebo);
                editor.putString("23", ed_13_header_angebo);
                editor.putString("24", ed_14_header_angebo);
                editor.putString("25", ed_15_header_angebo);
                editor.putString("26", ed_16_header_angebo);
                editor.putString("27", ed_17_header_angebo);
                editor.putString("100", ed18);
                editor.apply();
                Intent intent = new Intent();
                intent.putExtra("result1", ed_1_header_angebo);
                intent.putExtra("result2", ed_2_header_angebo);
                intent.putExtra("result3", ed_3_header_angebo);
                intent.putExtra("result4", ed_4_header_angebo);
                intent.putExtra("result5", ed_5_header_angebo);
                intent.putExtra("result6", ed_6_header_angebo);
                intent.putExtra("result7", ed_7_header_angebo);
                intent.putExtra("result8", ed_8_header_angebo);
                intent.putExtra("result9", ed_9_header_angebo);
                intent.putExtra("result10", ed_10_header_angebo);
                intent.putExtra("result11", ed_11_header_angebo);
                intent.putExtra("result12", ed_12_header_angebo);
                intent.putExtra("result13", ed_13_header_angebo);
                intent.putExtra("result14", ed_14_header_angebo);
                intent.putExtra("result15", ed_15_header_angebo);
                intent.putExtra("result16", ed_16_header_angebo);
                intent.putExtra("result17", ed_17_header_angebo);
                intent.putExtra("date", ed18);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
