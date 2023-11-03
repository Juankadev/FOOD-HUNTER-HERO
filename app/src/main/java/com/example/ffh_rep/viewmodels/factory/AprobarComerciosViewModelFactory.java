package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.admin.AdminAprobarComerciosViewModel;

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
