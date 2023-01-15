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

public class FooterAngebotActivity extends AppCompatActivity {
    private EditText ed_1_footer_dezimal,ed_2_footer_dezimal,ed_3_footer_dezimal,ed_4_footer_dezimal,ed_5_footer_dezimal
            ,ed_6_footer_dezimal,ed_7_footer_dezimal,ed_8_footer_dezimal,ed_9_footer_dezimal,ed_10_footer_dezimal
            ,ed_11_footer_dezimal,tv_rest;
    private Button btn_saveFooter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer_angebot);
        ed_1_footer_dezimal = findViewById(R.id.ed_1_footer_dezimal);
        ed_2_footer_dezimal = findViewById(R.id.ed_2_footer_dezimal);
        ed_3_footer_dezimal = findViewById(R.id.ed_3_footer_dezimal);
        ed_4_footer_dezimal = findViewById(R.id.ed_4_footer_dezimal);
        ed_5_footer_dezimal = findViewById(R.id.ed_5_footer_dezimal);
        ed_6_footer_dezimal = findViewById(R.id.ed_6_footer_dezimal);
        ed_7_footer_dezimal = findViewById(R.id.ed_7_footer_dezimal);
        ed_8_footer_dezimal = findViewById(R.id.ed_8_footer_dezimal);
        ed_9_footer_dezimal = findViewById(R.id.ed_9_footer_dezimal);
        ed_10_footer_dezimal = findViewById(R.id.ed_10_footer_dezimal);
        ed_11_footer_dezimal = findViewById(R.id.ed_11_footer_dezimal);
        tv_rest = findViewById(R.id.tv_rest);
        //to retreive data from shared prefrence
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String st1 = preferences.getString("101","Metaphrase");
        String st2 = preferences.getString("102","Beste Sprachschule in Tunesien für Deutsch & weitere Sprachen und zur Unterstützung von Immigrationsangelegenheiten");
        String st3 = preferences.getString("103"," 63065 Offenbach am Main");
        String st4 = preferences.getString("104","Tel: 069 506 086 810");
        String st5 = preferences.getString("105","Fax: (069) 900 19 595");
        String st6 = preferences.getString("106","service@metaphrase.net");
        String st7 = preferences.getString("107","www.metaphrase.online");
        String st8 = preferences.getString("108","Metaphrase");
        String st9 = preferences.getString("109","DE95 1101 0101 5518 4742 83");
        String st10 = preferences.getString("110","Steuernummer:354021229");
        String st11 = preferences.getString("111","Finanzamt Offenbach");
        String st12 = preferences.getString("112","BIC: SOBKDEB2XXX");
        ed_1_footer_dezimal.setText(st1);
        ed_2_footer_dezimal.setText(st2);
        ed_3_footer_dezimal.setText(st3);
        ed_4_footer_dezimal.setText(st4);
        ed_5_footer_dezimal.setText(st5);
        ed_6_footer_dezimal.setText(st6);
        ed_7_footer_dezimal.setText(st7);
        ed_8_footer_dezimal.setText(st8);
        ed_9_footer_dezimal.setText(st9);
        ed_10_footer_dezimal.setText(st10);
        ed_11_footer_dezimal.setText(st11);
        tv_rest.setText(st12);
        btn_saveFooter = findViewById(R.id.btn_savefooter);
        btn_saveFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ed_1_footer_dezima = ed_1_footer_dezimal.getText().toString();
                String ed_2_footer_dezima = ed_2_footer_dezimal.getText().toString();
                String ed_3_footer_dezima = ed_3_footer_dezimal.getText().toString();
                String ed_4_footer_dezima = ed_4_footer_dezimal.getText().toString();
                String ed_5_footer_dezima = ed_5_footer_dezimal.getText().toString();
                String ed_6_footer_dezima = ed_6_footer_dezimal.getText().toString();
                String ed_7_footer_dezima = ed_7_footer_dezimal.getText().toString();
                String ed_8_footer_dezima = ed_8_footer_dezimal.getText().toString();
                String ed_9_footer_dezima = ed_9_footer_dezimal.getText().toString();
                String ed_10_footer_dezima = ed_10_footer_dezimal.getText().toString();
                String ed_11_footer_dezima = ed_11_footer_dezimal.getText().toString();
                String ed_rest = tv_rest.getText().toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FooterAngebotActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("101",ed_1_footer_dezima);
                editor.putString("102",ed_2_footer_dezima);
                editor.putString("103",ed_3_footer_dezima);
                editor.putString("104",ed_4_footer_dezima);
                editor.putString("105",ed_5_footer_dezima);
                editor.putString("106",ed_6_footer_dezima);
                editor.putString("107",ed_7_footer_dezima);
                editor.putString("108",ed_8_footer_dezima);
                editor.putString("109",ed_9_footer_dezima);
                editor.putString("110",ed_10_footer_dezima);
                editor.putString("111",ed_11_footer_dezima);
                editor.putString("112",ed_rest);
                editor.apply();
                Intent intent = new Intent();
                intent.putExtra("footerresult1",ed_1_footer_dezima);
                intent.putExtra("footerresult2",ed_2_footer_dezima);
                intent.putExtra("footerresult3",ed_3_footer_dezima);
                intent.putExtra("footerresult4",ed_4_footer_dezima);
                intent.putExtra("footerresult5",ed_5_footer_dezima);
                intent.putExtra("footerresult6",ed_6_footer_dezima);
                intent.putExtra("footerresult7",ed_7_footer_dezima);
                intent.putExtra("footerresult8",ed_8_footer_dezima);
                intent.putExtra("footerresult9",ed_9_footer_dezima);
                intent.putExtra("footerresult10",ed_10_footer_dezima);
                intent.putExtra("footerresult11",ed_11_footer_dezima);
                intent.putExtra("footerrest",ed_rest);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
