package com.example.ffh_rep.repositories;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ComercioRepository {

    public void getComercios(MutableLiveData<List<Comercio>> mlDataComercio){
        new AsyncTask<Void, Void, List<Comercio>>(){
            @Override
            protected List<Comercio> doInBackground(Void... voids) {
                List<Comercio> lComercios = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Comercios c where c.aprobado like 'Aprobado' ";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();

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
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Comercios task", lComercios.toString());
                return lComercios;
            }

            @Override
            protected void onPostExecute(List<Comercio> comercios) {
                super.onPostExecute(comercios);
                mlDataComercio.postValue(comercios);
            }
        }.execute();
    }

}
