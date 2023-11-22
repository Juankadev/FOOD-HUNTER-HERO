package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComercioRepository {

    /**
     * Esta función realiza una operación asíncrona para actualizar la información del comercio en una base de datos,
     * utilizando MutableLiveData para informar sobre el progreso y el resultado de la operación.
     */
    public void updateUserInfo(MutableLiveData<Comercio> mlCommerce, Comercio comercio, MutableLiveData<Boolean> updatingInfo, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
            updatingInfo.postValue(true);
            try {
                Connection con = DBUtil.getConnection();
                String query = "Update Comercios set rubro = ?, correo_electronico = ?, telefono = ?, direccion = ? where id_comercio = ?";
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

    /**
     * Esta función realiza una operación asíncrona para "eliminar" la cuenta de un comercio actualizando el estado del usuario en la
     * base de datos. Utiliza MutableLiveData para informar sobre el resultado de la operación y muestra mensajes Toast en caso de éxito o
     * fallo.
     */
    public void eliminarCuenta(Comercio comercio, MutableLiveData<Boolean> isDelete, Context ctx){
        CompletableFuture.runAsync(() -> {
            try {
                Connection con = DBUtil.getConnection();
                String query = "Update Usuarios set estado = 0 where id_usuario = ?";
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
    public MutableLiveData<List<Comercio>> getComercios(MutableLiveData<List<Comercio>> original, MutableLiveData<List<Comercio>> mlDataComercio, int id_user, MutableLiveData<Boolean> success) {
        CompletableFuture.supplyAsync(() -> {
            List<Comercio> lComercios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT C.*," +
                         "       (SELECT MAX(CASE" +
                         "                      WHEN CF.id_comercio_favorito IS NOT NULL THEN 1" +
                         "                      ELSE 0" +
                         "                    END)" +
                         "        FROM Comercios_Favoritos CF" +
                         "        WHERE CF.id_comercio = C.id_comercio" +
                         "          AND CF.id_usuario =" + id_user+
                         "        ) AS es_favorito" +
                         " FROM Comercios C WHERE C.aprobado like 'Aprobado'");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_comercio");
                    String razonsocial = rs.getString("razon_social");
                    String cuit = rs.getString("cuit");
                    String rubro = rs.getString("rubro");
                    String email = rs.getString("correo_electronico");
                    String telefono = rs.getString("telefono");
                    boolean isFavorite = rs.getBoolean("es_favorito");
                    String direccion = rs.getString("direccion");
                    String aprobado = rs.getString("aprobado");
                    Comercio cData = new Comercio(id, razonsocial, cuit, rubro, email, telefono, direccion, aprobado);
                    cData.setFavorite(isFavorite);
                    lComercios.add(cData);
                }
            } catch (Exception e) {
                // Manejar la excepción adecuadamente, por ejemplo, registrándola o lanzándola de nuevo
                e.printStackTrace();
            }
            return lComercios;
        }).thenAcceptAsync(comercios -> {
            mlDataComercio.postValue(comercios);
            original.postValue(comercios);
        });
        success.postValue(true);
        return mlDataComercio;
    }

    /**
     * Obtiene y retorna la lista de comercios cuya razón social contiene la cadena proporcionada de manera asíncrona.
     * Utiliza un CompletableFuture para ejecutar la consulta en un hilo separado y actualiza el MutableLiveData con los resultados.
     *
     * @param mlDataComercio MutableLiveData que se actualizará con la lista de comercios obtenida.
     * @param strComercio Cadena que se utilizará para buscar comercios por razón social.
     */
    public void getComercioByName(MutableLiveData<List<Comercio>> original, MutableLiveData<List<Comercio>> mlDataComercio, String strComercio) {
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
        }).thenAcceptAsync(comercios -> {
            original.postValue(comercios);
            mlDataComercio.postValue(comercios);
        }
        );
    }


    public MutableLiveData<List<Comercio>> cargarComerciosNoAprobados(MutableLiveData<List<Comercio>> mlDataComercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Comercio> lComercios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Comercios c WHERE c.aprobado LIKE 'Pendiente'");
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

    public void rechazarComercio(Comercio comercio, Context ctx){
        CompletableFuture.runAsync(() -> {
            try {
                Connection con = DBUtil.getConnection();
                String query = "Update Comercios set aprobado = 'Rechazado' where id_comercio = ?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, comercio.getId());

                int rowsAffected = ps.executeUpdate();
                ps.close();
                DBUtil.closeConnection(con);
                if(rowsAffected > 0){

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ctx, "Rechazo exitoso", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ctx, "Ocurrio un error al rechazar el comercio", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(ctx, "Ocurrio un error al rechazar el comercio", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void aprobarComercio(Comercio comercio, Context ctx){
        CompletableFuture.runAsync(() -> {
            Connection con = DBUtil.getConnection();

            try {
                //GET ID_USUARIO
                PreparedStatement ps_idusuario = con.prepareStatement("Select id_usuario from Comercios where id_comercio = ?");
                ps_idusuario.setInt(1, comercio.getId());
                ResultSet rs = ps_idusuario.executeQuery();

                Integer id_usuario=-1;
                while (rs.next()) {
                    id_usuario = rs.getInt("id_usuario");
                }


                // Iniciar una transacción
                con.setAutoCommit(false);
                // Realizar operaciones de base de datos dentro de la transacción

                //UPDATE USUARIO
                String query_usuario = "Update Usuarios set estado = 1 where id_usuario = ?";
                PreparedStatement ps_usuario = con.prepareStatement(query_usuario);
                ps_usuario.setInt(1, id_usuario);
                int rowsAffectedUsuario = ps_usuario.executeUpdate();


                //UPDATE COMERCIO
                String query_comercio = "Update Comercios set aprobado = 'Aprobado' where id_comercio = ?";
                PreparedStatement ps_comercio = con.prepareStatement(query_comercio);
                ps_comercio.setInt(1, comercio.getId());
                int rowsAffectedComercio = ps_comercio.executeUpdate();


                // Confirmar la transacción
                con.commit();
                ps_usuario.close();
                ps_comercio.close();


                if(rowsAffectedUsuario > 0 && rowsAffectedComercio > 0){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ctx, "Aprobación exitosa", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ctx, "Ocurrió un error al aprobar el comercio", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
            catch (SQLException e){
                try {
                    // Deshacer la transacción en caso de error
                    con.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
                e.printStackTrace();
                Toast.makeText(ctx, "Ocurrió un error al aprobar el comercio", Toast.LENGTH_LONG).show();
            }
            finally {
                if (con != null) {
                    try {
                        // Restaurar el modo de autocommit
                        con.setAutoCommit(true);
                        con.close();
                        DBUtil.closeConnection(con);
                    } catch (SQLException closeException) {
                        closeException.printStackTrace();
                    }
                }
            }

        });

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

    public void dismarkAsFavorite(Comercio comercio, Hunter hunter, MutableLiveData<Boolean> isLoading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
            isLoading.postValue(true);
            try {
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement("Delete from Comercios_Favoritos  where id_comercio = ? and id_usuario = ?");
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

    public void cargarBeneficios(MutableLiveData<List<Beneficio>> listBeneficios, Comercio commerce, MutableLiveData<Boolean> loading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.supplyAsync(() -> {
            loading.postValue(true);
            List<Beneficio> lBeneficios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Beneficios b WHERE b.id_comercio = ? and b.estado = 1")) {
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
                e.printStackTrace();
                loading.postValue(false);
                error.postValue(true);
            }
            return lBeneficios;
        }).thenAcceptAsync(resenias -> {
            loading.postValue(false);
            success.postValue(true);
            listBeneficios.postValue(resenias);
        });
    }
}

