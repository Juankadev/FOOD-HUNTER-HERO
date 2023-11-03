package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.hunter.HunterVerComercioViewModel;

public class HunterVerComercioViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public HunterVerComercioViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(HunterVerComercioViewModel.class)){
            return (T) new HunterVerComercioViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}