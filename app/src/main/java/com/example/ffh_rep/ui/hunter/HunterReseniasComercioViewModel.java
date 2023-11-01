package com.example.ffh_rep.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.repositories.ComercioRepository;

import java.util.List;

public class HunterReseniasComercioViewModel extends ViewModel {
   private MutableLiveData<List<Resenia>> reseniasList;
   private Context ctx;

   private ComercioRepository cRepo;

   public HunterReseniasComercioViewModel(){}
    public HunterReseniasComercioViewModel(Context ctx) {
        this.reseniasList = new MutableLiveData<>();
        this.ctx = ctx;
        this.cRepo = new ComercioRepository();
    }

    public MutableLiveData<List<Resenia>> getReseniasList() {
        return reseniasList;
    }

    public void setReseniasList(MutableLiveData<List<Resenia>> reseniasList) {
        this.reseniasList = reseniasList;
    }

    public void cargarResenias(Comercio commerce){
       cRepo.cargarResenias(this.reseniasList, commerce);
    }
}