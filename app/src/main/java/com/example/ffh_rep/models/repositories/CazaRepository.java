package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Caza;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CazaRepository {
/*
   public MutableLiveData<List<Caza>> getCazas_by_idHunter(Integer id){
        new AsyncTask<Void, Void, List<Caza>>(){
            @Override
            protected List<Caza> doInBackground(Void... voids) {
                List<Caza> lCazas = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Cazas where id_hunter = ?" ;
                    ps = con.prepareStatement(query);
                    ps.setInt(1, id);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Caza aData = new Caza();
                        int id_caza = rs.getInt("id_caza");
                        int id_comercio = rs.getInt("id_comercio");
                        int id_hunter = rs.getInt("id_hunter");
                        int puntos = rs.getInt("puntos");

                        aData.setIdCaza(id_caza);

                        Comercio comercio = new Comercio();
                        comercio.setId(id_comercio);
                        aData.setComercio(comercio);

                        Hunter hunter = new Hunter();
                        hunter.setIdHunter(id_hunter);
                        aData.setHunter(hunter);


                        aData.setPuntos(puntos);

                        lCazas.add(aData);
                    }
                    rs.close();
                    ps.close();
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Cazas task", lCazas.toString());
                return lCazas;
            }

            @Override
            protected void onPostExecute(List<Caza> cazas) {
                super.onPostExecute(cazas);
                if(cazas.isEmpty()){
                    //ldata.postValue(new ArrayList<>());
                    return;
                }
                else{
                    //ldata.postValue(cazas);
                }
            }
        }.execute();
        return null;
    }
    */

    /*public MutableLiveData<List<Caza>> getCazasByIdHunter(MutableLiveData<List<Caza>> mlDataComercio, int id_hunter) {
        CompletableFuture.supplyAsync(() -> {
            List<Caza> lCazas = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * from Cazas");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_caza");
                    String razonsocial = rs.getString("razon_social");
                    String fecha = rs.getString("fecha");
                    int puntos = rs.getInt("puntos");
                    Caza caza = new Caza(id, puntos);
                    lCazas.add(caza);
                }
            } catch (Exception e) {
                // Manejar la excepción adecuadamente, por ejemplo, registrándola o lanzándola de nuevo
                e.printStackTrace();
            }
            return lCazas;
        }).thenAcceptAsync(comercios -> mlDataComercio.postValue(comercios));
        return mlDataComercio;
    }

    public void getCazas(MutableLiveData<List<Caza>> ldataCazas){
        new AsyncTask<Void, Void, List<Caza>>(){
            @Override
            protected List<Caza> doInBackground(Void... voids) {
                List<Caza> lCazas = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Cazas";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Caza aData = new Caza();
                        int id_caza = rs.getInt("id_caza");
                        int id_comercio = rs.getInt("id_comercio");
                        int id_hunter = rs.getInt("id_hunter");
                        int puntos = rs.getInt("puntos");

                        aData.setIdCaza(id_caza);

                        Comercio comercio = new Comercio();
                        comercio.setId(id_comercio);
                        aData.setComercio(comercio);

                        Hunter hunter = new Hunter();
                        hunter.setIdHunter(id_hunter);
                        aData.setHunter(hunter);


                        aData.setPuntos(puntos);

                        lCazas.add(aData);
                    }
                    rs.close();
                    ps.close();
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Cazas task", lCazas.toString());
                return lCazas;
            }

            @Override
            protected void onPostExecute(List<Caza> cazas) {
                super.onPostExecute(cazas);
                if(cazas.isEmpty()){
                    ldataCazas.postValue(new ArrayList<>());
                }
                else{
                    ldataCazas.postValue(cazas);
                }
            }
        }.execute();
    }
*/



    public MutableLiveData<List<Caza>> getCazasByIdHunter(MutableLiveData<List<Caza>> original,MutableLiveData<List<Caza>> mlDataComercio, int id_hunter) {
        CompletableFuture.supplyAsync(() -> {
            List<Caza> lCazas = new ArrayList<>();

            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT com.razon_social as razon_social, c.fecha as fecha, c.puntos as puntos, cxa.Cantidad as cantidad " +
                         "FROM Hunters as h " +
                         "inner join Cazas as c on h.id_hunter = c.id_hunter " +
                         "inner join Comercios as com on c.id_comercio = com.id_comercio " +
                         "inner join Caza_x_Articulo as cxa on cxa.ID_caza = c.id_caza " +
                         "where c.id_hunter = " + id_hunter +
                         " ORDER BY c.fecha  DESC");

                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //int id = rs.getInt("id_caza");
                    String razonsocial = rs.getString("razon_social");
                    Date fecha = rs.getDate("fecha");
                    int puntos = rs.getInt("puntos");
                    int cantidad = rs.getInt("cantidad");

                    Comercio comercio = new Comercio();
                    comercio.setRazonSocial(razonsocial);

                    Caza caza = new Caza(comercio,fecha, puntos,cantidad);
                    lCazas.add(caza);
                }
            } catch (Exception e) {
                // Manejar la excepción adecuadamente, por ejemplo, registrándola o lanzándola de nuevo
                e.printStackTrace();
            }

            return lCazas;
        }).thenAcceptAsync(cazas -> {
            original.postValue(cazas);
            mlDataComercio.postValue(cazas);
        });


        return mlDataComercio;
    }


}




/*String query =  ;
            */