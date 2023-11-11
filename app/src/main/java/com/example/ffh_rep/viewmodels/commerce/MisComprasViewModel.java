package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Caza;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.models.repositories.CazaRepository;
import com.example.ffh_rep.models.repositories.ComercioRepository;
import com.example.ffh_rep.models.repositories.QrRepository;

import java.util.ArrayList;
import java.util.List;

public class MisComprasViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Caza>> lCazas;
    private MutableLiveData<List<Caza>> listaOriginal;
    private CazaRepository cRepo;

    public MisComprasViewModel(Context ctx){
        this.ctx = ctx;
        this.lCazas = new MutableLiveData<>();
        this.listaOriginal = new MutableLiveData<>();
        this.cRepo = new CazaRepository();
    }

    public void cargarCazasPorIdHunter(Integer id) {
        cRepo.getCazasByIdHunter(this.listaOriginal,this.lCazas,id);
    }
    public MutableLiveData<List<Caza>> getCazasDelHunter() {
        return lCazas;
    }

    public void applyFilter(String _secuence) {
        Log.d("Secuencia", _secuence);
        List<Caza> originalList = listaOriginal.getValue();
        if (originalList != null) {
            if (_secuence.equals("") || _secuence.isEmpty() ) {
                lCazas.postValue(originalList);
            } else {
                List<Caza> filtered = new ArrayList<>();
                for (Caza item : originalList) {
                    if (item.getComercio().getRazonSocial().toLowerCase().contains(_secuence.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                lCazas.postValue(filtered);
            }
        }
    }
}