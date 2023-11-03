package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.List;

public class ComerciosViewModel extends ViewModel {
    private Context ctx;

    private MutableLiveData<List<Comercio>> mldComercios;
    private ComercioRepository cRepo;

    public ComerciosViewModel(Context ctx){
        this.ctx = ctx;
        this.cRepo = new ComercioRepository();
        this.mldComercios = new MutableLiveData<>();
    }
    /**
     * Carga todos los comercios disponibles utilizando el repositorio de comercios.
     */
    public void cargarComercios(){
        cRepo.getComercios(mldComercios);
    }
    /**
     * Carga los comercios disponibles filtrando por razon social utilizando el repositorio de comercios.
     */
    public void cargarComerciosByName(String razonSocial){
        cRepo.getComercioByName(mldComercios, razonSocial);
    }

    public MutableLiveData<List<Comercio>> getMldComercios() {
        return mldComercios;
    }
}
