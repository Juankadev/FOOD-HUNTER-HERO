package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.commerce.ComercioHomeViewModel;

public class ComercioHomeViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ComercioHomeViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ComercioHomeViewModel.class)){
            return (T) new ComercioHomeViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}