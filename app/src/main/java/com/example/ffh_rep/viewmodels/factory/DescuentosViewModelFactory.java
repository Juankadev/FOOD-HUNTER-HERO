package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.commerce.MisDescuentosComercioViewModel;


public class DescuentosViewModelFactory implements ViewModelProvider.Factory {
private Context ctx;

public DescuentosViewModelFactory(Context ctx){
    this.ctx = ctx;
}
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MisDescuentosComercioViewModel.class)){
            return (T) new MisDescuentosComercioViewModel(ctx);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }

}
