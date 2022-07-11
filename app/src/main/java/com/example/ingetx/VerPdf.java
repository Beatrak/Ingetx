package com.example.ingetx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.shockwave.pdfium.PdfDocument;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class VerPdf extends AppCompatActivity {
    PDFView doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pdf);

        String archivo = getIntent().getStringExtra("leer");
        Uri u = Uri.parse(archivo);
        doc= findViewById(R.id.pdfVer);
        doc.fromUri(u).load();
        init();

    }

    private void init(){
        Toolbar toolbar = this.findViewById(R.id.barra);
        toolbar.setNavigationOnClickListener(view -> {
            this.finish();
        });
    }
}