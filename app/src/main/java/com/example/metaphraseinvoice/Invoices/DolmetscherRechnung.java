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
import com.example.metaphraseinvoice.HeaderFooter.FooterDolmetcherActivity;
import com.example.metaphraseinvoice.HeaderFooter.HeaderDezimalActivity;
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

public class DolmetscherRechnung extends AppCompatActivity {
    TextView tv_rest,tv_1sehgehtre, ed_2denAn, tv_3_Dplmetscherauftrag,
            tv_4_berechnen, tv_5_metaphrase, tv_6_drname, tv_7_VerWend,
            tv_8_Dolmetscherund, tv_9_Weutenberger, tv_10_0319_W1228,
            tv_11_GroB, tv_12_AstafflenbergesterBe, tv_13_datum,
            tv_14_offenbach212, tv_15_stutgrat23, tv_16_date,
            tv_17_abzahlungseingang, tv_18_metaphrase_footer,
            tv_19_GroB_footer, tv_20_offenbench34_footer, tv_21_tel_footer,tv17,tv18,
            tv_22_fax_footer, tv_23_mail_footer, tv_24_metaphrase_mail_footer, tv_25_Velksbank_footer, tv_26_banknumber_footer, tv_27_Steuernumner_footer, tv_28_finnazami_footer;
    Context context = this;
    Button btn_editheader_dezimal, btn_editfooter_dezimal;

    TextView tv_pos_1,tv_pos_2,tv_pos_3,tv_pos_4,tv_pos_5,tv_pos_6,tv_pos_7,tv_pos_8,tv_pos_9,tv_pos_10;
    TextView tv_detail_1,tv_detail_2,tv_detail_3,tv_detail_4,tv_detail_5,tv_detail_6,tv_detail_7,tv_detail_8,tv_detail_9,tv_detail_10;
    TextView tv_Anzahl_1,tv_Anzahl_2,tv_Anzahl_3,tv_Anzahl_4,tv_Anzahl_5,tv_Anzahl_6,tv_Anzahl_7,tv_Anzahl_8,tv_Anzahl_9,tv_Anzahl_10;
    TextView tv_Einzelpreis_1,tv_Einzelpreis_2,tv_Einzelpreis_3,tv_Einzelpreis_4,tv_Einzelpreis_5,tv_Einzelpreis_6,tv_Einzelpreis_7,tv_Einzelpreis_8,tv_Einzelpreis_9,tv_Einzelpreis_10;
    TextView tv_Betrag_1,tv_Betrag_2,tv_Betrag_3,tv_Betrag_4,tv_Betrag_5,tv_Betrag_6,tv_Betrag_7,tv_Betrag_8,tv_Betrag_9,tv_Betrag_10;

    TextView tv_netto,tv_19_perc,tv_grand_total,newdoltext;
    EditText tv_advance;
    ArrayList<ItemModel> itemModelArrayList=new ArrayList<>();
    String advance="";
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
        setContentView(R.layout.activity_dolmetscher_rechnung);

        String list_as_string=getIntent().getStringExtra("array");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ItemModel>>(){}.getType();
        itemModelArrayList = gson.fromJson(list_as_string, type);
        advance=getIntent().getStringExtra("advance");
        if(advance.isEmpty())advance="0";

        inItComponents();
        newdoltext = findViewById(R.id.newdoltext);
        newdoltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DolmetscherRechnung.this);
                dialog.setContentView(R.layout.ed_dialogbox_2);
                dialog.setTitle("Title...");
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newdoltext.getText().toString();
                final EditText ed_newtext = dialog.findViewById(R.id.ed_newtext);
                LinearLayout close = dialog.findViewById(R.id.closetext);
                Button btn_save = dialog.findViewById(R.id.btn_savechange);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String news = ed_newtext.getText().toString();
                        newdoltext.setText(news);
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
        fillTable(itemModelArrayList);


        btn_editheader_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DolmetscherRechnung.this, HeaderDezimalActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        btn_editfooter_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DolmetscherRechnung.this, FooterDolmetcherActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        btn_save_as_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSaved){
                    Toast.makeText(context, "\n" +
                            "Bereits gespeichert", Toast.LENGTH_SHORT).show();
                }
                else {
                    btn_editfooter_dezimal.setVisibility(View.GONE);
                    btn_editheader_dezimal.setVisibility(View.GONE);
                    layoutToImage(v);
                }
            }
        });
    }

    private void inItComponents() {

        btn_save_as_pdf=findViewById(R.id.btn_save_as_pdf);

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
        tv_pos_1=findViewById(R.id.tv_pos_1);
        tv_pos_2=findViewById(R.id.tv_pos_2);
        tv_pos_3=findViewById(R.id.tv_pos_3);
        tv_pos_4=findViewById(R.id.tv_pos_4);
        tv_pos_5=findViewById(R.id.tv_pos_5);
        tv_pos_6=findViewById(R.id.tv_pos_6);
        tv_pos_7=findViewById(R.id.tv_pos_7);
        tv_pos_8=findViewById(R.id.tv_pos_8);
        tv_pos_9=findViewById(R.id.tv_pos_9);
        tv_pos_10=findViewById(R.id.tv_pos_10);

        tv_detail_1=findViewById(R.id.tv_detail_1);
        tv_detail_2=findViewById(R.id.tv_detail_2);
        tv_detail_3=findViewById(R.id.tv_detail_3);
        tv_detail_4=findViewById(R.id.tv_detail_4);
        tv_detail_5=findViewById(R.id.tv_detail_5);
        tv_detail_6=findViewById(R.id.tv_detail_6);
        tv_detail_7=findViewById(R.id.tv_detail_7);
        tv_detail_8=findViewById(R.id.tv_detail_8);
        tv_detail_9=findViewById(R.id.tv_detail_9);
        tv_detail_10=findViewById(R.id.tv_detail_10);

        tv_Anzahl_1=findViewById(R.id.tv_Anzahl_1);
        tv_Anzahl_2=findViewById(R.id.tv_Anzahl_2);
        tv_Anzahl_3=findViewById(R.id.tv_Anzahl_3);
        tv_Anzahl_4=findViewById(R.id.tv_Anzahl_4);
        tv_Anzahl_5=findViewById(R.id.tv_Anzahl_5);
        tv_Anzahl_6=findViewById(R.id.tv_Anzahl_6);
        tv_Anzahl_7=findViewById(R.id.tv_Anzahl_7);
        tv_Anzahl_8=findViewById(R.id.tv_Anzahl_8);
        tv_Anzahl_9=findViewById(R.id.tv_Anzahl_9);
        tv_Anzahl_10=findViewById(R.id.tv_Anzahl_10);

        tv_Einzelpreis_1=findViewById(R.id.tv_Einzelpreis_1);
        tv_Einzelpreis_2=findViewById(R.id.tv_Einzelpreis_2);
        tv_Einzelpreis_3=findViewById(R.id.tv_Einzelpreis_3);
        tv_Einzelpreis_4=findViewById(R.id.tv_Einzelpreis_4);
        tv_Einzelpreis_5=findViewById(R.id.tv_Einzelpreis_5);
        tv_Einzelpreis_6=findViewById(R.id.tv_Einzelpreis_6);
        tv_Einzelpreis_7=findViewById(R.id.tv_Einzelpreis_7);
        tv_Einzelpreis_8=findViewById(R.id.tv_Einzelpreis_8);
        tv_Einzelpreis_9=findViewById(R.id.tv_Einzelpreis_9);
        tv_Einzelpreis_10=findViewById(R.id.tv_Einzelpreis_10);

        tv_Betrag_1=findViewById(R.id.tv_Betrag_1);
        tv_Betrag_2=findViewById(R.id.tv_Betrag_2);
        tv_Betrag_3=findViewById(R.id.tv_Betrag_3);
        tv_Betrag_4=findViewById(R.id.tv_Betrag_4);
        tv_Betrag_5=findViewById(R.id.tv_Betrag_5);
        tv_Betrag_6=findViewById(R.id.tv_Betrag_6);
        tv_Betrag_7=findViewById(R.id.tv_Betrag_7);
        tv_Betrag_8=findViewById(R.id.tv_Betrag_8);
        tv_Betrag_9=findViewById(R.id.tv_Betrag_9);
        tv_Betrag_10=findViewById(R.id.tv_Betrag_10);

        tv_netto=findViewById(R.id.tv_netto);
        tv_19_perc=findViewById(R.id.tv_19_perc);
        tv_advance=findViewById(R.id.tv_advance);
        tv_grand_total=findViewById(R.id.tv_grand_total);
        rl_invoice=findViewById(R.id.rl_invoice);

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
    /*private void fillTable(ArrayList<ItemModel> itemModelArrayList) {

        if(itemModelArrayList.size()==1){

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            netto=total1;

        }else if(itemModelArrayList.size()==2){

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");
            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            netto=total1+total2;
        }
        else if(itemModelArrayList.size()==3){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            netto=total1+total2+total3;
        }
        else if(itemModelArrayList.size()==4){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            int quantity4=Integer.parseInt(itemModelArrayList.get(3).getQuantity());
            int price4=Integer.parseInt(itemModelArrayList.get(3).getPrice());
            int total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");
            netto=total1+total2+total3+total4;
        }
        else if(itemModelArrayList.size()==5){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            int quantity4=Integer.parseInt(itemModelArrayList.get(3).getQuantity());
            int price4=Integer.parseInt(itemModelArrayList.get(3).getPrice());
            int total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            int quantity5=Integer.parseInt(itemModelArrayList.get(4).getQuantity());
            int price5=Integer.parseInt(itemModelArrayList.get(4).getPrice());
            int total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");
            netto=total1+total2+total3+total4+total5;
        }
        else if(itemModelArrayList.size()==6){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            int quantity4=Integer.parseInt(itemModelArrayList.get(3).getQuantity());
            int price4=Integer.parseInt(itemModelArrayList.get(3).getPrice());
            int total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            int quantity5=Integer.parseInt(itemModelArrayList.get(4).getQuantity());
            int price5=Integer.parseInt(itemModelArrayList.get(4).getPrice());
            int total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            int quantity6=Integer.parseInt(itemModelArrayList.get(5).getQuantity());
            int price6=Integer.parseInt(itemModelArrayList.get(5).getPrice());
            int total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            netto=total1+total2+total3+total4+total5+total6;
        }
        else if(itemModelArrayList.size()==7){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            int quantity4=Integer.parseInt(itemModelArrayList.get(3).getQuantity());
            int price4=Integer.parseInt(itemModelArrayList.get(3).getPrice());
            int total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            int quantity5=Integer.parseInt(itemModelArrayList.get(4).getQuantity());
            int price5=Integer.parseInt(itemModelArrayList.get(4).getPrice());
            int total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            int quantity6=Integer.parseInt(itemModelArrayList.get(5).getQuantity());
            int price6=Integer.parseInt(itemModelArrayList.get(5).getPrice());
            int total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            int quantity7=Integer.parseInt(itemModelArrayList.get(6).getQuantity());
            int price7=Integer.parseInt(itemModelArrayList.get(6).getPrice());
            int total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            netto=total1+total2+total3+total4+total5+total6+total7;
        }
        else if(itemModelArrayList.size()==8){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            int quantity4=Integer.parseInt(itemModelArrayList.get(3).getQuantity());
            int price4=Integer.parseInt(itemModelArrayList.get(3).getPrice());
            int total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            int quantity5=Integer.parseInt(itemModelArrayList.get(4).getQuantity());
            int price5=Integer.parseInt(itemModelArrayList.get(4).getPrice());
            int total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            int quantity6=Integer.parseInt(itemModelArrayList.get(5).getQuantity());
            int price6=Integer.parseInt(itemModelArrayList.get(5).getPrice());
            int total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            int quantity7=Integer.parseInt(itemModelArrayList.get(6).getQuantity());
            int price7=Integer.parseInt(itemModelArrayList.get(6).getPrice());
            int total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(itemModelArrayList.get(7).getDescription());
            tv_Anzahl_8.setText(itemModelArrayList.get(7).getQuantity());
            tv_Einzelpreis_8.setText(itemModelArrayList.get(7).getPrice());
            int quantity8=Integer.parseInt(itemModelArrayList.get(7).getQuantity());
            int price8=Integer.parseInt(itemModelArrayList.get(7).getPrice());
            int total8=quantity8*price8;
            tv_Betrag_8.setText(total8+"");

            netto=total1+total2+total3+total4+total5+total6+total7+total8;
        }
        else if(itemModelArrayList.size()==9){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            int quantity4=Integer.parseInt(itemModelArrayList.get(3).getQuantity());
            int price4=Integer.parseInt(itemModelArrayList.get(3).getPrice());
            int total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            int quantity5=Integer.parseInt(itemModelArrayList.get(4).getQuantity());
            int price5=Integer.parseInt(itemModelArrayList.get(4).getPrice());
            int total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            int quantity6=Integer.parseInt(itemModelArrayList.get(5).getQuantity());
            int price6=Integer.parseInt(itemModelArrayList.get(5).getPrice());
            int total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            int quantity7=Integer.parseInt(itemModelArrayList.get(6).getQuantity());
            int price7=Integer.parseInt(itemModelArrayList.get(6).getPrice());
            int total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(itemModelArrayList.get(7).getDescription());
            tv_Anzahl_8.setText(itemModelArrayList.get(7).getQuantity());
            tv_Einzelpreis_8.setText(itemModelArrayList.get(7).getPrice());
            int quantity8=Integer.parseInt(itemModelArrayList.get(7).getQuantity());
            int price8=Integer.parseInt(itemModelArrayList.get(7).getPrice());
            int total8=quantity8*price8;
            tv_Betrag_8.setText(total8+"");

            //TODO: ROW 9
            tv_pos_9.setText("9");
            tv_detail_9.setText(itemModelArrayList.get(8).getDescription());
            tv_Anzahl_9.setText(itemModelArrayList.get(8).getQuantity());
            tv_Einzelpreis_9.setText(itemModelArrayList.get(8).getPrice());
            int quantity9=Integer.parseInt(itemModelArrayList.get(8).getQuantity());
            int price9=Integer.parseInt(itemModelArrayList.get(8).getPrice());
            int total9=quantity9*price9;
            tv_Betrag_9.setText(total9+"");

            netto=total1+total2+total3+total4+total5+total6+total7+total8+total9;
        }
        else{
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            int quantity1=Integer.parseInt(itemModelArrayList.get(0).getQuantity());
            int price1=Integer.parseInt(itemModelArrayList.get(0).getPrice());
            int total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            int quantity2=Integer.parseInt(itemModelArrayList.get(1).getQuantity());
            int price2=Integer.parseInt(itemModelArrayList.get(1).getPrice());
            int total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            int quantity3=Integer.parseInt(itemModelArrayList.get(2).getQuantity());
            int price3=Integer.parseInt(itemModelArrayList.get(2).getPrice());
            int total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            int quantity4=Integer.parseInt(itemModelArrayList.get(3).getQuantity());
            int price4=Integer.parseInt(itemModelArrayList.get(3).getPrice());
            int total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            int quantity5=Integer.parseInt(itemModelArrayList.get(4).getQuantity());
            int price5=Integer.parseInt(itemModelArrayList.get(4).getPrice());
            int total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            int quantity6=Integer.parseInt(itemModelArrayList.get(5).getQuantity());
            int price6=Integer.parseInt(itemModelArrayList.get(5).getPrice());
            int total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            int quantity7=Integer.parseInt(itemModelArrayList.get(6).getQuantity());
            int price7=Integer.parseInt(itemModelArrayList.get(6).getPrice());
            int total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(itemModelArrayList.get(7).getDescription());
            tv_Anzahl_8.setText(itemModelArrayList.get(7).getQuantity());
            tv_Einzelpreis_8.setText(itemModelArrayList.get(7).getPrice());
            int quantity8=Integer.parseInt(itemModelArrayList.get(7).getQuantity());
            int price8=Integer.parseInt(itemModelArrayList.get(7).getPrice());
            int total8=quantity8*price8;
            tv_Betrag_8.setText(total8+"");

            //TODO: ROW 9
            tv_pos_9.setText("1");
            tv_detail_9.setText(itemModelArrayList.get(8).getDescription());
            tv_Anzahl_9.setText(itemModelArrayList.get(8).getQuantity());
            tv_Einzelpreis_9.setText(itemModelArrayList.get(8).getPrice());
            int quantity9=Integer.parseInt(itemModelArrayList.get(8).getQuantity());
            int price9=Integer.parseInt(itemModelArrayList.get(8).getPrice());
            int total9=quantity9*price9;
            tv_Betrag_9.setText(total9+"");

            //TODO: ROW 10
            tv_pos_10.setText("10");
            tv_detail_10.setText(itemModelArrayList.get(9).getDescription());
            tv_Anzahl_10.setText(itemModelArrayList.get(9).getQuantity());
            tv_Einzelpreis_10.setText(itemModelArrayList.get(9).getPrice());
            int quantity10=Integer.parseInt(itemModelArrayList.get(9).getQuantity());
            int price10=Integer.parseInt(itemModelArrayList.get(9).getPrice());
            int total10=quantity10*price10;
            tv_Betrag_10.setText(total10+"");
            netto=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
        }
        tv_netto.setText(String.format("%.2f", netto));
        double gst_19_perc = (netto / 100.0f) * 19;
        tv_19_perc.setText(String.format("%.2f",gst_19_perc));
        Log.d("aaaa", "fillTable: "+netto+"  "+gst_19_perc);
        tv_advance.setText(advance);
        int advanceInt=Integer.parseInt(advance);
        double grand_total=netto+gst_19_perc-advanceInt;
        tv_grand_total.setText(String.format("%.2f", grand_total));
    }*/
    private void fillTable(ArrayList<ItemModel> itemModelArrayList) {

        if(itemModelArrayList.size()==1){

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1= Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1= Double.parseDouble((itemModelArrayList.get(0).getPrice()));
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            netto=total1;

        }else if(itemModelArrayList.size()==2){

            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1= Double.parseDouble((itemModelArrayList.get(0).getQuantity()));
            double price1= Double.parseDouble((itemModelArrayList.get(0).getPrice()));
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");
            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            netto=total1+total2;

        }
        else if(itemModelArrayList.size()==3){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            netto=total1+total2+total3;
        }
        else if(itemModelArrayList.size()==4){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            double quantity4=Double.parseDouble(itemModelArrayList.get(3).getQuantity());
            double price4=Double.parseDouble(itemModelArrayList.get(3).getPrice());
            double total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");
            netto=total1+total2+total3+total4;
        }
        else if(itemModelArrayList.size()==5){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            double quantity4=Double.parseDouble(itemModelArrayList.get(3).getQuantity());
            double price4=Double.parseDouble(itemModelArrayList.get(3).getPrice());
            double total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            double quantity5=Double.parseDouble(itemModelArrayList.get(4).getQuantity());
            double price5=Double.parseDouble(itemModelArrayList.get(4).getPrice());
            double total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");
            netto=total1+total2+total3+total4+total5;
        }
        else if(itemModelArrayList.size()==6){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            double quantity4=Double.parseDouble(itemModelArrayList.get(3).getQuantity());
            double price4=Double.parseDouble(itemModelArrayList.get(3).getPrice());
            double total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            double quantity5=Double.parseDouble(itemModelArrayList.get(4).getQuantity());
            double price5=Double.parseDouble(itemModelArrayList.get(4).getPrice());
            double total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            double quantity6=Double.parseDouble(itemModelArrayList.get(5).getQuantity());
            double price6=Double.parseDouble(itemModelArrayList.get(5).getPrice());
            double total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            netto=total1+total2+total3+total4+total5+total6;
        }
        else if(itemModelArrayList.size()==7){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            double quantity4=Double.parseDouble(itemModelArrayList.get(3).getQuantity());
            double price4=Double.parseDouble(itemModelArrayList.get(3).getPrice());
            double total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            double quantity5=Double.parseDouble(itemModelArrayList.get(4).getQuantity());
            double price5=Double.parseDouble(itemModelArrayList.get(4).getPrice());
            double total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            double quantity6=Double.parseDouble(itemModelArrayList.get(5).getQuantity());
            double price6=Double.parseDouble(itemModelArrayList.get(5).getPrice());
            double total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            double quantity7=Double.parseDouble(itemModelArrayList.get(6).getQuantity());
            double price7=Double.parseDouble(itemModelArrayList.get(6).getPrice());
            double total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            netto=total1+total2+total3+total4+total5+total6+total7;
        }
        else if(itemModelArrayList.size()==8){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            double quantity4=Double.parseDouble(itemModelArrayList.get(3).getQuantity());
            double price4=Double.parseDouble(itemModelArrayList.get(3).getPrice());
            double total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            double quantity5=Double.parseDouble(itemModelArrayList.get(4).getQuantity());
            double price5=Double.parseDouble(itemModelArrayList.get(4).getPrice());
            double total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            double quantity6=Double.parseDouble(itemModelArrayList.get(5).getQuantity());
            double price6=Double.parseDouble(itemModelArrayList.get(5).getPrice());
            double total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            double quantity7=Double.parseDouble(itemModelArrayList.get(6).getQuantity());
            double price7=Double.parseDouble(itemModelArrayList.get(6).getPrice());
            double total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(itemModelArrayList.get(7).getDescription());
            tv_Anzahl_8.setText(itemModelArrayList.get(7).getQuantity());
            tv_Einzelpreis_8.setText(itemModelArrayList.get(7).getPrice());
            double quantity8=Double.parseDouble(itemModelArrayList.get(7).getQuantity());
            double price8=Double.parseDouble(itemModelArrayList.get(7).getPrice());
            double total8=quantity8*price8;
            tv_Betrag_8.setText(total8+"");

            netto=total1+total2+total3+total4+total5+total6+total7+total8;
        }
        else if(itemModelArrayList.size()==9){
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            double quantity4=Double.parseDouble(itemModelArrayList.get(3).getQuantity());
            double price4=Double.parseDouble(itemModelArrayList.get(3).getPrice());
            double total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            double quantity5=Double.parseDouble(itemModelArrayList.get(4).getQuantity());
            double price5=Double.parseDouble(itemModelArrayList.get(4).getPrice());
            double total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            double quantity6=Double.parseDouble(itemModelArrayList.get(5).getQuantity());
            double price6=Double.parseDouble(itemModelArrayList.get(5).getPrice());
            double total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            double quantity7=Double.parseDouble(itemModelArrayList.get(6).getQuantity());
            double price7=Double.parseDouble(itemModelArrayList.get(6).getPrice());
            double total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(itemModelArrayList.get(7).getDescription());
            tv_Anzahl_8.setText(itemModelArrayList.get(7).getQuantity());
            tv_Einzelpreis_8.setText(itemModelArrayList.get(7).getPrice());
            double quantity8=Double.parseDouble(itemModelArrayList.get(7).getQuantity());
            double price8=Double.parseDouble(itemModelArrayList.get(7).getPrice());
            double total8=quantity8*price8;
            tv_Betrag_8.setText(total8+"");

            //TODO: ROW 9
            tv_pos_9.setText("9");
            tv_detail_9.setText(itemModelArrayList.get(8).getDescription());
            tv_Anzahl_9.setText(itemModelArrayList.get(8).getQuantity());
            tv_Einzelpreis_9.setText(itemModelArrayList.get(8).getPrice());
            double quantity9=Double.parseDouble(itemModelArrayList.get(8).getQuantity());
            double price9=Double.parseDouble(itemModelArrayList.get(8).getPrice());
            double total9=quantity9*price9;
            tv_Betrag_9.setText(total9+"");

            netto=total1+total2+total3+total4+total5+total6+total7+total8+total9;
        }
        else{
            //TODO: ROW 1
            tv_pos_1.setText("1");
            tv_detail_1.setText(itemModelArrayList.get(0).getDescription());
            tv_Anzahl_1.setText(itemModelArrayList.get(0).getQuantity());
            tv_Einzelpreis_1.setText(itemModelArrayList.get(0).getPrice());
            double quantity1=Double.parseDouble(itemModelArrayList.get(0).getQuantity());
            double price1=Double.parseDouble(itemModelArrayList.get(0).getPrice());
            double total1=quantity1*price1;
            tv_Betrag_1.setText(total1+"");

            //TODO: ROW 2
            tv_pos_2.setText("2");
            tv_detail_2.setText(itemModelArrayList.get(1).getDescription());
            tv_Anzahl_2.setText(itemModelArrayList.get(1).getQuantity());
            tv_Einzelpreis_2.setText(itemModelArrayList.get(1).getPrice());
            double quantity2=Double.parseDouble(itemModelArrayList.get(1).getQuantity());
            double price2=Double.parseDouble(itemModelArrayList.get(1).getPrice());
            double total2=quantity2*price2;
            tv_Betrag_2.setText(total2+"");

            //TODO: ROW 3
            tv_pos_3.setText("3");
            tv_detail_3.setText(itemModelArrayList.get(2).getDescription());
            tv_Anzahl_3.setText(itemModelArrayList.get(2).getQuantity());
            tv_Einzelpreis_3.setText(itemModelArrayList.get(2).getPrice());
            double quantity3=Double.parseDouble(itemModelArrayList.get(2).getQuantity());
            double price3=Double.parseDouble(itemModelArrayList.get(2).getPrice());
            double total3=quantity3*price3;
            tv_Betrag_3.setText(total3+"");

            //TODO: ROW 4
            tv_pos_4.setText("4");
            tv_detail_4.setText(itemModelArrayList.get(3).getDescription());
            tv_Anzahl_4.setText(itemModelArrayList.get(3).getQuantity());
            tv_Einzelpreis_4.setText(itemModelArrayList.get(3).getPrice());
            double quantity4=Double.parseDouble(itemModelArrayList.get(3).getQuantity());
            double price4=Double.parseDouble(itemModelArrayList.get(3).getPrice());
            double total4=quantity4*price4;
            tv_Betrag_4.setText(total4+"");

            //TODO: ROW 5
            tv_pos_5.setText("5");
            tv_detail_5.setText(itemModelArrayList.get(4).getDescription());
            tv_Anzahl_5.setText(itemModelArrayList.get(4).getQuantity());
            tv_Einzelpreis_5.setText(itemModelArrayList.get(4).getPrice());
            double quantity5=Double.parseDouble(itemModelArrayList.get(4).getQuantity());
            double price5=Double.parseDouble(itemModelArrayList.get(4).getPrice());
            double total5=quantity5*price5;
            tv_Betrag_5.setText(total5+"");

            //TODO: ROW 6
            tv_pos_6.setText("6");
            tv_detail_6.setText(itemModelArrayList.get(5).getDescription());
            tv_Anzahl_6.setText(itemModelArrayList.get(5).getQuantity());
            tv_Einzelpreis_6.setText(itemModelArrayList.get(5).getPrice());
            double quantity6=Double.parseDouble(itemModelArrayList.get(5).getQuantity());
            double price6=Double.parseDouble(itemModelArrayList.get(5).getPrice());
            double total6=quantity6*price6;
            tv_Betrag_6.setText(total6+"");

            //TODO: ROW 7
            tv_pos_7.setText("7");
            tv_detail_7.setText(itemModelArrayList.get(6).getDescription());
            tv_Anzahl_7.setText(itemModelArrayList.get(6).getQuantity());
            tv_Einzelpreis_7.setText(itemModelArrayList.get(6).getPrice());
            double quantity7=Double.parseDouble(itemModelArrayList.get(6).getQuantity());
            double price7=Double.parseDouble(itemModelArrayList.get(6).getPrice());
            double total7=quantity7*price7;
            tv_Betrag_7.setText(total7+"");

            //TODO: ROW 8
            tv_pos_8.setText("8");
            tv_detail_8.setText(itemModelArrayList.get(7).getDescription());
            tv_Anzahl_8.setText(itemModelArrayList.get(7).getQuantity());
            tv_Einzelpreis_8.setText(itemModelArrayList.get(7).getPrice());
            double quantity8=Double.parseDouble(itemModelArrayList.get(7).getQuantity());
            double price8=Double.parseDouble(itemModelArrayList.get(7).getPrice());
            double total8=quantity8*price8;
            tv_Betrag_8.setText(total8+"");

            //TODO: ROW 9
            tv_pos_9.setText("9");
            tv_detail_9.setText(itemModelArrayList.get(8).getDescription());
            tv_Anzahl_9.setText(itemModelArrayList.get(8).getQuantity());
            tv_Einzelpreis_9.setText(itemModelArrayList.get(8).getPrice());
            double quantity9=Double.parseDouble(itemModelArrayList.get(8).getQuantity());
            double price9=Double.parseDouble(itemModelArrayList.get(8).getPrice());
            double total9=quantity9*price9;
            tv_Betrag_9.setText(total9+"");

            //TODO: ROW 10
            tv_pos_10.setText("10");
            tv_detail_10.setText(itemModelArrayList.get(9).getDescription());
            tv_Anzahl_10.setText(itemModelArrayList.get(9).getQuantity());
            tv_Einzelpreis_10.setText(itemModelArrayList.get(9).getPrice());
            double quantity10=Double.parseDouble(itemModelArrayList.get(9).getQuantity());
            double price10=Double.parseDouble(itemModelArrayList.get(9).getPrice());
            double total10=quantity10*price10;
            tv_Betrag_10.setText(total10+"");
            netto=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
        }
       /* tv_netto.setText(netto+"");
        int gst_19_perc=(netto*19)/100;
        tv_19_perc.setText(gst_19_perc+"");
        tv_advance.setText(advance);
        int advanceInt=Integer.parseInt(advance);
        int grand_total=netto+gst_19_perc-advanceInt;
        tv_grand_total.setText(grand_total+"");*/
        tv_netto.setText(netto+"");
        double gst_19_perc = (netto / 100.0f) * 19;
        tv_19_perc.setText(String.format("%.2f",gst_19_perc));
        Log.d("aaaa", "fillTable: "+netto+"  "+gst_19_perc);
        tv_advance.setText(advance);
        double advanceInt=Double.parseDouble(advance);
        double grand_total=netto+gst_19_perc-advanceInt;
        tv_grand_total.setText(String.format("%.2f", grand_total));
    }
   /* public void layoutToImage(View view) {
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
            Log.d("sss", "imageToPDF: "+e.getMessage());
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
                    - document.rightMargin() - 140) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            showDialogForPdfOptions();
            Toast.makeText(this, "\n" +
                    "PDF Erfolgreich generiert! ..", Toast.LENGTH_SHORT).show();
            isSaved=true;
        } catch (Exception e) {
            Toast.makeText(DolmetscherRechnung.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DolmetscherRechnung.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(DolmetscherRechnung.this, "File not exist!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(DolmetscherRechnung.this, "File not exist!", Toast.LENGTH_SHORT).show();
        }
    }
}
