package com.example.ffh_rep;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentAgregarDescuentoComercio extends Fragment {

    private FragmentAgregarDescuentoComercioViewModel mViewModel;

    public static FragmentAgregarDescuentoComercio newInstance() {
        return new FragmentAgregarDescuentoComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_agregar_descuento_comercio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentAgregarDescuentoComercioViewModel.class);
        // TODO: Use the ViewModel
    }

}