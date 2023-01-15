package com.example.metaphraseinvoice.Invoices;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metaphraseinvoice.HeaderFooter.FooterDezimalActivity;
import com.example.metaphraseinvoice.HeaderFooter.HeaderDezimalActivity;
import com.example.metaphraseinvoice.InputActivitites.Model.DezimalModel;
import com.example.metaphraseinvoice.InputActivitites.NormezeilenInputActivity;
import com.example.metaphraseinvoice.ItemModel;
import com.example.metaphraseinvoice.R;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DezimalRechnung extends AppCompatActivity {
    TextView tv_rest, tv_1sehgehtre, ed_2denAn, tv_3_Dplmetscherauftrag, tv_4_berechnen, tv_5_metaphrase, tv_6_drname, tv_7_VerWend, tv_8_Dolmetscherund, tv_9_Weutenberger, tv_10_0319_W1228, tv_11_GroB, tv_12_AstafflenbergesterBe, tv_13_datum, tv_14_offenbach212, tv_15_stutgrat23, tv_16_date, tv_17_abzahlungseingang, tv_18_metaphrase_footer, tv_19_GroB_footer, tv_20_offenbench34_footer, tv_21_tel_footer,
            tv_22_fax_footer, tv_23_mail_footer, tv_24_metaphrase_mail_footer, tv_25_Velksbank_footer, tv_26_banknumber_footer, tv_27_Steuernumner_footer, tv_28_finnazami_footer;
    Context context = this;
    Button btn_editheader_dezimal, btn_editfooter_dezimal;

    TextView tv_pos_1, tv_pos_2, tv_pos_3, tv_pos_4, tv_pos_5, tv_pos_6, tv_pos_7, tv_pos_8, tv_pos_9, tv_pos_10;
    TextView tv_detail_1, tv_detail_2, tv_detail_3, tv_detail_4, tv_detail_5, tv_detail_6, tv_detail_7, tv_detail_8, tv_detail_9, tv_detail_10;
    TextView tv_hours_1, tv_hours_2, tv_hours_3, tv_hours_4, tv_hours_5, tv_hours_6, tv_hours_7, tv_hours_8, tv_hours_9, tv_hours_10;
    TextView tv_minutes_1, tv_minutes_2, tv_minutes_3, tv_minutes_4, tv_minutes_5, tv_minutes_6, tv_minutes_7, tv_minutes_8, tv_minutes_9, tv_minutes_10;
    TextView tv_decimal_digit_1, tv_decimal_digit_2, tv_decimal_digit_3, tv_decimal_digit_4, tv_decimal_digit_5, tv_decimal_digit_6, tv_decimal_digit_7, tv_decimal_digit_8, tv_decimal_digit_9, tv_decimal_digit_10;
    TextView tv_menge_1, tv_menge_2, tv_menge_3, tv_menge_4, tv_menge_5, tv_menge_6, tv_menge_7, tv_menge_8, tv_menge_9, tv_menge_10;
    TextView tv_Einzelpreis_1, tv_Einzelpreis_2, tv_Einzelpreis_3, tv_Einzelpreis_4, tv_Einzelpreis_5, tv_Einzelpreis_6, tv_Einzelpreis_7, tv_Einzelpreis_8, tv_Einzelpreis_9, tv_Einzelpreis_10;
    TextView tv_Betrag_1, tv_Betrag_2, tv_Betrag_3, tv_Betrag_4, tv_Betrag_5, tv_Betrag_6, tv_Betrag_7, tv_Betrag_8, tv_Betrag_9, tv_Betrag_10;

    TextView tv_netto, tv_19_perc, tv_grand_total,newdezimaltext,tv17, tv18;
    EditText tv_advance;

    ArrayList<DezimalModel> dezimalModelArrayList = new ArrayList<>();
    String advance = "";
    double netto;


    Button btn_save_as_pdf;
    RelativeLayout rl_invoice;
    String dirpath;
    File fileUri;

    //TODO: FOR Signature Pad
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean isSaved=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_dezimal_rechnung);
        String list_as_string = getIntent().getStringExtra("array");
        Gson gson = new Gson();
        Type type = new TypeToken<List<DezimalModel>>() {
        }.getType();
        dezimalModelArrayList = gson.fromJson(list_as_string, type);
        advance = getIntent().getStringExtra("advance");
        if (advance.isEmpty()) advance = "0";

        inItComponents();
        newdezimaltext = findViewById(R.id.newdezimaltext);
        newdezimaltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DezimalRechnung.this);
                dialog.setContentView(R.layout.ed_dialogbox1);
                dialog.setTitle("Title...");
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newdezimaltext.getText().toString();
                final EditText ed_newtext = dialog.findViewById(R.id.ed_newtext);
                LinearLayout close = dialog.findViewById(R.id.closetext);
                Button btn_save = dialog.findViewById(R.id.btn_savechange);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String news = ed_newtext.getText().toString();
                        newdezimaltext.setText(news);
                        dialog.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        fillTable(dezimalModelArrayList);


        btn_editheader_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DezimalRechnung.this, HeaderDezimalActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        btn_editfooter_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DezimalRechnung.this, FooterDezimalActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        btn_save_as_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isSaved){
                    Toast.makeText(DezimalRechnung.this, "\n" +
                            "Bereits gespeichert", Toast.LENGTH_SHORT).show();
                }
                else{
                    btn_editfooter_dezimal.setVisibility(View.GONE);
                    btn_editheader_dezimal.setVisibility(View.GONE);
                    layoutToImage(v);
                }
            }
        });
    }

    private void fillTable(ArrayList<DezimalModel> dezimalModelArrayList) {

        if (dezimalModelArrayList.size() == 1) {

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min);
            double minutes = min / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            netto = total1;

        } else if (dezimalModelArrayList.size() == 2) {

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            netto = total1 + total2;

        } else if (dezimalModelArrayList.size() == 3) {

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime =Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));
            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double tt = Double.parseDouble(tv_hours_3.getText().toString());
            double totalTime3 = tt + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            netto = total1 + total2 + total3;
        } else if (dezimalModelArrayList.size() == 4) {

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double totalTime3 = Double.parseDouble(tv_hours_3.getText().toString()) + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(dezimalModelArrayList.get(3).getDescription());
            tv_hours_4.setText(dezimalModelArrayList.get(3).getHours());
            tv_minutes_4.setText(dezimalModelArrayList.get(3).getMinutes());
            double min4 = Double.parseDouble(tv_minutes_4.getText().toString());
            Log.d("minnn", "fillTable: " + min4);
            double minutes4 = min4 / 60;
            double totalTime4 = Double.parseDouble(tv_hours_4.getText().toString()) + minutes4;
            String.format("%.2f", totalTime4);
            tv_decimal_digit_4.setText(String.format("%.2f", totalTime4));
            tv_menge_4.setText(String.format("%.2f", totalTime4));
            tv_Einzelpreis_4.setText(dezimalModelArrayList.get(3).getPrice());
            double price4 = Double.parseDouble(dezimalModelArrayList.get(3).getPrice());
            double total4 = totalTime4 * price4;
            tv_Betrag_4.setText(String.format("%.2f", total4));

            netto = total1 + total2 + total3 + total4;
        } else if (dezimalModelArrayList.size() == 5) {
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double totalTime3 = Double.parseDouble(tv_hours_3.getText().toString()) + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(dezimalModelArrayList.get(3).getDescription());
            tv_hours_4.setText(dezimalModelArrayList.get(3).getHours());
            tv_minutes_4.setText(dezimalModelArrayList.get(3).getMinutes());
            double min4 = Double.parseDouble(tv_minutes_4.getText().toString());
            Log.d("minnn", "fillTable: " + min4);
            double minutes4 = min4 / 60;
            double totalTime4 = Double.parseDouble(tv_hours_4.getText().toString()) + minutes4;
            String.format("%.2f", totalTime4);
            tv_decimal_digit_4.setText(String.format("%.2f", totalTime4));
            tv_menge_4.setText(String.format("%.2f", totalTime4));
            tv_Einzelpreis_4.setText(dezimalModelArrayList.get(3).getPrice());
            double price4 = Double.parseDouble(dezimalModelArrayList.get(3).getPrice());
            double total4 = totalTime4 * price4;
            tv_Betrag_4.setText(String.format("%.2f", total4));

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(dezimalModelArrayList.get(4).getDescription());
            tv_hours_5.setText(dezimalModelArrayList.get(4).getHours());
            tv_minutes_5.setText(dezimalModelArrayList.get(4).getMinutes());
            double min5 = Double.parseDouble(tv_minutes_5.getText().toString());
            Log.d("minnn", "fillTable: " + min5);
            double minutes5 = min5 / 60;
            double totalTime5 = Double.parseDouble(tv_hours_5.getText().toString()) + minutes5;
            String.format("%.2f", totalTime5);
            tv_decimal_digit_5.setText(String.format("%.2f", totalTime5));
            tv_menge_5.setText(String.format("%.2f", totalTime5));
            tv_Einzelpreis_5.setText(dezimalModelArrayList.get(4).getPrice());
            double price5 = Double.parseDouble(dezimalModelArrayList.get(4).getPrice());
            double total5 = totalTime5 * price5;
            tv_Betrag_5.setText(String.format("%.2f", total5));

            netto = total1 + total2 + total3 + total4 + total5;
        } else if (dezimalModelArrayList.size() == 6) {
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double totalTime3 = Double.parseDouble(tv_hours_3.getText().toString()) + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(dezimalModelArrayList.get(3).getDescription());
            tv_hours_4.setText(dezimalModelArrayList.get(3).getHours());
            tv_minutes_4.setText(dezimalModelArrayList.get(3).getMinutes());
            double min4 = Double.parseDouble(tv_minutes_4.getText().toString());
            Log.d("minnn", "fillTable: " + min4);
            double minutes4 = min4 / 60;
            double totalTime4 = Double.parseDouble(tv_hours_4.getText().toString()) + minutes4;
            String.format("%.2f", totalTime4);
            tv_decimal_digit_4.setText(String.format("%.2f", totalTime4));
            tv_menge_4.setText(String.format("%.2f", totalTime4));
            tv_Einzelpreis_4.setText(dezimalModelArrayList.get(3).getPrice());
            double price4 = Double.parseDouble(dezimalModelArrayList.get(3).getPrice());
            double total4 = totalTime4 * price4;
            tv_Betrag_4.setText(String.format("%.2f", total4));

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(dezimalModelArrayList.get(4).getDescription());
            tv_hours_5.setText(dezimalModelArrayList.get(4).getHours());
            tv_minutes_5.setText(dezimalModelArrayList.get(4).getMinutes());
            double min5 = Double.parseDouble(tv_minutes_5.getText().toString());
            Log.d("minnn", "fillTable: " + min5);
            double minutes5 = min5 / 60;
            double totalTime5 = Double.parseDouble(tv_hours_5.getText().toString()) + minutes5;
            String.format("%.2f", totalTime5);
            tv_decimal_digit_5.setText(String.format("%.2f", totalTime5));
            tv_menge_5.setText(String.format("%.2f", totalTime5));
            tv_Einzelpreis_5.setText(dezimalModelArrayList.get(4).getPrice());
            double price5 = Double.parseDouble(dezimalModelArrayList.get(4).getPrice());
            double total5 = totalTime5 * price5;
            tv_Betrag_5.setText(String.format("%.2f", total5));

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(dezimalModelArrayList.get(5).getDescription());
            tv_hours_6.setText(dezimalModelArrayList.get(5).getHours());
            tv_minutes_6.setText(dezimalModelArrayList.get(5).getMinutes());
            double min6 = Double.parseDouble(tv_minutes_6.getText().toString());
            Log.d("minnn", "fillTable: " + min6);
            double minutes6 = min6 / 60;
            double totalTime6 = Double.parseDouble(tv_hours_6.getText().toString()) + minutes6;
            String.format("%.2f", totalTime6);
            tv_decimal_digit_6.setText(String.format("%.2f", totalTime6));
            tv_menge_6.setText(String.format("%.2f", totalTime6));
            tv_Einzelpreis_6.setText(dezimalModelArrayList.get(5).getPrice());
            double price6 = Double.parseDouble(dezimalModelArrayList.get(5).getPrice());
            double total6 = totalTime6 * price6;
            tv_Betrag_6.setText(String.format("%.2f", total6));

            netto = total1 + total2 + total3 + total4 + total5 + total6;
        } else if (dezimalModelArrayList.size() == 7) {
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double totalTime3 = Double.parseDouble(tv_hours_3.getText().toString()) + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(dezimalModelArrayList.get(3).getDescription());
            tv_hours_4.setText(dezimalModelArrayList.get(3).getHours());
            tv_minutes_4.setText(dezimalModelArrayList.get(3).getMinutes());
            double min4 = Double.parseDouble(tv_minutes_4.getText().toString());
            Log.d("minnn", "fillTable: " + min4);
            double minutes4 = min4 / 60;
            double totalTime4 = Double.parseDouble(tv_hours_4.getText().toString()) + minutes4;
            String.format("%.2f", totalTime4);
            tv_decimal_digit_4.setText(String.format("%.2f", totalTime4));
            tv_menge_4.setText(String.format("%.2f", totalTime4));
            tv_Einzelpreis_4.setText(dezimalModelArrayList.get(3).getPrice());
            double price4 = Double.parseDouble(dezimalModelArrayList.get(3).getPrice());
            double total4 = totalTime4 * price4;
            tv_Betrag_4.setText(String.format("%.2f", total4));

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(dezimalModelArrayList.get(4).getDescription());
            tv_hours_5.setText(dezimalModelArrayList.get(4).getHours());
            tv_minutes_5.setText(dezimalModelArrayList.get(4).getMinutes());
            double min5 = Double.parseDouble(tv_minutes_5.getText().toString());
            Log.d("minnn", "fillTable: " + min5);
            double minutes5 = min5 / 60;
            double totalTime5 = Double.parseDouble(tv_hours_5.getText().toString()) + minutes5;
            String.format("%.2f", totalTime5);
            tv_decimal_digit_5.setText(String.format("%.2f", totalTime5));
            tv_menge_5.setText(String.format("%.2f", totalTime5));
            tv_Einzelpreis_5.setText(dezimalModelArrayList.get(4).getPrice());
            double price5 = Double.parseDouble(dezimalModelArrayList.get(4).getPrice());
            double total5 = totalTime5 * price5;
            tv_Betrag_5.setText(String.format("%.2f", total5));

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(dezimalModelArrayList.get(5).getDescription());
            tv_hours_6.setText(dezimalModelArrayList.get(5).getHours());
            tv_minutes_6.setText(dezimalModelArrayList.get(5).getMinutes());
            double min6 = Double.parseDouble(tv_minutes_6.getText().toString());
            Log.d("minnn", "fillTable: " + min6);
            double minutes6 = min6 / 60;
            double totalTime6 = Double.parseDouble(tv_hours_6.getText().toString()) + minutes6;
            String.format("%.2f", totalTime6);
            tv_decimal_digit_6.setText(String.format("%.2f", totalTime6));
            tv_menge_6.setText(String.format("%.2f", totalTime6));
            tv_Einzelpreis_6.setText(dezimalModelArrayList.get(5).getPrice());
            double price6 = Double.parseDouble(dezimalModelArrayList.get(5).getPrice());
            double total6 = totalTime6 * price6;
            tv_Betrag_6.setText(String.format("%.2f", total6));

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(dezimalModelArrayList.get(6).getDescription());
            tv_hours_7.setText(dezimalModelArrayList.get(6).getHours());
            tv_minutes_7.setText(dezimalModelArrayList.get(6).getMinutes());
            double min7 = Double.parseDouble(tv_minutes_7.getText().toString());
            Log.d("minnn", "fillTable: " + min7);
            double minutes7 = min7 / 60;
            double totalTime7 = Double.parseDouble(tv_hours_7.getText().toString()) + minutes7;
            String.format("%.2f", totalTime7);
            tv_decimal_digit_7.setText(String.format("%.2f", totalTime7));
            tv_menge_7.setText(String.format("%.2f", totalTime7));
            tv_Einzelpreis_7.setText(dezimalModelArrayList.get(6).getPrice());
            double price7 = Double.parseDouble(dezimalModelArrayList.get(6).getPrice());
            double total7 = totalTime7 * price7;
            tv_Betrag_7.setText(String.format("%.2f", total7));
            netto = total1 + total2 + total3 + total4 + total5 + total6 + total7;
        } else if (dezimalModelArrayList.size() == 8) {
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double totalTime3 = Double.parseDouble(tv_hours_3.getText().toString()) + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(dezimalModelArrayList.get(3).getDescription());
            tv_hours_4.setText(dezimalModelArrayList.get(3).getHours());
            tv_minutes_4.setText(dezimalModelArrayList.get(3).getMinutes());
            double min4 = Double.parseDouble(tv_minutes_4.getText().toString());
            Log.d("minnn", "fillTable: " + min4);
            double minutes4 = min4 / 60;
            double totalTime4 = Double.parseDouble(tv_hours_4.getText().toString()) + minutes4;
            String.format("%.2f", totalTime4);
            tv_decimal_digit_4.setText(String.format("%.2f", totalTime4));
            tv_menge_4.setText(String.format("%.2f", totalTime4));
            tv_Einzelpreis_4.setText(dezimalModelArrayList.get(3).getPrice());
            double price4 = Double.parseDouble(dezimalModelArrayList.get(3).getPrice());
            double total4 = totalTime4 * price4;
            tv_Betrag_4.setText(String.format("%.2f", total4));

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(dezimalModelArrayList.get(4).getDescription());
            tv_hours_5.setText(dezimalModelArrayList.get(4).getHours());
            tv_minutes_5.setText(dezimalModelArrayList.get(4).getMinutes());
            double min5 = Double.parseDouble(tv_minutes_5.getText().toString());
            Log.d("minnn", "fillTable: " + min5);
            double minutes5 = min5 / 60;
            double totalTime5 = Double.parseDouble(tv_hours_5.getText().toString()) + minutes5;
            String.format("%.2f", totalTime5);
            tv_decimal_digit_5.setText(String.format("%.2f", totalTime5));
            tv_menge_5.setText(String.format("%.2f", totalTime5));
            tv_Einzelpreis_5.setText(dezimalModelArrayList.get(4).getPrice());
            double price5 = Double.parseDouble(dezimalModelArrayList.get(4).getPrice());
            double total5 = totalTime5 * price5;
            tv_Betrag_5.setText(String.format("%.2f", total5));

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(dezimalModelArrayList.get(5).getDescription());
            tv_hours_6.setText(dezimalModelArrayList.get(5).getHours());
            tv_minutes_6.setText(dezimalModelArrayList.get(5).getMinutes());
            double min6 = Double.parseDouble(tv_minutes_6.getText().toString());
            Log.d("minnn", "fillTable: " + min6);
            double minutes6 = min6 / 60;
            double totalTime6 = Double.parseDouble(tv_hours_6.getText().toString()) + minutes6;
            String.format("%.2f", totalTime6);
            tv_decimal_digit_6.setText(String.format("%.2f", totalTime6));
            tv_menge_6.setText(String.format("%.2f", totalTime6));
            tv_Einzelpreis_6.setText(dezimalModelArrayList.get(5).getPrice());
            double price6 = Double.parseDouble(dezimalModelArrayList.get(5).getPrice());
            double total6 = totalTime6 * price6;
            tv_Betrag_6.setText(String.format("%.2f", total6));

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(dezimalModelArrayList.get(6).getDescription());
            tv_hours_7.setText(dezimalModelArrayList.get(6).getHours());
            tv_minutes_7.setText(dezimalModelArrayList.get(6).getMinutes());
            double min7 = Double.parseDouble(tv_minutes_7.getText().toString());
            Log.d("minnn", "fillTable: " + min7);
            double minutes7 = min7 / 60;
            double totalTime7 = Double.parseDouble(tv_hours_7.getText().toString()) + minutes7;
            String.format("%.2f", totalTime7);
            tv_decimal_digit_7.setText(String.format("%.2f", totalTime7));
            tv_menge_7.setText(String.format("%.2f", totalTime7));
            tv_Einzelpreis_7.setText(dezimalModelArrayList.get(6).getPrice());
            double price7 = Double.parseDouble(dezimalModelArrayList.get(6).getPrice());
            double total7 = totalTime7 * price7;
            tv_Betrag_7.setText(String.format("%.2f", total7));
            netto = total1 + total2 + total3 + total4 + total5 + total6 + total7;

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(dezimalModelArrayList.get(7).getDescription());
            tv_hours_8.setText(dezimalModelArrayList.get(7).getHours());
            tv_minutes_8.setText(dezimalModelArrayList.get(7).getMinutes());
            double min8 = Double.parseDouble(tv_minutes_8.getText().toString());
            Log.d("minnn", "fillTable: " + min8);
            double minutes8 = min8 / 60;
            double totalTime8 = Double.parseDouble(tv_hours_8.getText().toString()) + minutes8;
            String.format("%.2f", totalTime8);
            tv_decimal_digit_8.setText(String.format("%.2f", totalTime8));
            tv_menge_8.setText(String.format("%.2f", totalTime8));
            tv_Einzelpreis_8.setText(dezimalModelArrayList.get(7).getPrice());
            double price8 = Double.parseDouble(dezimalModelArrayList.get(7).getPrice());
            double total8 = totalTime8 * price8;
            tv_Betrag_8.setText(String.format("%.2f", total8));

            netto = total1 + total2 + total3 + total4 + total5 + total6 + total7 + total8;
        } else if (dezimalModelArrayList.size() == 9) {
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double totalTime3 = Double.parseDouble(tv_hours_3.getText().toString()) + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(dezimalModelArrayList.get(3).getDescription());
            tv_hours_4.setText(dezimalModelArrayList.get(3).getHours());
            tv_minutes_4.setText(dezimalModelArrayList.get(3).getMinutes());
            double min4 = Double.parseDouble(tv_minutes_4.getText().toString());
            Log.d("minnn", "fillTable: " + min4);
            double minutes4 = min4 / 60;
            double totalTime4 = Double.parseDouble(tv_hours_4.getText().toString()) + minutes4;
            String.format("%.2f", totalTime4);
            tv_decimal_digit_4.setText(String.format("%.2f", totalTime4));
            tv_menge_4.setText(String.format("%.2f", totalTime4));
            tv_Einzelpreis_4.setText(dezimalModelArrayList.get(3).getPrice());
            double price4 = Double.parseDouble(dezimalModelArrayList.get(3).getPrice());
            double total4 = totalTime4 * price4;
            tv_Betrag_4.setText(String.format("%.2f", total4));

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(dezimalModelArrayList.get(4).getDescription());
            tv_hours_5.setText(dezimalModelArrayList.get(4).getHours());
            tv_minutes_5.setText(dezimalModelArrayList.get(4).getMinutes());
            double min5 = Double.parseDouble(tv_minutes_5.getText().toString());
            Log.d("minnn", "fillTable: " + min5);
            double minutes5 = min5 / 60;
            double totalTime5 = Double.parseDouble(tv_hours_5.getText().toString()) + minutes5;
            String.format("%.2f", totalTime5);
            tv_decimal_digit_5.setText(String.format("%.2f", totalTime5));
            tv_menge_5.setText(String.format("%.2f", totalTime5));
            tv_Einzelpreis_5.setText(dezimalModelArrayList.get(4).getPrice());
            double price5 = Double.parseDouble(dezimalModelArrayList.get(4).getPrice());
            double total5 = totalTime5 * price5;
            tv_Betrag_5.setText(String.format("%.2f", total5));

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(dezimalModelArrayList.get(5).getDescription());
            tv_hours_6.setText(dezimalModelArrayList.get(5).getHours());
            tv_minutes_6.setText(dezimalModelArrayList.get(5).getMinutes());
            double min6 = Double.parseDouble(tv_minutes_6.getText().toString());
            Log.d("minnn", "fillTable: " + min6);
            double minutes6 = min6 / 60;
            double totalTime6 = Double.parseDouble(tv_hours_6.getText().toString()) + minutes6;
            String.format("%.2f", totalTime6);
            tv_decimal_digit_6.setText(String.format("%.2f", totalTime6));
            tv_menge_6.setText(String.format("%.2f", totalTime6));
            tv_Einzelpreis_6.setText(dezimalModelArrayList.get(5).getPrice());
            double price6 = Double.parseDouble(dezimalModelArrayList.get(5).getPrice());
            double total6 = totalTime6 * price6;
            tv_Betrag_6.setText(String.format("%.2f", total6));

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(dezimalModelArrayList.get(6).getDescription());
            tv_hours_7.setText(dezimalModelArrayList.get(6).getHours());
            tv_minutes_7.setText(dezimalModelArrayList.get(6).getMinutes());
            double min7 = Double.parseDouble(tv_minutes_7.getText().toString());
            Log.d("minnn", "fillTable: " + min7);
            double minutes7 = min7 / 60;
            double totalTime7 = Double.parseDouble(tv_hours_7.getText().toString()) + minutes7;
            String.format("%.2f", totalTime7);
            tv_decimal_digit_7.setText(String.format("%.2f", totalTime7));
            tv_menge_7.setText(String.format("%.2f", totalTime7));
            tv_Einzelpreis_7.setText(dezimalModelArrayList.get(6).getPrice());
            double price7 = Double.parseDouble(dezimalModelArrayList.get(6).getPrice());
            double total7 = totalTime7 * price7;
            tv_Betrag_7.setText(String.format("%.2f", total7));
            netto = total1 + total2 + total3 + total4 + total5 + total6 + total7;

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(dezimalModelArrayList.get(7).getDescription());
            tv_hours_8.setText(dezimalModelArrayList.get(7).getHours());
            tv_minutes_8.setText(dezimalModelArrayList.get(7).getMinutes());
            double min8 = Double.parseDouble(tv_minutes_8.getText().toString());
            Log.d("minnn", "fillTable: " + min8);
            double minutes8 = min8 / 60;
            double totalTime8 = Double.parseDouble(tv_hours_8.getText().toString()) + minutes8;
            String.format("%.2f", totalTime8);
            tv_decimal_digit_8.setText(String.format("%.2f", totalTime8));
            tv_menge_8.setText(String.format("%.2f", totalTime8));
            tv_Einzelpreis_8.setText(dezimalModelArrayList.get(7).getPrice());
            double price8 = Double.parseDouble(dezimalModelArrayList.get(7).getPrice());
            double total8 = totalTime8 * price8;
            tv_Betrag_8.setText(String.format("%.2f", total8));

            //TODO: ROW 9
            tv_pos_9.setText("9");
            tv_detail_9.setText(dezimalModelArrayList.get(8).getDescription());
            tv_hours_9.setText(dezimalModelArrayList.get(8).getHours());
            tv_minutes_9.setText(dezimalModelArrayList.get(8).getMinutes());
            double min9 = Double.parseDouble(tv_minutes_9.getText().toString());
            Log.d("minnn", "fillTable: " + min9);
            double minutes9 = min9 / 60;
            double totalTime9 = Double.parseDouble(tv_hours_9.getText().toString()) + minutes9;
            String.format("%.2f", totalTime9);
            tv_decimal_digit_9.setText(String.format("%.2f", totalTime9));
            tv_menge_9.setText(String.format("%.2f", totalTime9));
            tv_Einzelpreis_9.setText(dezimalModelArrayList.get(8).getPrice());
            double price9 = Double.parseDouble(dezimalModelArrayList.get(8).getPrice());
            double total9 = totalTime9 * price9;
            tv_Betrag_9.setText(String.format("%.2f", total9));

            netto = total1 + total2 + total3 + total4 + total5 + total6 + total7 + total8 + total9;
        } else {
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(dezimalModelArrayList.get(0).getDescription());
            tv_hours_1.setText(dezimalModelArrayList.get(0).getHours());
            tv_minutes_1.setText(dezimalModelArrayList.get(0).getMinutes());
            double min1 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min1);
            double minutes = min1 / 60;
            double totalTime = Double.parseDouble(tv_hours_1.getText().toString()) + minutes;
            String.format("%.2f", totalTime);
            tv_decimal_digit_1.setText(String.format("%.2f", totalTime));
            tv_menge_1.setText(String.format("%.2f", totalTime));
            tv_Einzelpreis_1.setText(dezimalModelArrayList.get(0).getPrice());
            double price1 = Double.parseDouble(dezimalModelArrayList.get(0).getPrice());
            double total1 = totalTime * price1;
            tv_Betrag_1.setText(String.format("%.2f", total1));

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(dezimalModelArrayList.get(1).getDescription());
            tv_hours_2.setText(dezimalModelArrayList.get(1).getHours());
            tv_minutes_2.setText(dezimalModelArrayList.get(1).getMinutes());
            double min2 = Double.parseDouble(tv_minutes_1.getText().toString());
            Log.d("minnn", "fillTable: " + min2);
            double minutes2 = min2 / 60;
            double totalTime2 = Double.parseDouble(tv_hours_2.getText().toString()) + minutes2;
            String.format("%.2f", totalTime2);
            tv_decimal_digit_2.setText(String.format("%.2f", totalTime2));
            tv_menge_2.setText(String.format("%.2f", totalTime2));
            tv_Einzelpreis_2.setText(dezimalModelArrayList.get(1).getPrice());
            double price2 = Double.parseDouble(dezimalModelArrayList.get(1).getPrice());
            double total2 = totalTime2 * price2;
            tv_Betrag_2.setText(String.format("%.2f", total2));

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(dezimalModelArrayList.get(2).getDescription());
            tv_hours_3.setText(dezimalModelArrayList.get(2).getHours());
            tv_minutes_3.setText(dezimalModelArrayList.get(2).getMinutes());
            double min3 = Double.parseDouble(tv_minutes_3.getText().toString());
            Log.d("minnn", "fillTable: " + min3);
            double minutes3 = min2 / 60;
            double totalTime3 = Double.parseDouble(tv_hours_3.getText().toString()) + minutes3;
            String.format("%.2f", totalTime3);
            tv_decimal_digit_3.setText(String.format("%.2f", totalTime3));
            tv_menge_3.setText(String.format("%.2f", totalTime3));
            tv_Einzelpreis_3.setText(dezimalModelArrayList.get(2).getPrice());
            double price3 = Double.parseDouble(dezimalModelArrayList.get(2).getPrice());
            double total3 = totalTime3 * price3;
            tv_Betrag_3.setText(String.format("%.2f", total3));

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(dezimalModelArrayList.get(3).getDescription());
            tv_hours_4.setText(dezimalModelArrayList.get(3).getHours());
            tv_minutes_4.setText(dezimalModelArrayList.get(3).getMinutes());
            double min4 = Double.parseDouble(tv_minutes_4.getText().toString());
            Log.d("minnn", "fillTable: " + min4);
            double minutes4 = min4 / 60;
            double totalTime4 = Double.parseDouble(tv_hours_4.getText().toString()) + minutes4;
            String.format("%.2f", totalTime4);
            tv_decimal_digit_4.setText(String.format("%.2f", totalTime4));
            tv_menge_4.setText(String.format("%.2f", totalTime4));
            tv_Einzelpreis_4.setText(dezimalModelArrayList.get(3).getPrice());
            double price4 = Double.parseDouble(dezimalModelArrayList.get(3).getPrice());
            double total4 = totalTime4 * price4;
            tv_Betrag_4.setText(String.format("%.2f", total4));

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(dezimalModelArrayList.get(4).getDescription());
            tv_hours_5.setText(dezimalModelArrayList.get(4).getHours());
            tv_minutes_5.setText(dezimalModelArrayList.get(4).getMinutes());
            double min5 = Double.parseDouble(tv_minutes_5.getText().toString());
            Log.d("minnn", "fillTable: " + min5);
            double minutes5 = min5 / 60;
            double totalTime5 = Double.parseDouble(tv_hours_5.getText().toString()) + minutes5;
            String.format("%.2f", totalTime5);
            tv_decimal_digit_5.setText(String.format("%.2f", totalTime5));
            tv_menge_5.setText(String.format("%.2f", totalTime5));
            tv_Einzelpreis_5.setText(dezimalModelArrayList.get(4).getPrice());
            double price5 = Double.parseDouble(dezimalModelArrayList.get(4).getPrice());
            double total5 = totalTime5 * price5;
            tv_Betrag_5.setText(String.format("%.2f", total5));

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(dezimalModelArrayList.get(5).getDescription());
            tv_hours_6.setText(dezimalModelArrayList.get(5).getHours());
            tv_minutes_6.setText(dezimalModelArrayList.get(5).getMinutes());
            double min6 = Double.parseDouble(tv_minutes_6.getText().toString());
            Log.d("minnn", "fillTable: " + min6);
            double minutes6 = min6 / 60;
            double totalTime6 = Double.parseDouble(tv_hours_6.getText().toString()) + minutes6;
            String.format("%.2f", totalTime6);
            tv_decimal_digit_6.setText(String.format("%.2f", totalTime6));
            tv_menge_6.setText(String.format("%.2f", totalTime6));
            tv_Einzelpreis_6.setText(dezimalModelArrayList.get(5).getPrice());
            double price6 = Double.parseDouble(dezimalModelArrayList.get(5).getPrice());
            double total6 = totalTime6 * price6;
            tv_Betrag_6.setText(String.format("%.2f", total6));

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(dezimalModelArrayList.get(6).getDescription());
            tv_hours_7.setText(dezimalModelArrayList.get(6).getHours());
            tv_minutes_7.setText(dezimalModelArrayList.get(6).getMinutes());
            double min7 = Double.parseDouble(tv_minutes_7.getText().toString());
            Log.d("minnn", "fillTable: " + min7);
            double minutes7 = min7 / 60;
            double totalTime7 = Double.parseDouble(tv_hours_7.getText().toString()) + minutes7;
            String.format("%.2f", totalTime7);
            tv_decimal_digit_7.setText(String.format("%.2f", totalTime7));
            tv_menge_7.setText(String.format("%.2f", totalTime7));
            tv_Einzelpreis_7.setText(dezimalModelArrayList.get(6).getPrice());
            double price7 = Double.parseDouble(dezimalModelArrayList.get(6).getPrice());
            double total7 = totalTime7 * price7;
            tv_Betrag_7.setText(String.format("%.2f", total7));
            netto = total1 + total2 + total3 + total4 + total5 + total6 + total7;

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(dezimalModelArrayList.get(7).getDescription());
            tv_hours_8.setText(dezimalModelArrayList.get(7).getHours());
            tv_minutes_8.setText(dezimalModelArrayList.get(7).getMinutes());
            double min8 = Double.parseDouble(tv_minutes_8.getText().toString());
            Log.d("minnn", "fillTable: " + min8);
            double minutes8 = min8 / 60;
            double totalTime8 = Double.parseDouble(tv_hours_8.getText().toString()) + minutes8;
            String.format("%.2f", totalTime8);
            tv_decimal_digit_8.setText(String.format("%.2f", totalTime8));
            tv_menge_8.setText(String.format("%.2f", totalTime8));
            tv_Einzelpreis_8.setText(dezimalModelArrayList.get(7).getPrice());
            double price8 = Double.parseDouble(dezimalModelArrayList.get(7).getPrice());
            double total8 = totalTime8 * price8;
            tv_Betrag_8.setText(String.format("%.2f", total8));

            //TODO: ROW 9
            tv_pos_9.setText("9");
            tv_detail_9.setText(dezimalModelArrayList.get(8).getDescription());
            tv_hours_9.setText(dezimalModelArrayList.get(8).getHours());
            tv_minutes_9.setText(dezimalModelArrayList.get(8).getMinutes());
            double min9 = Double.parseDouble(tv_minutes_9.getText().toString());
            Log.d("minnn", "fillTable: " + min9);
            double minutes9 = min9 / 60;
            double totalTime9 = Double.parseDouble(tv_hours_9.getText().toString()) + minutes9;
            String.format("%.2f", totalTime9);
            tv_decimal_digit_9.setText(String.format("%.2f", totalTime9));
            tv_menge_9.setText(String.format("%.2f", totalTime9));
            tv_Einzelpreis_9.setText(dezimalModelArrayList.get(8).getPrice());
            double price9 = Double.parseDouble(dezimalModelArrayList.get(8).getPrice());
            double total9 = totalTime9 * price9;
            tv_Betrag_9.setText(String.format("%.2f", total9));

            //TODO: ROW 10
            tv_pos_10.setText("10");
            tv_detail_10.setText(dezimalModelArrayList.get(9).getDescription());
            tv_hours_10.setText(dezimalModelArrayList.get(9).getHours());
            tv_minutes_10.setText(dezimalModelArrayList.get(9).getMinutes());
            double min10 = Double.parseDouble(tv_minutes_10.getText().toString());
            Log.d("minnn", "fillTable: " + min10);
            double minutes10 = min10 / 60;
            double totalTime10 = Double.parseDouble(tv_hours_10.getText().toString()) + minutes10;
            String.format("%.2f", totalTime10);
            tv_decimal_digit_10.setText(String.format("%.2f", totalTime10));
            tv_menge_10.setText(String.format("%.2f", totalTime10));
            tv_Einzelpreis_10.setText(dezimalModelArrayList.get(9).getPrice());
            double price10 = Double.parseDouble(dezimalModelArrayList.get(9).getPrice());
            double total10 = totalTime10 * price10;
            tv_Betrag_10.setText(String.format("%.2f", total10));

            netto = total1 + total2 + total3 + total4 + total5 + total6 + total7 + total8 + total9 + total10;
        }
        tv_netto.setText(String.format("%.2f", netto));
        double gst_19_perc = (netto / 100.0f) * 19;
        tv_19_perc.setText(String.format("%.2f", gst_19_perc));
        Log.d("aaaa", "fillTable: " + netto + "  " + gst_19_perc);
        tv_advance.setText(advance);
        double advanceInt = Double.parseDouble(advance);
        double grand_total = netto + gst_19_perc - advanceInt;
        tv_grand_total.setText(String.format("%.2f", grand_total));
    }

    private void inItComponents() {

        btn_save_as_pdf = findViewById(R.id.btn_save_as_pdf);

        //Ids for header of dezimal
        tv_1sehgehtre = findViewById(R.id.tv1);
        ed_2denAn = findViewById(R.id.tv2);
        tv_3_Dplmetscherauftrag = findViewById(R.id.tv3);
        tv_4_berechnen = findViewById(R.id.tv4);
        tv_5_metaphrase = findViewById(R.id.tv5);
        tv_6_drname = findViewById(R.id.tv6);
        tv_7_VerWend = findViewById(R.id.tv7);
        tv_8_Dolmetscherund = findViewById(R.id.tv8);
        tv_9_Weutenberger = findViewById(R.id.tv9);
        tv_10_0319_W1228 = findViewById(R.id.tv10);
        tv_11_GroB = findViewById(R.id.tv11);
        tv_12_AstafflenbergesterBe = findViewById(R.id.tv12);
        tv_13_datum = findViewById(R.id.tv13);
        tv_14_offenbach212 = findViewById(R.id.tv14);
        tv_15_stutgrat23 = findViewById(R.id.tv15);
        tv_16_date = findViewById(R.id.tv16);
        tv17 = findViewById(R.id.tv17);
        tv18 = findViewById(R.id.tv18);

        //Ids for footer of dezimal
        tv_18_metaphrase_footer = findViewById(R.id.tv_18_metaphrase_footer);
        tv_19_GroB_footer = findViewById(R.id.tv_19_GroB_footer);
        tv_20_offenbench34_footer = findViewById(R.id.tv_20_offenbench34_footer);
        tv_21_tel_footer = findViewById(R.id.tv_21_tel_footer);
        tv_22_fax_footer = findViewById(R.id.tv_22_fax_footer);
        tv_23_mail_footer = findViewById(R.id.tv_23_mail_footer);
        tv_24_metaphrase_mail_footer = findViewById(R.id.tv_24_metaphrase_mail_footer);
        tv_25_Velksbank_footer = findViewById(R.id.tv_25_Velksbank_footer);
        tv_26_banknumber_footer = findViewById(R.id.tv_26_banknumber_footer);
        tv_27_Steuernumner_footer = findViewById(R.id.tv_27_Steuernumner_footer);
        tv_28_finnazami_footer = findViewById(R.id.tv_28_finnazami_footer);
        tv_rest = findViewById(R.id.tv_rest);
        //id for buttons
        btn_editfooter_dezimal = findViewById(R.id.btn_editfooter_dezimal);
        btn_editheader_dezimal = findViewById(R.id.btn_editheader_Angebot);

        //ids for views
        tv_pos_1 = findViewById(R.id.tv_pos_1);
        tv_pos_2 = findViewById(R.id.tv_pos_2);
        tv_pos_3 = findViewById(R.id.tv_pos_3);
        tv_pos_4 = findViewById(R.id.tv_pos_4);
        tv_pos_5 = findViewById(R.id.tv_pos_5);
        tv_pos_6 = findViewById(R.id.tv_pos_6);
        tv_pos_7 = findViewById(R.id.tv_pos_7);
        tv_pos_8 = findViewById(R.id.tv_pos_8);
        tv_pos_9 = findViewById(R.id.tv_pos_9);
        tv_pos_10 = findViewById(R.id.tv_pos_10);

        tv_detail_1 = findViewById(R.id.tv_detail_1);
        tv_detail_2 = findViewById(R.id.tv_detail_2);
        tv_detail_3 = findViewById(R.id.tv_detail_3);
        tv_detail_4 = findViewById(R.id.tv_detail_4);
        tv_detail_5 = findViewById(R.id.tv_detail_5);
        tv_detail_6 = findViewById(R.id.tv_detail_6);
        tv_detail_7 = findViewById(R.id.tv_detail_7);
        tv_detail_8 = findViewById(R.id.tv_detail_8);
        tv_detail_9 = findViewById(R.id.tv_detail_9);
        tv_detail_10 = findViewById(R.id.tv_detail_10);

        tv_hours_1 = findViewById(R.id.tv_hours_1);
        tv_hours_2 = findViewById(R.id.tv_hours_2);
        tv_hours_3 = findViewById(R.id.tv_hours_3);
        tv_hours_4 = findViewById(R.id.tv_hours_4);
        tv_hours_5 = findViewById(R.id.tv_hours_5);
        tv_hours_6 = findViewById(R.id.tv_hours_6);
        tv_hours_7 = findViewById(R.id.tv_hours_7);
        tv_hours_8 = findViewById(R.id.tv_hours_8);
        tv_hours_9 = findViewById(R.id.tv_hours_9);
        tv_hours_10 = findViewById(R.id.tv_hours_10);

        tv_minutes_1 = findViewById(R.id.tv_minutes_1);
        tv_minutes_2 = findViewById(R.id.tv_minutes_2);
        tv_minutes_3 = findViewById(R.id.tv_minutes_3);
        tv_minutes_4 = findViewById(R.id.tv_minutes_4);
        tv_minutes_5 = findViewById(R.id.tv_minutes_5);
        tv_minutes_6 = findViewById(R.id.tv_minutes_6);
        tv_minutes_7 = findViewById(R.id.tv_minutes_7);
        tv_minutes_8 = findViewById(R.id.tv_minutes_8);
        tv_minutes_9 = findViewById(R.id.tv_minutes_9);
        tv_minutes_10 = findViewById(R.id.tv_minutes_10);

        tv_decimal_digit_1 = findViewById(R.id.tv_decimal_digit_1);
        tv_decimal_digit_2 = findViewById(R.id.tv_decimal_digit_2);
        tv_decimal_digit_3 = findViewById(R.id.tv_decimal_digit_3);
        tv_decimal_digit_4 = findViewById(R.id.tv_decimal_digit_4);
        tv_decimal_digit_5 = findViewById(R.id.tv_decimal_digit_5);
        tv_decimal_digit_6 = findViewById(R.id.tv_decimal_digit_6);
        tv_decimal_digit_7 = findViewById(R.id.tv_decimal_digit_7);
        tv_decimal_digit_8 = findViewById(R.id.tv_decimal_digit_8);
        tv_decimal_digit_9 = findViewById(R.id.tv_decimal_digit_9);
        tv_decimal_digit_10 = findViewById(R.id.tv_decimal_digit_10);

        tv_menge_1 = findViewById(R.id.tv_menge_1);
        tv_menge_2 = findViewById(R.id.tv_menge_2);
        tv_menge_3 = findViewById(R.id.tv_menge_3);
        tv_menge_4 = findViewById(R.id.tv_menge_4);
        tv_menge_5 = findViewById(R.id.tv_menge_5);
        tv_menge_6 = findViewById(R.id.tv_menge_6);
        tv_menge_7 = findViewById(R.id.tv_menge_7);
        tv_menge_8 = findViewById(R.id.tv_menge_8);
        tv_menge_9 = findViewById(R.id.tv_menge_9);
        tv_menge_10 = findViewById(R.id.tv_menge_10);

        tv_Einzelpreis_1 = findViewById(R.id.tv_Einzelpreis_1);
        tv_Einzelpreis_2 = findViewById(R.id.tv_Einzelpreis_2);
        tv_Einzelpreis_3 = findViewById(R.id.tv_Einzelpreis_3);
        tv_Einzelpreis_4 = findViewById(R.id.tv_Einzelpreis_4);
        tv_Einzelpreis_5 = findViewById(R.id.tv_Einzelpreis_5);
        tv_Einzelpreis_6 = findViewById(R.id.tv_Einzelpreis_6);
        tv_Einzelpreis_7 = findViewById(R.id.tv_Einzelpreis_7);
        tv_Einzelpreis_8 = findViewById(R.id.tv_Einzelpreis_8);
        tv_Einzelpreis_9 = findViewById(R.id.tv_Einzelpreis_9);
        tv_Einzelpreis_10 = findViewById(R.id.tv_Einzelpreis_10);

        tv_Betrag_1 = findViewById(R.id.tv_Betrag_1);
        tv_Betrag_2 = findViewById(R.id.tv_Betrag_2);
        tv_Betrag_3 = findViewById(R.id.tv_Betrag_3);
        tv_Betrag_4 = findViewById(R.id.tv_Betrag_4);
        tv_Betrag_5 = findViewById(R.id.tv_Betrag_5);
        tv_Betrag_6 = findViewById(R.id.tv_Betrag_6);
        tv_Betrag_7 = findViewById(R.id.tv_Betrag_7);
        tv_Betrag_8 = findViewById(R.id.tv_Betrag_8);
        tv_Betrag_9 = findViewById(R.id.tv_Betrag_9);
        tv_Betrag_10 = findViewById(R.id.tv_Betrag_10);

        tv_netto = findViewById(R.id.tv_netto);
        tv_19_perc = findViewById(R.id.tv_19_perc);
        tv_advance = findViewById(R.id.tv_advance);
        tv_grand_total = findViewById(R.id.tv_grand_total);
        rl_invoice = findViewById(R.id.rl_invoice);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result1 = data.getStringExtra("result1");
                String result2 = data.getStringExtra("result2");
                String result3 = data.getStringExtra("result3");
                String result4 = data.getStringExtra("result4");
                String result5 = data.getStringExtra("result5");
                String result6 = data.getStringExtra("result6");
                String result7 = data.getStringExtra("result7");
                String result8 = data.getStringExtra("result8");
                String result9 = data.getStringExtra("result9");
                String result10 = data.getStringExtra("result10");
                String result11 = data.getStringExtra("result11");
                String result12 = data.getStringExtra("result12");
                String result13 = data.getStringExtra("result13");
                String result14 = data.getStringExtra("result14");
                String result15 = data.getStringExtra("result15");
                String result16 = data.getStringExtra("result16");
                String result17 = data.getStringExtra("result17");
                String result18 = data.getStringExtra("date");
                tv_1sehgehtre.setText(result1);
                ed_2denAn.setText(result2);
                tv_3_Dplmetscherauftrag.setText(result3);
                tv_4_berechnen.setText(result4);
                tv_5_metaphrase.setText(result5);
                tv_6_drname.setText(result6);
                tv_7_VerWend.setText(result7);
                tv_8_Dolmetscherund.setText(result8);
                tv_9_Weutenberger.setText(result9);
                tv_10_0319_W1228.setText(result10);
                tv_11_GroB.setText(result11);
                tv_12_AstafflenbergesterBe.setText(result12);
                tv_13_datum.setText(result13);
                tv_14_offenbach212.setText(result14);
                tv_15_stutgrat23.setText(result15);
                tv_16_date.setText(result16);
                tv17.setText(result17);
                tv18.setText(result18);
            }

        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String result1 = data.getStringExtra("footerresult1");
                String result2 = data.getStringExtra("footerresult2");
                String result3 = data.getStringExtra("footerresult3");
                String result4 = data.getStringExtra("footerresult4");
                String result5 = data.getStringExtra("footerresult5");
                String result6 = data.getStringExtra("footerresult6");
                String result7 = data.getStringExtra("footerresult7");
                String result8 = data.getStringExtra("footerresult8");
                String result9 = data.getStringExtra("footerresult9");
                String result10 = data.getStringExtra("footerresult10");
                String result11 = data.getStringExtra("footerresult11");
                String result12 = data.getStringExtra("footerrest");
                tv_18_metaphrase_footer.setText(result1);
                tv_19_GroB_footer.setText(result2);
                tv_20_offenbench34_footer.setText(result3);
                tv_21_tel_footer.setText(result4);
                tv_22_fax_footer.setText(result5);
                tv_23_mail_footer.setText(result6);
                tv_24_metaphrase_mail_footer.setText(result7);
                tv_25_Velksbank_footer.setText(result8);
                tv_26_banknumber_footer.setText(result9);
                tv_27_Steuernumner_footer.setText(result10);
                tv_28_finnazami_footer.setText(result11);
                tv_rest.setText(result12);

            }
        }
    }

    /*public void layoutToImage(View view) {
        // get view group using reference
        // convert view group to bitmap
        rl_invoice.setDrawingCacheEnabled(true);
        rl_invoice.buildDrawingCache();
        Bitmap bm = rl_invoice.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            imageToPDF();
        } catch (IOException e) {
            Log.d("sss", "imageToPDF: " + e.getMessage());
            e.printStackTrace();
        }

    }*/
    /*public void layoutToImage(View view) {
        // get view group using reference
        // convert view group to bitmap
        rl_invoice.setDrawingCacheEnabled(true);
        rl_invoice.buildDrawingCache();
   *//* Bitmap bm = rl_invoice.getDrawingCache();
    Intent share = new Intent(Intent.ACTION_SEND);
    share.setType("image/jpeg");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);*//*
        Bitmap b = Bitmap.createBitmap(rl_invoice.getWidth(), rl_invoice.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        rl_invoice.layout(0, 0, rl_invoice.getLayoutParams().width, rl_invoice.getLayoutParams().height);
        rl_invoice.draw(c);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            imageToPDF();
        } catch (IOException e) {
            Log.d("sss", "imageToPDF: "+e.getMessage());
            e.printStackTrace();
        }
    }*/
    public void layoutToImage(View view) {
        // get view group using reference
        // convert view group to bitmap
        rl_invoice.setDrawingCacheEnabled(true);
        rl_invoice.buildDrawingCache();
   /* Bitmap bm = rl_invoice.getDrawingCache();
    Intent share = new Intent(Intent.ACTION_SEND);
    share.setType("image/jpeg");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);*/
        Bitmap b = Bitmap.createBitmap(rl_invoice.getWidth(), rl_invoice.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        rl_invoice.layout(0, 0, rl_invoice.getLayoutParams().width, rl_invoice.getLayoutParams().height);
        rl_invoice.draw(c);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            imageToPDF();
        } catch (IOException e) {
            Log.d("sss", "imageToPDF: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Metavoice/";;
            File root = new File(dirpath);
            if (!root.exists()) {
                root.mkdirs();       // create folder if not exist
            }
            String filePath = dirpath + "/invoice_at_" + UUID.randomUUID().toString() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath)); //  Change pdf's name.
            fileUri = new File(filePath);
            Log.d("papthhh", "imageToPDF: " + fileUri);
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 280) / img.getWidth()) * 150;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            showDialogForPdfOptions();
            Toast.makeText(this, "\n" + "PDF Erfolgreich generiert! ..", Toast.LENGTH_SHORT).show();
            isSaved=true;
        } catch (Exception e) {
            Toast.makeText(DezimalRechnung.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("sss", "imageToPDF: " + e.getMessage());
        }
    }


    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
       /*// Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }*/
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.INTERNET
                        },
                        REQUEST_EXTERNAL_STORAGE);
            }else{
                //Toast.makeText(activity, "granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDialogForPdfOptions() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_pdf_options);
        dialog.setTitle("Title...");
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_view = dialog.findViewById(R.id.ll_view);
        LinearLayout ll_share = dialog.findViewById(R.id.ll_share);

        ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPdf();
                dialog.dismiss();
            }
        });
        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf();
                dialog.dismiss();
            }
        });
        LinearLayout close_dialog = dialog.findViewById(R.id.close);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // Method for opening a pdf file
    private void viewPdf() {
        if (fileUri.exists()) //Checking if the file exists or not
        {
            Uri path;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                path = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", fileUri);
            } else {
                path = Uri.fromFile(fileUri);
            }
            // Setting the intent for pdf reader
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            Log.d("papthhh", "viewPdf: " + path);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(pdfIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(DezimalRechnung.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DezimalRechnung.this, "File not exist!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sharePdf() {
        if (fileUri.exists()) //Checking if the file exists or not
        {
            Uri path;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                path = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", fileUri);
            } else {
                path = Uri.fromFile(fileUri);
            }
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, path);
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(share);
        } else {
            Toast.makeText(DezimalRechnung.this, "File not exist!", Toast.LENGTH_SHORT).show();
        }
    }

}