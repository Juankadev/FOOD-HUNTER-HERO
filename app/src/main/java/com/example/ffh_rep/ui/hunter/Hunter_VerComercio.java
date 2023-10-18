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
import com.example.ffh_rep.databinding.FragmentHunterVerComercioBinding;
import com.example.ffh_rep.entidades.Comercio;

public class Hunter_VerComercio extends Fragment {

    private HunterVerComercioViewModel mViewModel;
    private Comercio commerce;
    private TextView descripcion;

    private FragmentHunterVerComercioBinding binding;

    public static Hunter_VerComercio newInstance() {
        return new Hunter_VerComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHunterVerComercioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        descripcion = view.findViewById(R.id.txt_shop_name_description);
        Bundle bundle = getArguments();

        if(bundle != null){
            if(bundle.containsKey("comercioSelect")){
                commerce = (Comercio) bundle.getSerializable("comercioSelect");
            }
        }

        descripcion.setText(commerce.getRazonSocial());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterVerComercioViewModel.class);
    }

}