package com.example.ffh_rep.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.ui.admin.AdminAprobarComerciosViewModel;
import com.example.ffh_rep.ui.hunter.HunterReseniasComercioViewModel;

public class AprobarComerciosViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public AprobarComerciosViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AdminAprobarComerciosViewModel.class)){
            return (T) new AdminAprobarComerciosViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
