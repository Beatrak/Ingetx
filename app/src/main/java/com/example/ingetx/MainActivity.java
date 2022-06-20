package com.example.ingetx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);

        queue = Volley.newRequestQueue(this);
        numControl= (EditText) findViewById(R.id.noControl);
        password= (EditText) findViewById(R.id.contra);
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findViewById(R.id.login).setEnabled(false);
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                connectWS();

            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        setContentView(R.layout.activity_main);

        finish();
        System.exit(0);
    }


    private void connectWS(){
        String getNC = numControl.getText().toString();
        String getPWD = password.getText().toString();

        String url = "http://192.168.1.73:8000/login/";

        NetworkResponseRequest request = new NetworkResponseRequest(Request.Method.POST,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        // This is status code: response.statusCode
                        // This is string response: NetworkResponseRequest.parseToString(response)

                        try {
                            String info = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            jsonObject = new JSONObject(info);
                            usuario= jsonObject.getJSONObject("user");
                            token = jsonObject.getString("token");
                            name = usuario.getString("name");
                            last_name = usuario.getString("last_name");
                            third_name = usuario.getString("third_name");
                            username = usuario.getString("username");
                            message = jsonObject.getString("message");
                            login_status=true;

                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            iniciarTablero();

                        }catch (JSONException e) {
                            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                        }catch (UnsupportedEncodingException u){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                        findViewById(R.id.login).setEnabled(true);
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
        };

        queue.add(request);
    }

    private void iniciarTablero(){
        findViewById(R.id.login).setEnabled(true);
        Intent intent = new Intent(MainActivity.this, Tablero_Alumno.class);
        intent.putExtra("objeto", jsonObject.toString());
        guardarDatos();
        startActivity(intent);
    }

    private void guardarDatos(){
        preferences= getSharedPreferences("preferencias",0);
        editor = preferences.edit();
        editor.putBoolean("sesion",login_status);
        editor.putString("jason",jsonObject.toString());
        editor.commit();

    }

    public static class NetworkResponseRequest extends Request<NetworkResponse> {
        private final Response.Listener<NetworkResponse> mListener;

        public NetworkResponseRequest(int method, String url, Response.Listener<NetworkResponse> listener,
                                      Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            mListener = listener;
        }

        public NetworkResponseRequest(String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
            this(Method.GET, url, listener, errorListener);
        }

        @Override
        protected void deliverResponse(NetworkResponse response) {
            mListener.onResponse(response);
        }

        @Override
        protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
        }

        public static String parseToString(NetworkResponse response) {
            String parsed;
            try {
                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.data);
            }
            return parsed;
        }
    }

    }
