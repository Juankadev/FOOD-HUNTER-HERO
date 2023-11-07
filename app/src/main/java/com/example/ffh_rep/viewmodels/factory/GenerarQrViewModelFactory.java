package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.commerce.MisDescuentosComercioViewModel;
import com.example.ffh_rep.viewmodels.hunter.GenerarQrViewModel;

public class GenerarQrViewModelFactory implements ViewModelProvider.Factory {
    private Context ctx;

    public GenerarQrViewModelFactory(Context ctx){
        this.ctx = ctx;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GenerarQrViewModel.class)){
            return (T) new GenerarQrViewModel(ctx);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }


}
