package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.models.repositories.ArticuloRepository;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.List;

public class ComercioHomeViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;
    private ArticuloRepository aRepo;
    private ComercioRepository cRepo;

    public ComercioHomeViewModel(){}

    public ComercioHomeViewModel(Context ctx){
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