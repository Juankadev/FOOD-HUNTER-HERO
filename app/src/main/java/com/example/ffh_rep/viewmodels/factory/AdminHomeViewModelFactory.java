package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.admin.AdminHomeViewModel;

public class AdminHomeViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public AdminHomeViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AdminHomeViewModel.class)){
            return (T) new AdminHomeViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}