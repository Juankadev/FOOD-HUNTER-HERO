package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.ItemCarrito;

import java.util.List;

public class ComercioAprobarCompraViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<ItemCarrito>> mlArticulos;

    public ComercioAprobarCompraViewModel(Context ctx){
        this.ctx = ctx;
        this.mlArticulos = new MutableLiveData<>();
    }

    public MutableLiveData<List<ItemCarrito>> getMlArticulos() {
        return mlArticulos;
    }

    public void setMlArticulos(MutableLiveData<List<ItemCarrito>> mlArticulos) {
        this.mlArticulos = mlArticulos;
    }
}