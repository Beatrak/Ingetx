package com.example.ingetx.ui.slideshow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ingetx.Tablero_Alumno;
import com.example.ingetx.VerPdf;
import com.example.ingetx.databinding.FragmentSlideshowBinding;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class SlideshowFragment extends Fragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ActivityResultLauncher<String> dGetContent;
    String archivo="";


    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences = getActivity().getSharedPreferences("subida",0);
        editor = preferences.edit();

        final TextView textView = binding.inicio;
        textView.setText("Agrega los siguientes documentos a tu expediente digital");

        dGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result == null){
                    try {
                        this.finalize();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }else{
                    archivo= result.toString();
                    editor.putString("ruta",archivo);
                    editor.commit();
                    //Toast.makeText(getActivity(),archivo,Toast.LENGTH_LONG).show();
                }
            }
        });

        final Button subir, ver, eliminar;
        subir = binding.subir;
        ver = binding.ver;
        eliminar= binding.eliminar;


        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dGetContent.launch("application/pdf");
            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (archivo.isEmpty()){
                    Toast.makeText(getActivity(),"No hay ningún archivo cargado",Toast.LENGTH_SHORT).show();
                }else{
                    Intent ver = new Intent(getActivity(), VerPdf.class);
                    ver.putExtra("leer",archivo);
                    startActivity(ver);
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (archivo.isEmpty()){
                    Toast.makeText(getActivity(),"No hay ningún archivo cargado",Toast.LENGTH_SHORT).show();
                }else{
                archivo="";
                Toast.makeText(getActivity(),"Archivo Eliminado Correctamente",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}