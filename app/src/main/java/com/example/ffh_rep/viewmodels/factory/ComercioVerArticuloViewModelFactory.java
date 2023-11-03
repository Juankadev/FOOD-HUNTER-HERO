package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.commerce.ComercioVerArticuloViewModel;

public class ComercioVerArticuloViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ComercioVerArticuloViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ComercioVerArticuloViewModel.class)){
            return (T) new ComercioVerArticuloViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
