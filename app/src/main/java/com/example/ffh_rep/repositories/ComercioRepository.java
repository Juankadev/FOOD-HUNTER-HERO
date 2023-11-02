package com.example.ffh_rep.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.ui.hunter.Hunter_VerComercio;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComercioRepository {

    public void updateUserInfo(MutableLiveData<Comercio> mlCommerce, Comercio comercio, Context ctx){
        CompletableFuture.runAsync(() -> {

            try {
                Connection con = DBUtil.getConnection();
                String query = "UPDATE Comercios set rubro = ?, correo_electronico = ?, telefono = ?, direccion = ? where Id = ?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, comercio.getRubro());
                ps.setString(2, comercio.getEmail());
                ps.setString(3, comercio.getTelefono());
                ps.setString(4, comercio.getDireccion());
                ps.setInt(5, comercio.getId());

                int rowsAffected = ps.executeUpdate();
                ps.close();
                DBUtil.closeConnection(con);
                if(rowsAffected > 0){
                    Toast.makeText(ctx, "Información actualizada con exito", Toast.LENGTH_LONG).show();
                    mlCommerce.postValue(comercio);
                }
                else{
                    Toast.makeText(ctx, "Ocurrio un error al actualizar la información", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(ctx, "Ocurrio un error al actualizar la información", Toast.LENGTH_LONG);
            }
        });
    }

    public void eliminarCuenta(Comercio comercio, MutableLiveData<Boolean> isDelete, Context ctx){
        CompletableFuture.runAsync(() -> {
            try {
                Connection con = DBUtil.getConnection();
                String query = "Update Usuarios set estado = '0' where id_usuario = ?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, comercio.getUser().getId_usuario());

                int rowsAffected = ps.executeUpdate();
                ps.close();
                DBUtil.closeConnection(con);
                if(rowsAffected > 0){
                    isDelete.postValue(true);
                }
                else{
                    Toast.makeText(ctx, "Ocurrio un error al eliminar tu cuenta", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(ctx, "Ocurrio un error al eliminar tu cuenta", Toast.LENGTH_LONG).show();
            }
        });

    }
    /**
     * Obtiene y retorna la lista de comercios aprobados desde la base de datos de manera asíncrona.
     * Utiliza un CompletableFuture para ejecutar la consulta en un hilo separado y actualiza el MutableLiveData con los resultados.
     *
     * @param mlDataComercio MutableLiveData que se actualizará con la lista de comercios obtenida.
     * @return MutableLiveData que contiene la lista de comercios, actualizado de forma asíncrona.
     */
    public MutableLiveData<List<Comercio>> getComercios(MutableLiveData<List<Comercio>> mlDataComercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Comercio> lComercios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Comercios c WHERE c.aprobado LIKE 'Aprobado'");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
            } catch (Exception e) {
                // Manejar la excepción adecuadamente, por ejemplo, registrándola o lanzándola de nuevo
                e.printStackTrace();
            }
            return lComercios;
        }).thenAcceptAsync(comercios -> mlDataComercio.postValue(comercios));
        return mlDataComercio;
    }
    /**
     * Obtiene y retorna la lista de comercios cuya razón social contiene la cadena proporcionada de manera asíncrona.
     * Utiliza un CompletableFuture para ejecutar la consulta en un hilo separado y actualiza el MutableLiveData con los resultados.
     *
     * @param mlDataComercio MutableLiveData que se actualizará con la lista de comercios obtenida.
     * @param strComercio Cadena que se utilizará para buscar comercios por razón social.
     */
    public void getComercioByName(MutableLiveData<List<Comercio>> mlDataComercio, String strComercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Comercio> lComercios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Comercios c WHERE c.razon_social LIKE ?")) {
                ps.setString(1, "%" + strComercio.trim() + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
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
                }
            } catch (Exception e) {
                // Manejar la excepción
                e.printStackTrace();
            }
            return lComercios;
        }).thenAcceptAsync(comercios -> mlDataComercio.postValue(comercios));
    }


    public MutableLiveData<List<Comercio>> getComerciosNoAprobados(MutableLiveData<List<Comercio>> mlDataComercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Comercio> lComercios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Comercios c WHERE c.aprobado LIKE 'Desaprobado'");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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
            } catch (Exception e) {
                // Manejar la excepción adecuadamente, por ejemplo, registrándola o lanzándola de nuevo
                e.printStackTrace();
            }
            return lComercios;
        }).thenAcceptAsync(comercios -> mlDataComercio.postValue(comercios));
        return mlDataComercio;
    }



    public void markAsFavorite(Comercio comercio, Hunter hunter, MutableLiveData<Boolean> isLoading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
            isLoading.postValue(true);
            try {
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement("Insert Into Comercios_Favoritos (id_comercio, id_usuario) values (?, ?)");
                ps.setInt(1, comercio.getId());
                ps.setInt(2, hunter.getUser().getId_usuario());

                int rowsAffected  = ps.executeUpdate();
                if( rowsAffected > 0){
                    success.postValue(true);
                }
                else{
                    error.postValue(false);
                }

                ps.close();
                DBUtil.closeConnection(con);
            }
            catch (Exception e){
                e.printStackTrace();
                error.postValue(true);
            }
            finally {
                isLoading.postValue(false);
            }
        });
    }
}
