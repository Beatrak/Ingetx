package com.example.ingetx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
    ArrayAdapter<String> arrayAdapter;

    String seleccion="";

    Spinner carreras;

    ArrayList<String> oferta = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_admision);

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

        preferences = getSharedPreferences("preferencias",0);
        cargarDatos();

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, oferta);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        carreras = findViewById(R.id.carreras_spinner);

        carreras.setAdapter(arrayAdapter);

        carreras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccion = parent.getItemAtPosition(position).toString();
                Toast.makeText(FichaAdmision.this,seleccion,Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
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