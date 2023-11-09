package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.models.repositories.HunterRepository;
import com.example.ffh_rep.utils.SessionManager;

public class HunterMiCuentaViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<Hunter> hunterData;
    private HunterRepository hunterRepo;
    private MutableLiveData<Boolean> deleteAccount;
    private MutableLiveData<Boolean> updatingInfo;
    private MutableLiveData<Boolean> successUpdate;
    private MutableLiveData<Boolean> errorUpdate;
    private MutableLiveData<Hunter> data;

    private MutableLiveData<Boolean> successRangoUpdate;
    private MutableLiveData<Boolean> errorRangoUpdate;
    public HunterMiCuentaViewModel(){}

    public HunterMiCuentaViewModel(Context ctx) {
        this.ctx = ctx;
        this.hunterData = new MutableLiveData<>();
        this.deleteAccount = new MutableLiveData<>(false);
        this.updatingInfo = new MutableLiveData<>(false);
        this.successUpdate = new MutableLiveData<>(false);
        this.errorUpdate = new MutableLiveData<>(false);
        this.successRangoUpdate = new MutableLiveData<>(false);
        this.errorRangoUpdate = new MutableLiveData<>(false);
        this.hunterRepo = new HunterRepository();
    }

    public void refreshAccount(Hunter hunter, SessionManager session){
        hunterRepo.actualizarRango(hunter, this.successRangoUpdate, this.errorRangoUpdate, session);
    }

    public MutableLiveData<Hunter> getHunterData() {
        return hunterData;
    }

    public void setHunterDataWithHunte(Hunter hunter){
        this.hunterData.setValue(hunter);
    }

    public void updateInformation(Hunter hunter){
        hunterRepo.updateUserInfo(this.hunterData, hunter, this.updatingInfo, this.successUpdate, this.errorUpdate);
    }

    public void eliminarMiCuenta(){
        Hunter aux = this.hunterData.getValue();
        hunterRepo.eliminarCuenta(aux, deleteAccount, ctx);
    }
    public void setHunterData(MutableLiveData<Hunter> hunterData) {
        this.hunterData = hunterData;
    }

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

    public MutableLiveData<Boolean> getSuccessRangoUpdate() {
        return successRangoUpdate;
    }

    public MutableLiveData<Boolean> getErrorRangoUpdate() {
        return errorRangoUpdate;
    }

    public void resetSuccessRango(){
        this.successRangoUpdate.setValue(false);
        this.errorRangoUpdate.setValue(false);
    }
}