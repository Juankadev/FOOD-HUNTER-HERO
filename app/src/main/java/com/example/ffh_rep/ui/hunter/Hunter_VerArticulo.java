package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterVerArticuloBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;

public class Hunter_VerArticulo extends Fragment {

    private HunterVerArticuloViewModel mViewModel;
    private Articulo article;
    private FragmentHunterVerArticuloBinding binding;
    private TextView descripcion, precio;

    public static Hunter_VerArticulo newInstance() {
        return new Hunter_VerArticulo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding  = FragmentHunterVerArticuloBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        descripcion = view.findViewById(R.id.tvDescripcionArt);
        precio = view.findViewById(R.id.tvPrecioArt);
        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("articuloSelected")){
                article = (Articulo) bundle.getSerializable("articuloSelected");
            }
        }

        precio.setText(String.valueOf(article.getPrecio()));
        descripcion.setText(article.getDescripcion());

       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterVerArticuloViewModel.class);
        // TODO: Use the ViewModel
    }

}