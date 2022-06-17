package com.example.ingetx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    JSONObject jsonObject = null;
    JSONObject usuario = null;
    EditText numControl,password;
    Button login;
    String token = "";
    String name= "";
    String  last_name= "";
    String third_name= "";
    String username= "";
    String  message= "";
    RequestQueue queue;
    boolean login_status=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        numControl= (EditText) findViewById(R.id.noControl);
        password= (EditText) findViewById(R.id.contra);
        login= (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            readWs();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

        finish();
        System.exit(0);

    }

    private void readWs(){
        String getNC = numControl.getText().toString();
        String getPWD = password.getText().toString();

        String url = "http://192.168.1.73:8000/login/";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                try {
                    jsonObject = new JSONObject(response);
                    usuario= jsonObject.getJSONObject("user");
                    token = jsonObject.getString("token");
                    name = usuario.getString("name");
                    last_name = usuario.getString("last_name");
                    third_name = usuario.getString("third_name");
                    username = usuario.getString("username");
                    message = jsonObject.getString("message");

                    login_status=true;
                    iniciarPrincipal();



                }
                catch (JSONException e) {

                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", getNC);
                params.put("password",getPWD);
                return params;
            }
        }
                ;
        queue.add(postRequest);


        //Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void iniciarPrincipal(){
        if (jsonObject.toString() != null){
            Intent intent = new Intent(MainActivity.this, Tablero_Alumno.class);
            intent.putExtra("objeto", jsonObject.toString());
            guardarDatos();
            startActivity(intent);
            //Toast.makeText(MainActivity.this,jsonObject.toString(),Toast.LENGTH_LONG).show();

        }else{
            iniciarPrincipal();
        }

    }

    private void guardarDatos(){
        preferences= getSharedPreferences("preferencias",0);
        editor = preferences.edit();
        editor.putBoolean("sesion",login_status);
        editor.putString("jason",jsonObject.toString());
        editor.commit();

    }

    }
