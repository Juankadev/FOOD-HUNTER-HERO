package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.models.repositories.ComercioRepository;
import com.example.ffh_rep.models.repositories.DescuentoRepository;
import com.example.ffh_rep.models.repositories.HunterRepository;
import com.example.ffh_rep.utils.SessionManager;

import java.util.List;

public class HunterVerBeneficiosViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Beneficio>> listBeneficios;

    private MutableLiveData<Boolean> successInsertBene;
    private MutableLiveData<Boolean> loadingInsertBene;

    private MutableLiveData<Boolean> errorInsertBene;

    private MutableLiveData<Boolean> loadingBeneficios;

    private MutableLiveData<Boolean> successBeneficios;
    private MutableLiveData<Boolean> errorBeneficios;
    private ComercioRepository cRepo;
    private DescuentoRepository dRepo;

    private HunterRepository hRepo;

    public HunterVerBeneficiosViewModel(Context ctx) {
        this.ctx = ctx;
        this.listBeneficios = new MutableLiveData<>();
        this.cRepo = new ComercioRepository();
        this.dRepo = new DescuentoRepository();
        this.hRepo = new HunterRepository();

        this.successInsertBene = new MutableLiveData<>(false);
        this.loadingInsertBene = new MutableLiveData<>(false);
        this.errorInsertBene = new MutableLiveData<>(false);
        this.successBeneficios = new MutableLiveData<>(false);
        this.errorBeneficios = new MutableLiveData<>(false);
        this.loadingBeneficios = new MutableLiveData<>(false);
    }

    public void cargarBeneficios(Comercio commerce) {
        cRepo.cargarBeneficios(listBeneficios, commerce, this.loadingBeneficios, this.successBeneficios, this.errorBeneficios);
    }


    public MutableLiveData<List<Beneficio>> getListBeneficios() {
        return listBeneficios;
    }

    public void canjearBeneficio(Hunter hunter, Beneficio bene, SessionManager session) {
        dRepo.insert_Beneficios_hunter(this.loadingInsertBene, this.successInsertBene, this.errorInsertBene, hunter, bene, session);
    }

    public MutableLiveData<Boolean> getSuccessInsertBene() {
        return successInsertBene;
    }

    public MutableLiveData<Boolean> getLoadingInsertBene() {
        return loadingInsertBene;
    }

    public MutableLiveData<Boolean> getErrorInsertBene() {
        return errorInsertBene;
    }

    public void resetInsertValues() {
        this.loadingInsertBene.postValue(false);
        this.errorInsertBene.postValue(false);
        this.successInsertBene.postValue(false);
    }



    public MutableLiveData<Boolean> getLoadingBeneficios() {
        return loadingBeneficios;
    }

    public MutableLiveData<Boolean> getSuccessBeneficios() {
        return successBeneficios;
    }

    public MutableLiveData<Boolean> getErrorBeneficios() {
        return errorBeneficios;
    }
}