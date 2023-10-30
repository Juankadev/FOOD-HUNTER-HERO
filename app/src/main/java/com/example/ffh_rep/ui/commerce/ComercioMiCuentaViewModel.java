package com.example.ffh_rep.ui.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.repositories.ComercioRepository;

public class ComercioMiCuentaViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<Comercio> comercioData;
    private ComercioRepository commerceRepo;
    private MutableLiveData<Boolean> deleteAccount;
    public ComercioMiCuentaViewModel(){}

    public ComercioMiCuentaViewModel(Context ctx) {
        this.ctx = ctx;
        this.comercioData = new MutableLiveData<>();
        this.deleteAccount = new MutableLiveData<>(false);
        this.commerceRepo = new ComercioRepository();
    }

    public MutableLiveData<Comercio> getCommerceData() {
        return comercioData;
    }

    public void setCommerceDataWithCommerce(Comercio comercio){this.comercioData.setValue(comercio);}

    public void updateInformation(Comercio comercio){
        commerceRepo.updateUserInfo(this.comercioData, comercio, this.ctx);
    }

    public void eliminarMiCuenta(){
        Comercio aux = this.comercioData.getValue();
        commerceRepo.eliminarCuenta(aux, deleteAccount, ctx);
    }
    public void setCommerceData(MutableLiveData<Comercio> commerceData) {
        this.comercioData = commerceData;
    }

    public MutableLiveData<Boolean> getDeleteAccount() {
        return deleteAccount;
    }
}
