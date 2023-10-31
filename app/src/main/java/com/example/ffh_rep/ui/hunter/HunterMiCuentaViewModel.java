package com.example.ffh_rep.ui.hunter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.repositories.HunterRepository;

public class HunterMiCuentaViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<Hunter> hunterData;
    private HunterRepository hunterRepo;
    private MutableLiveData<Boolean> deleteAccount;
    private MutableLiveData<Boolean> updatingInfo;
    public HunterMiCuentaViewModel(){}

    public HunterMiCuentaViewModel(Context ctx) {
        this.ctx = ctx;
        this.hunterData = new MutableLiveData<>();
        this.deleteAccount = new MutableLiveData<>(false);
        this.updatingInfo = new MutableLiveData<>(false);
        this.hunterRepo = new HunterRepository();
    }



    public MutableLiveData<Hunter> getHunterData() {
        return hunterData;
    }

    public void setHunterDataWithHunte(Hunter hunter){
        this.hunterData.setValue(hunter);
    }

    public void updateInformation(Hunter hunter){
        hunterRepo.updateUserInfo(this.hunterData, hunter, this.updatingInfo);
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
}