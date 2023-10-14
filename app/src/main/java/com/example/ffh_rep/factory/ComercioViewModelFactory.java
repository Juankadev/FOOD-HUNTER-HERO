package com.example.ffh_rep.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.ui.hunter.ComerciosViewModel;
import com.example.ffh_rep.ui.hunter.HunterHomeViewModel;

public class ComercioViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ComercioViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ComerciosViewModel.class)){
            return (T) new ComerciosViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
