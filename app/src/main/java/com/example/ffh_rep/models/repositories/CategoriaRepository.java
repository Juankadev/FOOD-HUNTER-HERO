package com.example.ffh_rep.models.repositories;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {
    public void getCategorias(MutableLiveData<List<Categoria>> ldataCategorias){
        new AsyncTask<Void, Void, List<Categoria>>(){
            @Override
            protected List<Categoria> doInBackground(Void... voids) {
                List<Categoria> lCategorias = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Categorias";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Categoria aData = new Categoria();
                        int id_categoria = rs.getInt("id_categoria");
                        String descripcion = rs.getString("descripcion");

                        aData.setIdCategoria(id_categoria);
                        aData.setDescripcion(descripcion);

                        lCategorias.add(aData);
                    }
                    rs.close();
                    ps.close();
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Categorias task", lCategorias.toString());
                return lCategorias;
            }

            @Override
            protected void onPostExecute(List<Categoria> categorias) {
                super.onPostExecute(categorias);
                if(categorias.isEmpty()){
                    ldataCategorias.postValue(new ArrayList<>());
                }
                else{
                    ldataCategorias.postValue(categorias);
                }
            }
        }.execute();
    }
}
