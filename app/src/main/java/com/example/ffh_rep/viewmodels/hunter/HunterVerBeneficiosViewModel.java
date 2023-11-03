package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.List;

public class HunterVerBeneficiosViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Beneficio>> listBeneficios;

    private ComercioRepository cRepo;

    public HunterVerBeneficiosViewModel(Context ctx){
        this.ctx = ctx;
        this.listBeneficios = new MutableLiveData<>();
        this.cRepo = new ComercioRepository();
    }

    public void cargarBeneficios(Comercio commerce){
        cRepo.cargarBeneficios(listBeneficios, commerce);
    }

    public MutableLiveData<List<Beneficio>> getListBeneficios() {
        return listBeneficios;
    }

    public void canjearBeneficio(){

    }
}