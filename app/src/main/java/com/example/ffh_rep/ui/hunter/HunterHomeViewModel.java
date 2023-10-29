package com.example.ffh_rep.ui.hunter;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.repositories.ArticuloRepository;
import com.example.ffh_rep.repositories.ComercioRepository;



import java.util.List;

import kotlinx.coroutines.GlobalScope;

public class HunterHomeViewModel extends ViewModel {
   private Context context;
   private MutableLiveData<List<Comercio>> mlDataComercio;
   private MutableLiveData<List<Articulo>> mlDataArticulo;
   private ComercioRepository cRepo;
   private ArticuloRepository aRepo;
   public HunterHomeViewModel() {
   }

   public HunterHomeViewModel(Context ctx){
      this.context = ctx;
      this.mlDataComercio = new MutableLiveData<>();
      this.mlDataArticulo = new MutableLiveData<>();
      this.cRepo = new ComercioRepository();
      this.aRepo = new ArticuloRepository();
   }

   public void cargarComercios(){
      cRepo.getComercios(mlDataComercio);
   }

   public void cargarListArticulo(){
      aRepo.getArticulos(mlDataArticulo);
   }


   public MutableLiveData<List<Articulo>> getMlDataArticulo() {
      return mlDataArticulo;
   }

   public MutableLiveData<List<Comercio>> getMlDataComercio() {
      return mlDataComercio;
   }
}