package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.admin.AdminEstadisticasViewModel;

public class AdminEstadisticasViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public AdminEstadisticasViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AdminEstadisticasViewModel.class)){
            return (T) new AdminEstadisticasViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
