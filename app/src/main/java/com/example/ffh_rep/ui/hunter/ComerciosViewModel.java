package com.example.ffh_rep.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.repositories.ComercioRepository;

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

    public void cargarComercios(){
        cRepo.getComercios(mldComercios);
    }

    public MutableLiveData<List<Comercio>> getMldComercios() {
        return mldComercios;
    }
}
