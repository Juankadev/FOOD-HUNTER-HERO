package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.models.repositories.ArticuloRepository;

import java.util.Date;
import java.util.List;

public class ArticulosViewModel extends ViewModel {
    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;
    private MutableLiveData<Boolean> success;
    private ArticuloRepository aRepo;
    public ArticulosViewModel(Context ctx){
        this.ctx = ctx;
        this.aRepo = new ArticuloRepository();
        this.mldArticulos = new MutableLiveData<>();
        this.success = new MutableLiveData<>(false);
    }
    /**
     * Carga todos los comercios disponibles utilizando el repositorio de comercios.
     */
    public void cargarArticulos(){
        aRepo.getArticulos(mldArticulos, success);
    }
    public MutableLiveData<List<Articulo>> getMldArticulos() {
        return mldArticulos;
    }
    public void reducirStock(ItemCarrito item) { aRepo.reducirStock(item); }
    public void agregarStock(ItemCarrito item) { aRepo.agregarStock(item); }
}