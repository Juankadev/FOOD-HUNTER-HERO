package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.commerce.ComercioAprobarCompraViewModel;
import com.example.ffh_rep.viewmodels.hunter.CarritoViewModel;

public class ComercioAprobarCompraViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ComercioAprobarCompraViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ComercioAprobarCompraViewModel.class)){
            return (T) new ComercioAprobarCompraViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }

}


