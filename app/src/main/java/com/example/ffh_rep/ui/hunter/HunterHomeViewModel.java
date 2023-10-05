package com.example.ffh_rep.ui.hunter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HunterHomeViewModel extends ViewModel {
   private Context context;
   private MutableLiveData<List<Comercio>> mlDataComercio;
   public HunterHomeViewModel() {
   }
   public HunterHomeViewModel(Context ctx){
      this.context = ctx;
      this.mlDataComercio = new MutableLiveData<>();
   }

   public void cargarComercios(){
      List<Comercio> lComercios = databinding_onDB();
      this.mlDataComercio.setValue(lComercios);
   }


   public List<Comercio> databinding_onDB(){
      List<Comercio> lComercios = new ArrayList<>();
      Connection con = DBUtil.getConnection();
      String query = "Select * from Comercios where aprobado = 1";

      try {
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery();

         while(rs.next()){
            int id = rs.getInt("id_comercio");
            String razonsocial = rs.getString("razon_social");
            String cuit = rs.getString("cuit");
            String rubro = rs.getString("rubro");
            String email = rs.getString("correo_electronico");
            String telefono = rs.getString("telefono");
            String direccion = rs.getString("direccion");
            String aprobado = rs.getString("aprobado");

            Comercio cData = new Comercio(id, razonsocial, cuit, rubro, email, telefono, direccion, aprobado);
            lComercios.add(cData);
         }

         rs.close();
         ps.close();
      }
      catch (Exception e){
         e.printStackTrace();
      }
      finally {
         DBUtil.closeConnection(con);
      }

      return lComercios;
   }
}