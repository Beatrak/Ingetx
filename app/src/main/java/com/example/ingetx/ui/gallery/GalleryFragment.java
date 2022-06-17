package com.example.ingetx.ui.gallery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ingetx.databinding.FragmentGalleryBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryFragment extends Fragment {
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
    String mail="";

    TextView nombre;
    TextView ap_paterno;
    TextView ap_materno;
    TextView noControl;
    TextView correo;


    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        preferences = requireActivity().getSharedPreferences("preferencias",0);
        cargarDatos();

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        noControl = binding.noControlFill;
        noControl.setText(username);

        ap_paterno = binding.aPFill;
        ap_paterno.setText(last_name);

        ap_materno = binding.aMFill;
        ap_materno.setText(third_name);

        nombre = binding.nombreFill;
        nombre.setText(name);

        correo = binding.mailFill;
        correo.setText(mail);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
            mail = usuario.getString("email");



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}