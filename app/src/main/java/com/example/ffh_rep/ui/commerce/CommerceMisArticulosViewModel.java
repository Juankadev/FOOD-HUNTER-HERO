package com.example.ffh_rep.ui.commerce;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.repositories.ArticuloRepository;
import com.example.ffh_rep.repositories.ComercioRepository;

import java.util.List;

public class CommerceMisArticulosViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;
    private ArticuloRepository aRepo;
    private ComercioRepository cRepo;

    public CommerceMisArticulosViewModel(){}

    public CommerceMisArticulosViewModel(Context ctx){
        this.ctx = ctx;
        this.mldArticulos = new MutableLiveData<>();
        this.aRepo = new ArticuloRepository();
        this.cRepo = new ComercioRepository();
    }
    public MutableLiveData<List<Articulo>> getMldArticulos() {
        return mldArticulos;
    }

    public void cargarArticulos(int id){
        aRepo.getArticulosByIdComercio(mldArticulos, id);
    }

}