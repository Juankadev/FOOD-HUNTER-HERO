package com.example.ffh_rep.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.repositories.ArticuloRepository;

import java.util.List;

public class HunterVerComercioViewModel extends ViewModel {
    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;

    private ArticuloRepository aRepo;
    public HunterVerComercioViewModel(Context ctx){
        this.ctx = ctx;
        this.mldArticulos = new MutableLiveData<>();
        this.aRepo = new ArticuloRepository();
    }

    public void cargarArticulos(int id){
        aRepo.getArticulos_by_idComercio(mldArticulos, id);
    }

    public MutableLiveData<List<Articulo>> getMldArticulos() {
        return mldArticulos;
    }
}