package com.example.ffh_rep.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.repositories.ArticuloRepository;
import com.example.ffh_rep.repositories.ComercioRepository;

import java.util.List;

public class HunterVerComercioViewModel extends ViewModel {
    private Context ctx;
    private MutableLiveData<List<Articulo>> mldArticulos;
    private MutableLiveData<Boolean> isMarkingAsFav;
    private MutableLiveData<Boolean>  markSuccess;
    private MutableLiveData<Boolean>  errorMarking;
    private MediatorLiveData<Boolean> markingStack;

    private ArticuloRepository aRepo;
    private ComercioRepository cRepo;

    public HunterVerComercioViewModel(){}
    public HunterVerComercioViewModel(Context ctx){
        this.ctx = ctx;
        this.mldArticulos = new MutableLiveData<>();
        this.isMarkingAsFav = new MutableLiveData<>(false);
        this.markSuccess = new MutableLiveData<>(false);
        this.errorMarking = new MutableLiveData<>(false);
        this.markingStack = new MediatorLiveData<>();
        this.aRepo = new ArticuloRepository();
        this.cRepo = new ComercioRepository();


        this.markingStack.addSource(isMarkingAsFav, value -> this.markingStack.setValue(value));
        this.markingStack.addSource(markSuccess, value -> this.markingStack.setValue(value));
        this.markingStack.addSource(errorMarking, value -> this.markingStack.setValue(value));

    }

    public void cargarArticulos(int id){
        aRepo.getArticulosByIdComercio(mldArticulos, id);
    }

    public void markAsFav(Comercio commerce, Hunter ht){
        cRepo.markAsFavorite(commerce, ht, this.isMarkingAsFav, this.markSuccess, this.errorMarking);
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
}