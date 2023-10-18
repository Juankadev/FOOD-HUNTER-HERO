package com.example.ffh_rep.repositories;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Caza;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CazaRepository {

    public void getCazas_by_idHunter(MutableLiveData<List<Caza>> ldata, int id){
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
                    ldata.postValue(new ArrayList<>());
                }
                else{
                    ldata.postValue(cazas);
                }
            }
        }.execute();
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
}
