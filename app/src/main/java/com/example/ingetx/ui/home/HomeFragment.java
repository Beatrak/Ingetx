package com.example.ingetx.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ingetx.FichaAdmision;
import com.example.ingetx.Referencia;
import com.example.ingetx.Tablero_Alumno;
import com.example.ingetx.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button referencia = binding.referenciaButton;
        referencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ref = new Intent(HomeFragment.this.binding.textHome.getContext(), Referencia.class);
                startActivity(ref);
            }
        });

        Button ficha = binding.fichaButton;
        ficha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fic = new Intent(HomeFragment.this.binding.textHome.getContext(), FichaAdmision.class);
                startActivity(fic);
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