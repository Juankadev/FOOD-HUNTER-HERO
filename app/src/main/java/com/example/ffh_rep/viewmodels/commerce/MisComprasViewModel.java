package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Caza;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.models.repositories.CazaRepository;
import com.example.ffh_rep.models.repositories.ComercioRepository;
import com.example.ffh_rep.models.repositories.QrRepository;

import java.util.List;

public class MisComprasViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Caza>> lCazas;
    private CazaRepository cRepo;

    public MisComprasViewModel(Context ctx){
        this.ctx = ctx;
        this.lCazas = new MutableLiveData<>();
        this.cRepo = new CazaRepository();
    }
/*
    public MutableLiveData<List<Caza>> getCazas_by_idHunter(Integer id) {
        return cRepo.getCazas_by_idHunter(id);*/
    /*
public MutableLiveData<List<Caza>> cargarCazasPorIdHunter(MutableLiveData<List<Caza>> mlDataComercio, Integer id) {
    return cRepo.getCazasByIdHunter(mlDataComercio, id);
    }*/
    public void cargarCazasPorIdHunter(Integer id) {
        cRepo.getCazasByIdHunter(lCazas,id);
    }
    public MutableLiveData<List<Caza>> getCazasDelHunter() {
        return lCazas;
    }
}