package com.example.ingetx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Principal extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    JSONObject alumno= null;
    JSONObject usuario = null;
    String objeto="";
    String token = "";
    String name= "";
    String  last_name= "";
    String third_name= "";
    String username= "";
    String  message= "";

    TextView nombreAl;
    Button cierraSesion;
    Button dashboard;
    boolean login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        preferences = getSharedPreferences("preferencias",0);
        login = preferences.getBoolean("sesion",false);


        nombreAl = (TextView) findViewById(R.id.nombreAl);
        cierraSesion = (Button) findViewById(R.id.cierraSesion);
        dashboard = (Button) findViewById(R.id.dashboard);

        cierraSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences cierra = getSharedPreferences("preferencias",0);
                SharedPreferences.Editor editor = cierra.edit();
                editor.putBoolean("sesion",false);
                editor.commit();

                Intent cierraSes = new Intent(Principal.this, MainActivity.class);
                startActivity(cierraSes);
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantalla();
            }
        });


        if(login){
            cargarDatos();
            Toast.makeText(this,"Bienvenido: "+name,Toast.LENGTH_SHORT).show();
            nombreAl.setText(name+" "+last_name+" "+third_name);


        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void pantalla (){
        Intent intent = new Intent(Principal.this,Tablero_Alumno.class);
        startActivity(intent);
    }

    public void cargarDatos(){
        objeto = this.preferences.getString("jason","");

        try {
            alumno = new JSONObject(objeto);
            usuario = alumno.getJSONObject("user");
            token = alumno.getString("token");
            name = usuario.getString("name");
            last_name = usuario.getString("last_name");
            third_name = usuario.getString("third_name");
            username = usuario.getString("username");
            message = alumno.getString("message");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}



