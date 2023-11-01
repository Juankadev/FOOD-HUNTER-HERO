package com.example.ffh_rep.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.repositories.ArticuloRepository;

import java.util.List;

public class ArticulosViewModel extends ViewModel {
    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;
    private ArticuloRepository aRepo;
    public ArticulosViewModel(Context ctx){
        this.ctx = ctx;
        this.aRepo = new ArticuloRepository();
        this.mldArticulos = new MutableLiveData<>();
    }
    /**
     * Carga todos los comercios disponibles utilizando el repositorio de comercios.
     */
    public void cargarArticulos(){
        aRepo.getArticulos(mldArticulos);
    }
    public MutableLiveData<List<Articulo>> getMldArticulos() {
        return mldArticulos;
    }
}