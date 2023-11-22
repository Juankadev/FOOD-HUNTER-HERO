package com.example.ffh_rep.viewmodels.commerce;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.models.repositories.ComercioRepository;
import com.example.ffh_rep.models.repositories.DescuentoRepository;

import java.util.ArrayList;
import java.util.List;

public class MisDescuentosComercioViewModel extends ViewModel {
   private Context ctx;
   private MutableLiveData<List<Beneficio>> mldListaBeneficios;
   private MutableLiveData<List<Beneficio>> originalListBeneficios;
   private DescuentoRepository dRepo;
   private ComercioRepository cRepo;

   public MisDescuentosComercioViewModel(){}

   public MisDescuentosComercioViewModel(Context ctx){
      this.ctx = ctx;
      this.mldListaBeneficios = new MutableLiveData<>();
      this.dRepo = new DescuentoRepository();
      this.cRepo = new ComercioRepository();
      this.originalListBeneficios = new MutableLiveData<>();
   }

   /**
    * Esta función proporciona acceso a un objeto MutableLiveData que contiene una lista de beneficios. Esta función
    * observa y obtiene la lista actualizada de beneficios en tiempo real
    */
   public MutableLiveData<List<Beneficio>> getMldListaBeneficios() {
      return mldListaBeneficios;
   }
   public void insertarBeneficio(Beneficio bene){
      dRepo.agregarDescuento(ctx, bene);
   }
   public void eliminarBeneficio(Beneficio bene){dRepo.eliminarDescuento(ctx, bene);}
   public void editarBeneficio(Beneficio bene){dRepo.modificarDescuento(ctx, bene);}
   public void listarDescuentos(int id){
      dRepo.listarDescuentosByComercio(this.originalListBeneficios, this.mldListaBeneficios,id);
   }

   /**
    * Esta función toma una lista de beneficios, filtra los elementos según una secuencia proporcionada y actualiza un
    * objeto MutableLiveData con la lista filtrada.
    * @param _secuence
    */
   public void applyFilter(String _secuence) {
      List<Beneficio> originalList = mldListaBeneficios.getValue();
      Log.d("Original List", originalList.toString());
      if (originalList != null) {
         if (_secuence.equals("") || _secuence.isEmpty() ) {
            mldListaBeneficios.postValue(originalListBeneficios.getValue());
         } else {
            List<Beneficio> filtered = new ArrayList<>();
            for (Beneficio item : originalList) {
               if (item.getDescripcion().toLowerCase().contains(_secuence.toLowerCase())) {
                  filtered.add(item);
               }
            }
            mldListaBeneficios.postValue(filtered);
         }
      }
   }

}