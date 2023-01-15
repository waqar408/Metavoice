package com.example.metaphraseinvoice;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metaphraseinvoice.InputActivitites.DezimalINputActivity;
import com.example.metaphraseinvoice.InputActivitites.NormezeilenInputActivity;


public class SelectInvoiceActivity extends AppCompatActivity {
    RecyclerView rv_invoice;
    TextView tv_angebot,tv_PRIVATEKUNDEN,tv_DOLMETSCHER,tv_DEZIMAL,tv_NORMEZEILEN;
    Button btn_selectAngebot,btn_nextAngebot;
    RelativeLayout rel_angebot,rel_dolm,rel_dezimal,rel_privat,rel_norm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_invoice);
       // checkPermission();
       // requestPermission();
        permission();
        tv_PRIVATEKUNDEN = findViewById(R.id.tv_PRIVATEKUNDEN);
        rel_angebot = findViewById(R.id.rel_angebot);
        rel_angebot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectInvoiceActivity.this, MainActivity.class);
                intent.putExtra("from","tv_angebot");
                startActivity(intent);
            }
        });
        rel_privat = findViewById(R.id.privat);
        rel_privat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectInvoiceActivity.this, MainActivity.class);
                intent.putExtra("from","tv_PRIVATEKUNDEN");
                startActivity(intent);
            }
        });
        rel_dolm = findViewById(R.id.rel_dolme);
        rel_dolm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectInvoiceActivity.this, MainActivity.class);
                intent.putExtra("from","tv_DOLMETSCHER");
                startActivity(intent);
            }
        });
        rel_dezimal = findViewById(R.id.rel_dezimal) ;
        rel_dezimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SelectInvoiceActivity.this, DezimalINputActivity.class);
                intent.putExtra("from","tv_DEZIMAL");
                startActivity(intent);
            }
        });
        rel_norm =findViewById(R.id.rel_norm);
        rel_norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectInvoiceActivity.this, NormezeilenInputActivity.class);
                intent.putExtra("from","tv_Norm");
                startActivity(intent);
            }
        });


    }
    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(SelectInvoiceActivity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(SelectInvoiceActivity.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }
    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(SelectInvoiceActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, 100);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void permission()
    {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()){

    // If you don't have access, launch a new activity to show the user the system's dialog
    // to allow access to the external storage
            }else{
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
}
