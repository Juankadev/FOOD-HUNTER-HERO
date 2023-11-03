package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.models.repositories.ComercioRepository;
import com.example.ffh_rep.models.repositories.DescuentoRepository;

import java.util.List;

public class MisDescuentosComercioViewModel extends ViewModel {
   private Context ctx;
   private MutableLiveData<List<Beneficio>> mldListaBeneficios;
   private DescuentoRepository dRepo;
   private ComercioRepository cRepo;

   public MisDescuentosComercioViewModel(){}

   public MisDescuentosComercioViewModel(Context ctx){
      this.ctx = ctx;
      this.mldListaBeneficios = new MutableLiveData<>();
      this.dRepo = new DescuentoRepository();
      this.cRepo = new ComercioRepository();
   }

   public MutableLiveData<List<Beneficio>> getMldListaBeneficios() {
      return mldListaBeneficios;
   }

   public void insertarBeneficio(Beneficio bene){
      dRepo.agregarDescuento(ctx, bene);
   }
   public void eliminarBeneficio(Beneficio bene){dRepo.eliminarDescuento(ctx, bene);}
   public void editarBeneficio(Beneficio bene){dRepo.modificarDescuento(ctx, bene);}
   public void listarDescuentos(int id){
      dRepo.listarDescuentosByComercio(mldListaBeneficios,id);
   }
}