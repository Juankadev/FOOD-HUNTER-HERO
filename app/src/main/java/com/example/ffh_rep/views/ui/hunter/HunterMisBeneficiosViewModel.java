package com.example.ffh_rep.views.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.models.repositories.DescuentoRepository;
import com.google.android.material.carousel.MultiBrowseCarouselStrategy;

import java.util.List;

public class HunterMisBeneficiosViewModel extends ViewModel {

    private Context ctx;
    private MutableLiveData<List<Beneficio>> mlBeneficios;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> success;
    private MutableLiveData<Boolean> error;
    private DescuentoRepository dRepo;

    public HunterMisBeneficiosViewModel(Context ctx){
        this.ctx = ctx;
        this.mlBeneficios = new MutableLiveData<>();
        this.dRepo = new DescuentoRepository();

        this.isLoading = new MutableLiveData<>(false);
        this.success = new MutableLiveData<>(false);
        this.error = new MutableLiveData<>(false);
    }

    public MutableLiveData<List<Beneficio>> getMlBeneficios() {
        return mlBeneficios;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public void cargarMisBeneficios(Hunter hunter){
        dRepo.listarDescuentos_byIdHunter(this.mlBeneficios, this.isLoading, this.success, this.error, hunter);
    }
}