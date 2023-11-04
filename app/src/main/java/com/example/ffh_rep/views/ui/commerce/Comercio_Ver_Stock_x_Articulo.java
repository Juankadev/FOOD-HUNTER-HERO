package com.example.ffh_rep.views.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffh_rep.R;
import com.example.ffh_rep.viewmodels.commerce.ComercioVerStockXArticuloViewModel;

public class Comercio_Ver_Stock_x_Articulo extends Fragment {

    private ComercioVerStockXArticuloViewModel mViewModel;

    public static Comercio_Ver_Stock_x_Articulo newInstance() {
        return new Comercio_Ver_Stock_x_Articulo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comercio__ver__stock_x__articulo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ComercioVerStockXArticuloViewModel.class);
        // TODO: Use the ViewModel
    }

}