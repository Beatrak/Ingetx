package com.example.ingetx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FichaAdmision extends AppCompatActivity {

    SharedPreferences preferences;

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

    String seleccion="";

    Spinner carreras;

    ArrayList<String> oferta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_admision);

        preferences = getSharedPreferences("preferencias",0);
        cargarDatos();
        oferta.add("Ingeniería en Sistemas Computacionales");
        oferta.add("Ingeniería Industrial");
        oferta.add("Ingeniería Bioquímica");
        oferta.add("Ingeniería Electromecanica");

        carreras = findViewById(R.id.carreras_spinner);

        //carreras.se
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
            correo = usuario.getString("email");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}