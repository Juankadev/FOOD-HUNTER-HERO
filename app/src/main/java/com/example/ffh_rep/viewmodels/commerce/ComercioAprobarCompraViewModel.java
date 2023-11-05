package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.ItemCarrito;

import java.util.List;

public class ComercioAprobarCompraViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<ItemCarrito>> mlArticulos;
    private MutableLiveData<Integer> idHunter;
    private MutableLiveData<Integer> idComercio;

    public ComercioAprobarCompraViewModel(Context ctx){
        this.ctx = ctx;
        this.mlArticulos = new MutableLiveData<>();
        this.idComercio = new MutableLiveData<>(0);
        this.idHunter = new MutableLiveData<>(0);
    }

    public MutableLiveData<List<ItemCarrito>> getMlArticulos() {
        return mlArticulos;
    }

    public void setMlArticulos(List<ItemCarrito> mlArticulos) {
        this.mlArticulos.postValue(mlArticulos);
    }

    public MutableLiveData<Integer> getIdHunter() {
        return idHunter;
    }

    public void setIdHunter(int idHunter) {
        this.idHunter.postValue(idHunter);
    }

    public MutableLiveData<Integer> getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio.postValue(idComercio);
    }
}