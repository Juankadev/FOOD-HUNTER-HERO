package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.commerce.ComercioVerArticuloViewModel;
import com.example.ffh_rep.viewmodels.commerce.ComercioVerStockXArticuloViewModel;
import com.example.ffh_rep.viewmodels.hunter.HunterVerBeneficiosViewModel;

public class ComercioVerStocksArticuloViewModelFactory implements ViewModelProvider.Factory{
    private final Context context;

    public ComercioVerStocksArticuloViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ComercioVerStockXArticuloViewModel.class)){
            return (T) new ComercioVerStockXArticuloViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }

}
