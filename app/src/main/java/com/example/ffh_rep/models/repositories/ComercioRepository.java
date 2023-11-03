package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComercioRepository {

    public void updateUserInfo(MutableLiveData<Comercio> mlCommerce, Comercio comercio, MutableLiveData<Boolean> updatingInfo, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
            updatingInfo.postValue(true);
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
                    updatingInfo.postValue(false);
                    success.postValue(true);
                    mlCommerce.postValue(comercio);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                error.postValue(false);
                updatingInfo.postValue(false);
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


    public MutableLiveData<List<Comercio>> cargarComerciosNoAprobados(MutableLiveData<List<Comercio>> mlDataComercio) {
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

    public void cargarResenias(MutableLiveData<List<Resenia>> listaResenias, Comercio commerce){
        CompletableFuture.supplyAsync(() -> {
            List<Resenia> lResenias = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Resenas r WHERE r.id_comercio = ?")) {
                ps.setInt(1, commerce.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id_resena");
                        int id_comercio = rs.getInt("id_comercio");
                        int id_usuario = rs.getInt("id_usuario");
                        int calificacion = rs.getInt("calificacion");
                        String comentario = rs.getString("comentario");
                        Resenia res = new Resenia(id, calificacion, comentario);
                        lResenias.add(res);
                    }
                }
            } catch (Exception e) {
                // Manejar la excepción
                e.printStackTrace();
            }
            return lResenias;
        }).thenAcceptAsync(resenias -> listaResenias.postValue(resenias));
    }

    public void generarResenia(Resenia res, MutableLiveData<Boolean> isLoading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
            isLoading.postValue(true);
            try {
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement("Insert Into Resenas (id_comercio, id_usuario, calificacion, comentario) values (?, ?, ?, ?)");
                ps.setInt(1, res.getComercio().getId());
                ps.setInt(2, res.getUsuario().getId_usuario());
                ps.setInt(3, res.getCalificacion());
                ps.setString(4, res.getComentario());


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

    public void cargarBeneficios(MutableLiveData<List<Beneficio>> listBeneficios, Comercio commerce){
        CompletableFuture.supplyAsync(() -> {
            List<Beneficio> lBeneficios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Beneficios b WHERE b.id_comercio = ?")) {
                ps.setInt(1, commerce.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Beneficio bene = new Beneficio();
                        bene.setId_beneficio(rs.getInt("id_beneficio"));
                        bene.setId_comercio(commerce);
                        bene.setDescripcion(rs.getString("descripcion"));
                        bene.setPuntos_requeridos(rs.getInt("puntos_requeridos"));
                        lBeneficios.add(bene);
                    }
                }
            } catch (Exception e) {
                // Manejar la excepción
                e.printStackTrace();
            }
            return lBeneficios;
        }).thenAcceptAsync(resenias -> listBeneficios.postValue(resenias));
    }
}