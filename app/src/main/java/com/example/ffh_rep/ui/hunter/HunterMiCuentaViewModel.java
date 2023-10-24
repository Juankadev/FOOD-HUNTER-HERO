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
    public HunterMiCuentaViewModel(){}


    public HunterMiCuentaViewModel(Context ctx) {
        this.ctx = ctx;
        this.hunterData = new MutableLiveData<>();
        this.hunterRepo = new HunterRepository();
    }


    public void updateInformation(){
        Log.d("Mutable Live Data", this.hunterData.toString());
    }
    public MutableLiveData<Hunter> getHunterData() {
        return hunterData;
    }

    public void setHunterDataWithHunte(Hunter hunter){
        this.hunterData.postValue(hunter);
        Log.d("SETTING", "setHunterDataWithHunte: " + this.hunterData.toString());
    }
    public void setHunterData(MutableLiveData<Hunter> hunterData) {
        this.hunterData = hunterData;
    }
}