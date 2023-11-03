package com.example.ffh_rep.models.repositories;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MarcaRepository {
    public void getMarcas(MutableLiveData<List<Marca>> ldataMarcas){
        new AsyncTask<Void, Void, List<Marca>>(){
            @Override
            protected List<Marca> doInBackground(Void... voids) {
                List<Marca> lMarcas = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Marcas";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Marca aData = new Marca();
                        int id_marca = rs.getInt("id_marca");
                        String descripcion = rs.getString("descripcion");

                        aData.setIdMarca(id_marca);
                        aData.setDescripcion(descripcion);

                        lMarcas.add(aData);
                    }
                    rs.close();
                    ps.close();
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Marcas task", lMarcas.toString());
                return lMarcas;
            }

            @Override
            protected void onPostExecute(List<Marca> marcas) {
                super.onPostExecute(marcas);
                if(marcas.isEmpty()){
                    ldataMarcas.postValue(new ArrayList<>());
                }
                else{
                    ldataMarcas.postValue(marcas);
                }
            }
        }.execute();
    }
}
