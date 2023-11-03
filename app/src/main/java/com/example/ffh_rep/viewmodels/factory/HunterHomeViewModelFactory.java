package com.example.ffh_rep.viewmodels.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.viewmodels.hunter.HunterHomeViewModel;

public class HunterHomeViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public HunterHomeViewModelFactory(Context ctx){
        this.context = ctx;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(HunterHomeViewModel.class)){
            return (T) new HunterHomeViewModel(context);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }
}
