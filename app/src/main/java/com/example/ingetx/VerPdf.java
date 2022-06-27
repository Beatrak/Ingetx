package com.example.ingetx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    SharedPreferences preferences;
    SharedPreferences cargar;
    JSONObject alumno= null;
    JSONObject usuario = null;
    String objeto="";
    String token = "";
    String name= "";
    String  last_name= "";
    String third_name= "";
    String username= "";
    String  message= "";
    String correo= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pdf);

        preferences = getSharedPreferences("subida",0);
        cargar = getSharedPreferences("preferencias",0);

        objeto = this.cargar.getString("jason","");

        try {
            alumno = new JSONObject(objeto);
            usuario = alumno.getJSONObject("user");
            token = alumno.getString("token");
            name = usuario.getString("name");
            last_name = usuario.getString("last_name");
            third_name = usuario.getString("third_name");
            username = usuario.getString("username");
            message = alumno.getString("message");
            correo = usuario.getString("email");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        File file = new File(preferences.getString("ruta",""));
        Uri u = Uri.parse(preferences.getString("ruta",""));
        Toast.makeText(VerPdf.this,file.getPath(),Toast.LENGTH_LONG).show();



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