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
                    String query = "Select a.id_articulo, a.descripcion, a.precio, a.id_categoria as id_categoria, m.id_marca as idmarca, m.descripcion as marca, cat.descripcion as categoria from Articulos a "
                    + "inner join Stocks s on s.id_articulo = a.id_articulo " +
                            "inner join Comercios c on c.id_comercio = s.id_comercio " +
                            "inner join Marcas m on m.id_marca = a.id_marca " +
                            "inner join Categorias cat on cat.id_categoria = a.id_categoria " +
                            "where a.estado = 1 and c.id_comercio = ?" ;
                    ps = con.prepareStatement(query);
                    ps.setInt(1, id);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Articulo aData = new Articulo();
                        int id = rs.getInt("id_articulo");
                        String descripcion = rs.getString("descripcion");
                        Double precio = rs.getDouble("precio");

                        aData.setMarca(new Marca());
                        aData.getMarca().setIdMarca(rs.getInt("idmarca"));
                        aData.getMarca().setDescripcion(rs.getString("marca"));

                        aData.setCategoria(new Categoria());
                        aData.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                        aData.getCategoria().setDescripcion(rs.getString("categoria"));
                        aData.setIdArticulo(id);
                        aData.setDescripcion(descripcion);
                        aData.setPrecio(precio);
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
                if(articulos != null){
                    if(articulos.isEmpty()){
                        ldata.postValue(new ArrayList<>());
                    }
                    else{
                        ldata.postValue(articulos);
                    }
                }
                else{
                    ldata.postValue(new ArrayList<>());
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
