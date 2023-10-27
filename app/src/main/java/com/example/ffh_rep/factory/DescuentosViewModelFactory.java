package com.example.ffh_rep.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.ui.commerce.DescuentosViewModel;


public class DescuentosViewModelFactory implements ViewModelProvider.Factory {
private Context ctx;

public DescuentosViewModelFactory(Context ctx){
    this.ctx = ctx;
}
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(DescuentosViewModel.class)){
            return (T) new DescuentosViewModel(ctx);
        }

        throw new IllegalArgumentException("ViewModel Class Desconocido");
    }

}
