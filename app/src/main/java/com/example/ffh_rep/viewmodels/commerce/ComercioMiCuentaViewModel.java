package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.models.repositories.ComercioRepository;

public class ComercioMiCuentaViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<Comercio> comercioData;
    private ComercioRepository commerceRepo;
    private MutableLiveData<Boolean> deleteAccount;
    private MutableLiveData<Boolean> updatingInfo;
    private MutableLiveData<Boolean> successUpdate;
    private MutableLiveData<Boolean> errorUpdate;
    public ComercioMiCuentaViewModel(){}

    public ComercioMiCuentaViewModel(Context ctx) {
        this.ctx = ctx;
        //Se crea una instancia de MutableLiveData llamada comercioData. MutableLiveData es una clase de LiveData que puede ser modificada,
        this.comercioData = new MutableLiveData<>();
        this.deleteAccount = new MutableLiveData<>(false);
        this.updatingInfo = new MutableLiveData<>(false);
        this.successUpdate = new MutableLiveData<>(false);
        this.errorUpdate = new MutableLiveData<>(false);
        this.commerceRepo = new ComercioRepository();
    }

    public MutableLiveData<Comercio> getCommerceData() {
        return comercioData;
    }

    //Se define un método llamado setCommerceDataWithCommerce que toma un objeto Comercio como argumento y utiliza setValue de
    //MutableLiveData para establecer el valor de comercioData con ese objeto Comercio.
    public void setCommerceDataWithCommerce(Comercio comercio){this.comercioData.setValue(comercio);}

    public void updateInformation(Comercio comercio){
        commerceRepo.updateUserInfo(this.comercioData, comercio, this.updatingInfo, this.successUpdate, this.errorUpdate);
    }

    public void eliminarMiCuenta(){
        Comercio aux = this.comercioData.getValue();
        commerceRepo.eliminarCuenta(aux, deleteAccount, ctx);
    }
    public void setCommerceData(MutableLiveData<Comercio> commerceData) {this.comercioData = commerceData;}
    public MutableLiveData<Boolean> getUpdatingInfo() {
        return updatingInfo;
    }
    public MutableLiveData<Boolean> getDeleteAccount() {
        return deleteAccount;
    }
    public MutableLiveData<Boolean> getSuccessUpdate() {
        return successUpdate;
    }

    public void setUpdatingInfo(boolean updatingInfo) {
        this.updatingInfo.postValue(updatingInfo);
    }

    public void setSuccessUpdate(boolean value) {
        this.successUpdate.postValue(value);
    }

    public MutableLiveData<Boolean> getErrorUpdate() {
        return errorUpdate;
    }

    public void setErrorUpdate(boolean value) {
        this.errorUpdate.postValue(value);
    }
}
