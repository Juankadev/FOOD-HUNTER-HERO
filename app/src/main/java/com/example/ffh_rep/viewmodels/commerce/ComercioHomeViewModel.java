package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.models.repositories.ArticuloRepository;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.ArrayList;
import java.util.List;

public class ComercioHomeViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;
    private MutableLiveData<List<Articulo>> originalListArticulos;
    private ArticuloRepository aRepo;
    private ComercioRepository cRepo;

    public ComercioHomeViewModel(){}

    public ComercioHomeViewModel(Context ctx){
        this.ctx = ctx;
        this.mldArticulos = new MutableLiveData<>();
        this.aRepo = new ArticuloRepository();
        this.cRepo = new ComercioRepository();
        this.originalListArticulos = new MutableLiveData<>();
    }
    public MutableLiveData<List<Articulo>> getMldArticulos() {
        return mldArticulos;
    }

    public void cargarArticulos(int id){
        aRepo.getArticulosByIdComercio(originalListArticulos, mldArticulos, id);
    }

    public void applyFilter(String _secuence) {
        List<Articulo> originalList = mldArticulos.getValue();
        Log.d("Original List", originalList.toString());
        if (originalList != null) {
            if (_secuence.equals("") || _secuence.isEmpty() ) {
                mldArticulos.postValue(originalListArticulos.getValue());
            } else {
                List<Articulo> filtered = new ArrayList<>();
                for (Articulo item : originalList) {
                    if (item.getDescripcion().toLowerCase().contains(_secuence.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                mldArticulos.postValue(filtered);
            }
        }
    }

}