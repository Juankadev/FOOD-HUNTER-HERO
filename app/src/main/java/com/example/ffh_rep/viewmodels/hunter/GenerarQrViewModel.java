package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.entidades.QrObject;
import com.example.ffh_rep.models.repositories.QrRepository;

public class GenerarQrViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<Boolean> success;
    private MutableLiveData<Boolean> error;
    private MutableLiveData<Integer> idGenerado;

    private MutableLiveData<Boolean> cazaAprobada;

    public QrRepository qrRepository;

    public GenerarQrViewModel(Context ctx){
        this.ctx = ctx;

        this.loading = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.success = new MutableLiveData<>();
        this.idGenerado = new MutableLiveData<>();
        this.cazaAprobada = new MutableLiveData<>();
        this.qrRepository = new QrRepository();
    }

    public void generarQr(QrObject qr){
        qrRepository.insertQrData(qr, this.loading, this.success, this.error, this.idGenerado);
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

    public MutableLiveData<Integer> getIdGenerado() {
        return idGenerado;
    }

    public void checkQRStatus(JSONQRRequest obj){
        qrRepository.pollingIntoQrState(this.cazaAprobada, obj);
    }

    public MutableLiveData<Boolean> getCazaAprobada() {
        return cazaAprobada;
    }
}