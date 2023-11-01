package com.example.ffh_rep.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.ui.commerce.CommerceMisArticulosViewModel;

public class ComercioMisArticulosViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ComercioMisArticulosViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CommerceMisArticulosViewModel.class)){
            return (T) new CommerceMisArticulosViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}