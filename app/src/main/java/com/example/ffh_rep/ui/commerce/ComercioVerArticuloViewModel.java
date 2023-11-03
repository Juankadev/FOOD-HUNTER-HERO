package com.example.ffh_rep.ui.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.repositories.ComercioRepository;
import com.example.ffh_rep.repositories.StockRepository;

import java.sql.Date;
import java.util.List;

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
    public MutableLiveData<Stock> getObtenerStock() {
        return obtenerStock;
    }
    public void setObtenerStock(Comercio comercio, Articulo articulo){
        stockRepository.getStock(obtenerStock, articulo, comercio);
    }
    public MutableLiveData<Boolean> getNuevoRegistroStock() {
        return nuevoRegistroStock;
    }
    public void setNuevoRegistroStock(Comercio comercio, Articulo articulo, int cantidad, Date fechaVencimiento){
        stockRepository.insertarNuevoStock(nuevoRegistroStock, articulo, comercio, cantidad, fechaVencimiento);
    }
    public MutableLiveData<Boolean> getRestarStock() {
        return restarStock;
    }
    public void setRestarStock(Comercio comercio, Articulo articulo, int cantidad){
        stockRepository.restarStock(restarStock, articulo, comercio, cantidad);
    }
}