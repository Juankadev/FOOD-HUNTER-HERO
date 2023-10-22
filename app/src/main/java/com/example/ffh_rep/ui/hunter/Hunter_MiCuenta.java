package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Usuario;

public class Hunter_MiCuenta extends Fragment {

    private HunterMiCuentaViewModel mViewModel;
    private TextView et_nombre, et_apellido, et_dni, et_sexo, et_correo, et_direccion;

    public static Hunter_MiCuenta newInstance() {
        return new Hunter_MiCuenta();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initializeViews();
        return inflater.inflate(R.layout.fragment_hunter__mi_cuenta, container, false);
    }
    private void initializeViews() {

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HunterMiCuentaViewModel.class);
        // TODO: Use the ViewModel
    }

}