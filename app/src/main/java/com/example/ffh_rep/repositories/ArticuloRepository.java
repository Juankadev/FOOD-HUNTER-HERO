package com.example.ffh_rep.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.ffh_rep.repositories.ArticuloRepository;


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

    public void insertArticulo(Context context, Articulo articulo) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Connection con = null;
                PreparedStatement ps = null;

                try {

                    con = DBUtil.getConnection();
                    String query = "INSERT INTO Articulos (descripcion, precio, id_categoria, id_marca, id_articulo, imagen, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    ps = con.prepareStatement(query);
                    ps.setString(1, articulo.getDescripcion());
                    ps.setDouble(2, articulo.getPrecio());
                    ps.setInt(3, articulo.getCategoria().getIdCategoria());
                    ps.setInt(4, articulo.getMarca().getIdMarca());
                    ps.setInt(5, articulo.getIdArticulo());
                    ps.setString(6, articulo.getImagen());
                    ps.setString(7, articulo.getEstado());

                    int rowsAffected = ps.executeUpdate();

                    return rowsAffected > 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        DBUtil.closeConnection(con);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Artículo agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al insertar el artículooooo", Toast.LENGTH_SHORT).show();
                }
            }

        }.execute();
    }

    public void updateArticulo(Context context, Articulo articulo) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                Connection con = null;
                PreparedStatement ps = null;

                try {
                    con = DBUtil.getConnection();
                    String query = "UPDATE Articulos SET descripcion = ?, precio = ?, id_categoria = ?, id_marca = ?, imagen = ?, estado = ? WHERE id_articulo = ?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, articulo.getDescripcion());
                    ps.setDouble(2, articulo.getPrecio());
                    ps.setInt(3, articulo.getCategoria().getIdCategoria());
                    ps.setInt(4, articulo.getMarca().getIdMarca());
                    ps.setString(5, articulo.getImagen());
                    ps.setString(6, articulo.getEstado());
                    ps.setInt(7, articulo.getIdArticulo());

                    int rowsAffected = ps.executeUpdate();

                    return rowsAffected > 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        DBUtil.closeConnection(con);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Artículo actualizado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al actualizar el artículo", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void buscarArticuloPorID(Context context, int idArticulo) {
        new AsyncTask<Void, Void, Articulo>() {
            @Override
            protected Articulo doInBackground(Void... voids) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    con = DBUtil.getConnection();
                    String query = "SELECT * FROM Articulos WHERE id_articulo = ?";
                    ps = con.prepareStatement(query);
                    ps.setInt(1, idArticulo);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        Articulo articulo = new Articulo();
                        articulo.setIdArticulo(rs.getInt("id_articulo"));
                        articulo.setDescripcion(rs.getString("descripcion"));
                        articulo.setPrecio(rs.getDouble("precio"));
                        articulo.setCategoria(new Categoria());
                        articulo.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                        articulo.setMarca(new Marca());
                        articulo.getMarca().setIdMarca(rs.getInt("id_marca"));
                        articulo.setImagen(rs.getString("imagen"));
                        articulo.setEstado(rs.getString("estado"));

                        return articulo;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                        DBUtil.closeConnection(con);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Articulo articulo) {
                super.onPostExecute(articulo);
            }
        }.execute();

    }

    public void buscarArticuloPorDescripcion(MutableLiveData<List<Articulo>> ldata, String descripcion) {
        new AsyncTask<Void, Void, List<Articulo>>() {
            @Override
            protected List<Articulo> doInBackground(Void... voids) {
                List<Articulo> articulos = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    con = DBUtil.getConnection();
                    String query = "SELECT * FROM Articulos WHERE descripcion LIKE ?";
                    ps = con.prepareStatement(query);
                    ps.setString(1, "%" + descripcion + "%");

                    rs = ps.executeQuery();

                    while (rs.next()) {
                        Articulo articulo = new Articulo();
                        articulo.setIdArticulo(rs.getInt("id_articulo"));
                        articulo.setDescripcion(rs.getString("descripcion"));
                        articulo.setPrecio(rs.getDouble("precio"));
                        articulo.setCategoria(new Categoria());
                        articulo.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                        articulo.setMarca(new Marca());
                        articulo.getMarca().setIdMarca(rs.getInt("id_marca"));
                        articulo.setImagen(rs.getString("imagen"));
                        articulo.setEstado(rs.getString("estado"));

                        articulos.add(articulo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                        DBUtil.closeConnection(con);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return articulos;
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





}
