package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.models.repositories.ArticuloRepository;
import com.example.ffh_rep.models.repositories.ComercioRepository;
import com.example.ffh_rep.models.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;

public class HunterVerComercioViewModel extends ViewModel {
    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;
    private MutableLiveData<List<Stock>> originalStockList;
    private MutableLiveData<List<Stock>> mldStockArticulos;
    private MutableLiveData<Boolean> isMarkingAsFav;
    private MutableLiveData<Boolean>  markSuccess;
    private MutableLiveData<Boolean>  errorMarking;

    private MutableLiveData<Boolean> isDismarkingAsFav;
    private MutableLiveData<Boolean>  dismarkSuccess;
    private MutableLiveData<Boolean>  errorDismarking;

    private MutableLiveData<Boolean> loadingStocks;
    private MutableLiveData<Boolean>  successStocks;
    private MutableLiveData<Boolean>  errorStocks;


    private ArticuloRepository aRepo;
    private ComercioRepository cRepo;
    private StockRepository sRepo;

    public HunterVerComercioViewModel(){}
    public HunterVerComercioViewModel(Context ctx){
        this.ctx = ctx;
        this.mldArticulos = new MutableLiveData<>();
        this.mldStockArticulos  = new MutableLiveData<>();
        this.originalStockList = new MutableLiveData<>();
        this.isMarkingAsFav = new MutableLiveData<>(false);
        this.markSuccess = new MutableLiveData<>(false);
        this.errorMarking = new MutableLiveData<>(false);

        this.isDismarkingAsFav = new MutableLiveData<>(false);
        this.dismarkSuccess = new MutableLiveData<>(false);
        this.errorDismarking = new MutableLiveData<>(false);

        this.loadingStocks = new MutableLiveData<>(false);
        this.successStocks = new MutableLiveData<>(false);
        this.errorStocks = new MutableLiveData<>(false);

        this.aRepo = new ArticuloRepository();
        this.cRepo = new ComercioRepository();
        this.sRepo = new StockRepository();

    }

    public void resetFavAtts(){
        this.isMarkingAsFav.setValue(false);
        this.markSuccess.setValue(false);
        this.errorMarking.setValue(false);

        this.isDismarkingAsFav.setValue(false);
        this.dismarkSuccess.setValue(false);
        this.errorDismarking.setValue(false);
    }

    public void cargarArticulos(int id){
        aRepo.getArticulosByIdComercioWithStock(mldArticulos, id);
    }

    public void markAsFav(Comercio commerce, Hunter ht){
        cRepo.markAsFavorite(commerce, ht, this.isMarkingAsFav, this.markSuccess, this.errorMarking);
    }

    public void dismarkAsFav(Comercio commerce, Hunter ht){
        cRepo.dismarkAsFavorite(commerce, ht, this.isDismarkingAsFav, this.dismarkSuccess, this.errorDismarking);
    }

    public void getStockbyArticulos(Comercio commerce){
        sRepo.getStockByComercioAndFechaVencimiento(this.originalStockList, this.mldStockArticulos,this.loadingStocks,this.successStocks,this.errorStocks ,commerce);
    }

    public void setListaOriginal(List<Stock> stock){
        this.originalStockList.postValue(stock);
    }
    public void setIsMarkingAsFav(boolean value) {
        isMarkingAsFav.setValue(value);
    }

    public void setMarkSuccess(boolean value) {
        markSuccess.setValue(value);
    }

    public void setErrorMarking(boolean value) {
        errorMarking.setValue(value);
    }

    public MutableLiveData<Boolean> getIsMarkingAsFav() {
        return isMarkingAsFav;
    }

    public MutableLiveData<Boolean> getMarkSuccess() {
        return markSuccess;
    }

    public MutableLiveData<Boolean> getErrorMarking() {
        return errorMarking;
    }

    public MutableLiveData<List<Articulo>> getMldArticulos() {
        return mldArticulos;
    }

    public MutableLiveData<Boolean> getIsDismarkingAsFav() {
        return isDismarkingAsFav;
    }

    public void setIsDismarkingAsFav(MutableLiveData<Boolean> isDismarkingAsFav) {
        this.isDismarkingAsFav = isDismarkingAsFav;
    }

    public MutableLiveData<Boolean> getDismarkSuccess() {
        return dismarkSuccess;
    }

    public void setDismarkSuccess(MutableLiveData<Boolean> dismarkSuccess) {
        this.dismarkSuccess = dismarkSuccess;
    }

    public MutableLiveData<Boolean> getErrorDismarking() {
        return errorDismarking;
    }

    public void setErrorDismarking(MutableLiveData<Boolean> errorDismarking) {
        this.errorDismarking = errorDismarking;
    }


    public MutableLiveData<List<Stock>> getMldStockArticulos() {
        return mldStockArticulos;
    }

    public void setMldStockArticulos(List<Stock> mldStockArticulos) {
        this.mldStockArticulos.postValue(mldStockArticulos);
    }

    public MutableLiveData<Boolean> getLoadingStocks() {
        return loadingStocks;
    }

    public MutableLiveData<Boolean> getSuccessStocks() {
        return successStocks;
    }

    public MutableLiveData<Boolean> getErrorStocks() {
        return errorStocks;
    }

    public void applyFilter(String _secuence) {
        Log.d("Secuencia", _secuence);
        List<Stock> originalList = originalStockList.getValue();
        Log.d("Original List", originalList.toString());
        if (originalList != null) {
            if (_secuence.equals("") || _secuence.isEmpty() ) {
                mldStockArticulos.postValue(originalList);
            } else {
                List<Stock> filtered = new ArrayList<>();
                for (Stock item : originalList) {
                    if (item.getId_articulo().getDescripcion().toLowerCase().contains(_secuence.toLowerCase())) {
                        filtered.add(item);
                    }
                }
                mldStockArticulos.postValue(filtered);
            }
        }
    }
}