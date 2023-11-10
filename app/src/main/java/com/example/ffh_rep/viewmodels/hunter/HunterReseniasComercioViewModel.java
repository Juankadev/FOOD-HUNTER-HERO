package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.List;

public class HunterReseniasComercioViewModel extends ViewModel {
   private MutableLiveData<List<Resenia>> reseniasList;
   private MutableLiveData<Boolean> loadingSendResenia;
   private MutableLiveData<Boolean> successSendResenia;
   private MutableLiveData<Boolean> errorSendResenia;
   private Context ctx;

   private ComercioRepository cRepo;

   public HunterReseniasComercioViewModel(){}
    public HunterReseniasComercioViewModel(Context ctx) {
        this.reseniasList = new MutableLiveData<>();
        this.loadingSendResenia = new MutableLiveData<>(false);
        this.successSendResenia = new MutableLiveData<>(false);
        this.errorSendResenia = new MutableLiveData<>(false);
        this.ctx = ctx;
        this.cRepo = new ComercioRepository();
    }

    public MutableLiveData<List<Resenia>> getReseniasList() {
        return reseniasList;
    }

    public void setReseniasList(MutableLiveData<List<Resenia>> reseniasList) {
        this.reseniasList = reseniasList;
    }

    public MutableLiveData<Boolean> getLoadingSendResenia() {
        return loadingSendResenia;
    }

    public void setLoadingSendResenia(boolean value) {
        this.loadingSendResenia.postValue(value);
    }

    public MutableLiveData<Boolean> getSuccessSendResenia() {
        return successSendResenia;
    }

    public void setSuccessSendResenia(boolean value) {
        this.successSendResenia.postValue(value);
    }

    public MutableLiveData<Boolean> getErrorSendResenia() {
        return errorSendResenia;
    }

    public void setErrorSendResenia(boolean value) {
        this.errorSendResenia.postValue(value);
    }

    public void cargarResenias(Comercio commerce){
       cRepo.cargarResenias(this.reseniasList, commerce);
    }

    public void generarResenia(Resenia res){
       cRepo.generarResenia(res, this.loadingSendResenia, this.successSendResenia, this.errorSendResenia);
    }

    public void resetValues(){
        this.loadingSendResenia.setValue(false);
        this.successSendResenia.setValue(false);
        this.errorSendResenia.setValue(false);
    }
}