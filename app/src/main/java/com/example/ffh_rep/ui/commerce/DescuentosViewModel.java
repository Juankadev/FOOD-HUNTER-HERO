package com.example.ffh_rep.ui.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.repositories.DescuentoRepository;

import java.util.List;

public class DescuentosViewModel extends ViewModel {
   private Context ctx;
   private MutableLiveData<Beneficio> mldBeneficio;
   private MutableLiveData<List<Beneficio>> mldListaBeneficios;
   private DescuentoRepository dRepo;

   public DescuentosViewModel(){}
   public DescuentosViewModel(Context ctx){
      this.ctx = ctx;
      this.mldBeneficio = new MutableLiveData<>();
      this.mldListaBeneficios = new MutableLiveData<>();
      this.dRepo = new DescuentoRepository();
   }

   public void insertarBeneficio(Beneficio bene){
      dRepo.agregarDescuento(ctx, bene);
   }

   public void listarDescuentos(){
      dRepo.listarDescuentos(mldListaBeneficios);
   }

   public MutableLiveData<Beneficio> getMldBeneficio() {
      return mldBeneficio;
   }

   public MutableLiveData<List<Beneficio>> getMldListaBeneficios() {
      return mldListaBeneficios;
   }
}