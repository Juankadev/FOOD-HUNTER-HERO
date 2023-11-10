package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.models.repositories.QrRepository;

import java.util.List;

public class ComercioAprobarCompraViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<ItemCarrito>> mlArticulos;
    private MutableLiveData<Integer> idHunter;
    private MutableLiveData<Integer> idComercio;

    private MutableLiveData<Boolean> loading;
    private MutableLiveData<Boolean> success;
    private MutableLiveData<Boolean> error;

    private MutableLiveData<Boolean> loadingReject;
    private MutableLiveData<Boolean> successReject;
    private MutableLiveData<Boolean> errorReject;


    private QrRepository qrRepository;

    public ComercioAprobarCompraViewModel(Context ctx){
        this.ctx = ctx;
        this.mlArticulos = new MutableLiveData<>();
        this.idComercio = new MutableLiveData<>(0);
        this.idHunter = new MutableLiveData<>(0);
        this.qrRepository = new QrRepository();

        this.loading = new MutableLiveData<>(false);
        this.error = new MutableLiveData<>(false);
        this.success = new MutableLiveData<>(false);

        this.loadingReject = new MutableLiveData<>(false);
        this.successReject = new MutableLiveData<>(false);
        this.errorReject = new MutableLiveData<>(false);
    }

    public MutableLiveData<List<ItemCarrito>> getMlArticulos() {
        return mlArticulos;
    }

    public void setMlArticulos(List<ItemCarrito> mlArticulos) {
        this.mlArticulos.postValue(mlArticulos);
    }

    public MutableLiveData<Integer> getIdHunter() {
        return idHunter;
    }

    public void setIdHunter(int idHunter) {
        this.idHunter.postValue(idHunter);
    }

    public MutableLiveData<Integer> getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio.postValue(idComercio);
    }

    public void aprobeHunt(JSONQRRequest response){
        qrRepository.aprobeHunt(response, this.loading, this.success, this.error);
    }

    public void rejectHunt(JSONQRRequest response){
        qrRepository.rejectHunt(response, this.loadingReject, this.successReject, this.errorReject);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoadingReject() {
        return loadingReject;
    }

    public MutableLiveData<Boolean> getSuccessReject() {
        return successReject;
    }

    public MutableLiveData<Boolean> getErrorReject() {
        return errorReject;
    }

    public void resetVariables(){
        this.loading.setValue(false);
        this.error.setValue(false);
        this.success.setValue(false);

        this.loadingReject.setValue(false);
        this.successReject.setValue(false);
        this.errorReject.setValue(false);
    }
}