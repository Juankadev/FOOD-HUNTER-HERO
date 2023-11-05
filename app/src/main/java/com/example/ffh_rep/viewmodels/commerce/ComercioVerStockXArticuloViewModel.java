package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.models.repositories.ComercioRepository;
import com.example.ffh_rep.models.repositories.DescuentoRepository;
import com.example.ffh_rep.models.repositories.StockRepository;

import java.util.List;

public class ComercioVerStockXArticuloViewModel extends ViewModel {
    private Context ctx;
    private MutableLiveData<List<Stock>> mldListaStocks;
    private StockRepository sRepo;

    public ComercioVerStockXArticuloViewModel(){}

    public ComercioVerStockXArticuloViewModel(Context ctx){
        this.ctx = ctx;
        this.mldListaStocks = new MutableLiveData<>();
        this.sRepo = new StockRepository();
    }
    public MutableLiveData<List<Stock>> getMldListaStocks() {
        return mldListaStocks;
    }
    public void insertarStock(Stock stock){
        sRepo.insertarNuevoStock(ctx, stock);
    }
    public void restarCantidadDeStock(Stock stock, int cantidad){sRepo.restarStock(ctx, stock, cantidad);}
    public void listarStocksArticuloxComercio(Articulo articulo, Comercio comercio){
        sRepo.getStock(mldListaStocks, articulo, comercio);
    }
}