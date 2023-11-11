package com.example.ffh_rep.viewmodels.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.models.repositories.ArticuloRepository;
import com.example.ffh_rep.models.repositories.ComercioRepository;



import java.util.List;

public class HunterHomeViewModel extends ViewModel {
   private Context context;
   private MutableLiveData<List<Comercio>> mlDataComercio;
   private MutableLiveData<List<Articulo>> mlDataArticulo;
   private MutableLiveData<List<Comercio>> originalList;

   private MutableLiveData<Boolean> successComercios;
   private MutableLiveData<Boolean> sucessArticulos;
   private ComercioRepository cRepo;
   private ArticuloRepository aRepo;
   public HunterHomeViewModel() {
   }

   public HunterHomeViewModel(Context ctx){
      this.context = ctx;
      this.mlDataComercio = new MutableLiveData<>();
      this.mlDataArticulo = new MutableLiveData<>();
      this.successComercios = new MutableLiveData<>(false);
      this.sucessArticulos = new MutableLiveData<>(false);
      this.cRepo = new ComercioRepository();
      this.aRepo = new ArticuloRepository();
   }

   public void cargarComercios(int id){
      cRepo.getComercios(originalList, mlDataComercio, id, successComercios);
   }

   public void cargarListArticulo(){
      aRepo.getArticulos(mlDataArticulo, sucessArticulos);
   }


   public MutableLiveData<List<Articulo>> getMlDataArticulo() {
      return mlDataArticulo;
   }

   public MutableLiveData<List<Comercio>> getMlDataComercio() {
      return mlDataComercio;
   }

   public MutableLiveData<Boolean> getSuccessComercios() {
      return successComercios;
   }

   public MutableLiveData<Boolean> getSucessArticulos() {
      return sucessArticulos;
   }
}