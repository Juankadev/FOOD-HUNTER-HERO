package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.ArrayList;
import java.util.List;

public class ComerciosViewModel extends ViewModel {
    private Context ctx;

    private MutableLiveData<List<Comercio>> mldComercios;
    private MutableLiveData<List<Comercio>> originalListComercios;
    private MutableLiveData<Boolean> success;
    private ComercioRepository cRepo;

    public ComerciosViewModel(Context ctx){
        this.ctx = ctx;
        this.cRepo = new ComercioRepository();
        this.mldComercios = new MutableLiveData<>();
        this.originalListComercios = new MutableLiveData<>();
        this.success = new MutableLiveData<>(false);
    }
    /**
     * Carga todos los comercios disponibles utilizando el repositorio de comercios.
     */
    public void cargarComercios(int id){
        cRepo.getComercios(this.originalListComercios, this.mldComercios, id, success);
    }
    /**
     * Carga los comercios disponibles filtrando por razon social utilizando el repositorio de comercios.
     */
    public void cargarComerciosByName(String razonSocial){
        cRepo.getComercioByName(this.originalListComercios, this.mldComercios, razonSocial);
    }

    public MutableLiveData<List<Comercio>> getMldComercios() {
        return mldComercios;
    }

    public void applyFilter(String _secuence) {
        Log.d("Secuencia", _secuence);
        List<Comercio> originalList = originalListComercios.getValue();
        if (originalList != null) {
            if (_secuence.equals("") || _secuence.isEmpty() ) {
                mldComercios.postValue(originalList);
            } else {
                List<Comercio> filtered = new ArrayList<>();
                for (Comercio item : originalList) {
                    if (item.getRazonSocial().toLowerCase().contains(_secuence.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                mldComercios.postValue(filtered);
            }
        }
    }
}
