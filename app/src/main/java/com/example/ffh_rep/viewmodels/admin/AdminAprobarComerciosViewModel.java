package com.example.ffh_rep.viewmodels.admin;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.List;

public class AdminAprobarComerciosViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private Context ctx;
    private MutableLiveData<List<Comercio>> listComercios;

    private ComercioRepository cRepo;

    public AdminAprobarComerciosViewModel(){}

    public AdminAprobarComerciosViewModel(Context ctx){
        this.ctx = ctx;
        this.listComercios = new MutableLiveData<>();
        this.cRepo = new ComercioRepository();
    }

    public void cargarComerciosNoAprobados(){
        cRepo.cargarComerciosNoAprobados(listComercios);
    }

    public MutableLiveData<List<Comercio>> getListComerciosNoAprobados() {
        return listComercios;
    }

    public void rechazarComercio(Comercio c){
        cRepo.rechazarComercio(c, ctx);
    }
    public void aprobarComercio(Comercio c){
        cRepo.aprobarComercio(c, ctx);
    }
}