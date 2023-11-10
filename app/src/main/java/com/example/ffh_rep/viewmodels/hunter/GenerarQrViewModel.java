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
    private MutableLiveData<Boolean> cazaRechazada;
    private MutableLiveData<Boolean> pollingEnabled;

    private MutableLiveData<Boolean> qrDeleted;

    public QrRepository qrRepository;

    public GenerarQrViewModel(Context ctx){
        this.ctx = ctx;

        this.loading = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.success = new MutableLiveData<>();
        this.idGenerado = new MutableLiveData<>();
        this.cazaAprobada = new MutableLiveData<>(false);
        this.cazaRechazada = new MutableLiveData<>(false);
        this.qrDeleted = new MutableLiveData<>(false);
        this.qrRepository = new QrRepository();

        this.pollingEnabled = new MutableLiveData<>(true);
    }

    public void generarQr(QrObject qr){
        qrRepository.insertQrData(qr, this.loading, this.success, this.error, this.idGenerado);
    }

    public void deleteQr(JSONQRRequest response){
        qrRepository.qrDelete(response, this.qrDeleted);
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
        qrRepository.pollingIntoQrState(this.cazaAprobada, obj, this.pollingEnabled, this.cazaRechazada);
    }

    public MutableLiveData<Boolean> getCazaAprobada() {
        return cazaAprobada;
    }

    public MutableLiveData<Boolean> getPollingEnabled() {
        return pollingEnabled;
    }

    public void setPollingEnabled(boolean pollingEnabled) {
        this.pollingEnabled.setValue(pollingEnabled);
    }

    public MutableLiveData<Boolean> getCazaRechazada() {
        return cazaRechazada;
    }

    public void resetAttributes(){
        loading.setValue(false);
        success.setValue(false);
        error.setValue(false);
        idGenerado.setValue(null);
        cazaAprobada.setValue(false);
        cazaRechazada.setValue(false);
    }
}
