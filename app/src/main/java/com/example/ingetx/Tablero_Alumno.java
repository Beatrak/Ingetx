package com.example.ingetx;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ingetx.databinding.ActivityTableroAlumnoBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class Tablero_Alumno extends AppCompatActivity {

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
    String correo= "";
    boolean login;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTableroAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTableroAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTableroAlumno.toolbar);
        binding.appBarTableroAlumno.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tablero_alumno);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        preferences = getSharedPreferences("preferencias",0);
        login = preferences.getBoolean("sesion",false);

        if(login){
            cargarDatos();

        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


           }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tablero__alumno, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tablero_alumno);
        TextView nombreAl = (TextView) findViewById(R.id.nombreAlumno);
        TextView noControl = (TextView) findViewById(R.id.noControlDash);
        noControl.setText(username);
        nombreAl.setText(name+" "+last_name+" "+third_name);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();


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

    public boolean cerrarSesion(MenuItem item){
        SharedPreferences cierra = getSharedPreferences("preferencias",0);
        SharedPreferences.Editor editor = cierra.edit();
        editor.putBoolean("sesion",false);
        editor.commit();

        Intent cierraSes = new Intent(Tablero_Alumno.this, MainActivity.class);
        startActivity(cierraSes);
        return true;
    }

}