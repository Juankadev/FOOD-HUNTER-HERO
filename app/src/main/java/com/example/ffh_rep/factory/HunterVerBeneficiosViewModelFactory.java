package com.example.ffh_rep.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.ui.hunter.HunterHomeViewModel;
import com.example.ffh_rep.ui.hunter.HunterVerBeneficiosViewModel;

public class HunterVerBeneficiosViewModelFactory implements ViewModelProvider.Factory{
    private final Context context;

    public HunterVerBeneficiosViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(HunterVerBeneficiosViewModel.class)){
            return (T) new HunterVerBeneficiosViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
