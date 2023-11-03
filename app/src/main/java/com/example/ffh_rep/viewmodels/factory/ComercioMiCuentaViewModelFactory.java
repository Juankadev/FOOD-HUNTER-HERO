package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.commerce.ComercioMiCuentaViewModel;

public class ComercioMiCuentaViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ComercioMiCuentaViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ComercioMiCuentaViewModel.class)){
            return (T) new ComercioMiCuentaViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
