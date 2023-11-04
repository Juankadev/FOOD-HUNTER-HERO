package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.models.repositories.StockRepository;

import java.sql.Date;

public class ComercioVerArticuloViewModel extends ViewModel {
    private Context ctx;
    private MutableLiveData<Stock> obtenerStock;
    private MutableLiveData<Boolean> agregarStock;
    private MutableLiveData<Boolean> restarStock;
    private MutableLiveData<Boolean> nuevoRegistroStock;
    private StockRepository stockRepository;
    public ComercioVerArticuloViewModel(){}
    public ComercioVerArticuloViewModel(Context ctx){
        this.ctx = ctx;
        this.agregarStock = new MutableLiveData<>();
        this.restarStock = new MutableLiveData<>();
        this.nuevoRegistroStock = new MutableLiveData<>();
        this.stockRepository = new StockRepository();
    }

}