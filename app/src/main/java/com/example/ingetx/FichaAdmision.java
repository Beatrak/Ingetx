package com.example.ingetx;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;

public class FichaAdmision extends AppCompatActivity {
    SharedPreferences preferences;

    JSONObject alumno = null;
    JSONObject usuario = null;
    String objeto = "";
    String token = "";
    String name = "";
    String last_name = "";
    String third_name = "";
    String username = "";
    String message = "";
    String correo = "";
    ArrayAdapter<String> carreraAdapter;
    ArrayAdapter<String> modalidadAdapter;

    String selec_carrera = "";
    String selec_mod = "";

    Spinner carreras;
    Spinner mod;

    ArrayList<String> oferta = new ArrayList<>();
    ArrayList<String> modalidad = new ArrayList<>();

    // declaring width and height
    // for our PDF file.
    int pageHeight = 1120;
    int pagewidth = 792;
    Bitmap bmp, scaledbmp,scaledPfp;
    Bitmap profilePic = null;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Button generar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_admision);

        // initializing our variables.
        generar = findViewById(R.id.ficha_generate);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.itsx);
        profilePic = BitmapFactory.decodeResource(getResources(),R.drawable.perfil);

        scaledbmp = Bitmap.createScaledBitmap(bmp, 120, 140, false);
        scaledPfp = Bitmap.createScaledBitmap(profilePic,160 , 220, false);

        oferta.add("Ingeniería en Sistemas Computacionales");
        oferta.add("Ingeniería Industrial");
        oferta.add("Ingeniería Bioquímica");
        oferta.add("Ingeniería Electromecanica");
        oferta.add("Ingeniería Electrónica");
        oferta.add("Ingeniería en Industrias Alimentarias");
        oferta.add("Licenciatura en Gastronomía");
        oferta.add("Ingeniería Civil");
        oferta.add("Ingeniería Mecatronica");
        oferta.add("Ingeniería Industrial");
        oferta.add("Ingeniería en Gestion Empresarial");

        modalidad.add("Escolarizado");
        modalidad.add("Sabatino");


        if (checkPermission()) {
            //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to
                // generate our PDF file.
                generatePDF();
            }
        });

        preferences = getSharedPreferences("preferencias", 0);
        cargarDatos();

        carreraAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oferta);
        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        modalidadAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modalidad);
        modalidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        carreras = findViewById(R.id.carreras_spinner);
        mod = findViewById(R.id.modalidad_spinner);

        carreras.setAdapter(carreraAdapter);
        mod.setAdapter(modalidadAdapter);

        carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selec_carrera = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selec_mod = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void cargarDatos() {
        objeto = this.preferences.getString("jason", "");

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
    }

    private void generatePDF() {

        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();



        canvas.drawBitmap(scaledbmp, 56, 40, paint);
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(15);
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));



        canvas.drawText(username, 209, 60, title);
        canvas.drawText("Instituto Tecnológico Superior de Xalapa", 209, 80, title);
        canvas.drawText("Solicitud de ficha para nuevo ingreso", 209, 100, title);



        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(15);


        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawBitmap(scaledPfp, 296, 300, paint);
        canvas.drawText("Nombre completo: "+last_name+" "+third_name+" "+name, 396, 560, title);
        canvas.drawText("Número de control: "+username, 396, 580, title);
        canvas.drawText("Correo Electrónico: "+correo, 396, 600, title);
        canvas.drawText("Carrera: "+selec_carrera, 396, 620, title);
        canvas.drawText("Modalidad: "+selec_mod, 396, 640, title);


        pdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(), username+"_ficha.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(FichaAdmision.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FichaAdmision.this, e.toString(),Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Acceso Concedido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Acceso Denegado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}