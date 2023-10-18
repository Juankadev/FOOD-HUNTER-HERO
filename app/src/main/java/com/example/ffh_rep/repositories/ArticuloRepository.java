package com.example.ffh_rep.repositories;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArticuloRepository {

    public void getArticulos_by_idComercio(MutableLiveData<List<Articulo>> ldata, int id){
        new AsyncTask<Void, Void, List<Articulo>>(){
            @Override
            protected List<Articulo> doInBackground(Void... voids) {
                List<Articulo> lArticulos = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Articulos a inner join stock s on s.id_articulo = a.id_articulo inner join comercios c on c.id_comercio = s.id_comercio on a.where a.estado = 1 and c.id_comercio = ?" ;
                    ps = con.prepareStatement(query);
                    ps.setInt(1, id);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Articulo aData = new Articulo();
                        int id = rs.getInt("id_articulo");
                        String descripcion = rs.getString("descripcion");
                        Double precio = rs.getDouble("precio");
                        int id_categoria = rs.getInt("id_categoria");
                        aData.setIdArticulo(id);
                        aData.setDescripcion(descripcion);
                        aData.setPrecio(precio);
                        aData.setCategoria(new Categoria());
                        aData.getCategoria().setIdCategoria(id_categoria);
                        lArticulos.add(aData);
                    }
                    rs.close();
                    ps.close();
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Comercios task", lArticulos.toString());
                return lArticulos;
            }

            @Override
            protected void onPostExecute(List<Articulo> articulos) {
                super.onPostExecute(articulos);
                if(articulos.isEmpty()){
                    ldata.postValue(new ArrayList<>());
                }
                else{
                    ldata.postValue(articulos);
                }
            }
        }.execute();
    }
    public void getArticulos(MutableLiveData<List<Articulo>> ldataArticulos){
        new AsyncTask<Void, Void, List<Articulo>>(){
            @Override
            protected List<Articulo> doInBackground(Void... voids) {
                List<Articulo> lArticulos = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Articulos a where a.estado = 1";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Articulo aData = new Articulo();
                        int id = rs.getInt("id_articulo");
                        String descripcion = rs.getString("descripcion");
                        Double precio = rs.getDouble("precio");
                        int id_categoria = rs.getInt("id_categoria");
                        aData.setIdArticulo(id);
                        aData.setDescripcion(descripcion);
                        aData.setPrecio(precio);
                        aData.setCategoria(new Categoria());
                        aData.getCategoria().setIdCategoria(id_categoria);
                        lArticulos.add(aData);
                    }
                    rs.close();
                    ps.close();
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Comercios task", lArticulos.toString());
                return lArticulos;
            }

            @Override
            protected void onPostExecute(List<Articulo> articulos) {
                super.onPostExecute(articulos);
                if(articulos.isEmpty()){
                    ldataArticulos.postValue(new ArrayList<>());
                }
                else{
                    ldataArticulos.postValue(articulos);
                }
            }
        }.execute();
    }
}
