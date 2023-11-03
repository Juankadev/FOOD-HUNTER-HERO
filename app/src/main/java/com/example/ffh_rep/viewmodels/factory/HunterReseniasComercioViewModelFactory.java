package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.hunter.HunterReseniasComercioViewModel;

public class HunterReseniasComercioViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public HunterReseniasComercioViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(HunterReseniasComercioViewModel.class)){
            return (T) new HunterReseniasComercioViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
