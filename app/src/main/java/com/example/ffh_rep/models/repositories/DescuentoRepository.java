package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.DBUtil;
import com.example.ffh_rep.utils.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DescuentoRepository {

    /**
     * Obtiene la lista de beneficios activos por comercio desde la base de datos.
     *
     * @param mlDataBeneficios MutableLiveData que se actualizará con la lista de beneficios obtenida.
     * @return MutableLiveData actualizado con la lista de beneficios activos o una lista vacía en caso de error o ausencia de datos.
     */
    public MutableLiveData<List<Beneficio>> listarDescuentosByComercio(MutableLiveData<List<Beneficio>> original,MutableLiveData<List<Beneficio>> mlDataBeneficios, int id) {
        CompletableFuture.supplyAsync(() -> {
            List<Beneficio> lBeneficios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Beneficios b WHERE b.estado = '1' and b.id_comercio ="+id);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Beneficio beneficio = new Beneficio();
                    beneficio.setId_beneficio(rs.getInt("id_beneficio"));
                    beneficio.setDescripcion(rs.getString("descripcion"));
                    beneficio.setPuntos_requeridos(rs.getInt("puntos_requeridos"));

                    lBeneficios.add(beneficio);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lBeneficios;
        }).thenAcceptAsync(beneficios -> {
            if (beneficios != null) {
                original.postValue(beneficios.isEmpty() ? new ArrayList<>() : beneficios);
                mlDataBeneficios.postValue(beneficios.isEmpty() ? new ArrayList<>() : beneficios);
            } else {
                mlDataBeneficios.postValue(new ArrayList<>());
                original.postValue(new ArrayList<>());
            }
        });
        return mlDataBeneficios;
    }

    /**
     * Inserta un nuevo beneficio en la base de datos.
     *
     * @param context   Contexto de la aplicación.
     * @param beneficio Beneficio a ser insertado en la base de datos.
     */
    public void agregarDescuento(Context context, Beneficio beneficio) {
        CompletableFuture.runAsync(() -> {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO Beneficios (id_comercio, descripcion, puntos_requeridos, estado) VALUES (?, ?, ?, ?)")) {

                ps.setInt(1, beneficio.getId_comercio().getId());
                ps.setString(2, beneficio.getDescripcion());
                ps.setInt(3, beneficio.getPuntos_requeridos());
                ps.setBoolean(4, beneficio.getEstado());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Beneficio agregado exitosamente", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Ocurrio un error al agregar el Beneficio", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al insertar el Beneficio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Actualiza la información de un beneficio en la base de datos.
     *
     * @param context   Contexto de la aplicación.
     * @param beneficio Objeto Beneficio con la información actualizada.
     */

    public void modificarDescuento(Context context, Beneficio beneficio) {
        CompletableFuture.runAsync(() -> {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("UPDATE Beneficios set descripcion = ?, puntos_requeridos = ? where id_beneficio = ?")) {

                ps.setString(1, beneficio.getDescripcion());
                ps.setInt(2, beneficio.getPuntos_requeridos());
                ps.setInt(3, beneficio.getId_beneficio());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Beneficio modificado exitosamente", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Ocurrio un error al modificar el Beneficio", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al actualizar el beneficio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Elimina de manera logica la información de un beneficio en la base de datos.
     *
     * @param context   Contexto de la aplicación.
     * @param beneficio Objeto Beneficio con la información actualizada.
     */

    public void eliminarDescuento(Context context, Beneficio beneficio) {
        CompletableFuture.runAsync(() -> {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("UPDATE Beneficios set estado = 0 where id_beneficio = ?")) {

                ps.setInt(1, beneficio.getId_beneficio());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Beneficio eliminado exitosamente", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Ocurrio un error al eliminar el Beneficio", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al eliminado el beneficio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insert_Beneficios_hunter(MutableLiveData<Boolean> loading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error, Hunter hunter, Beneficio bene, SessionManager manager) {
        CompletableFuture.runAsync(() -> {
            loading.postValue(true);
            try (Connection con = DBUtil.getConnection()) {
                con.setAutoCommit(false);
                try (PreparedStatement insertBeneficio = con.prepareStatement("INSERT INTO Beneficios_Hunters (id_beneficio, id_hunter, estado) VALUES (?, ?, 1)")) {
                    insertBeneficio.setInt(1, bene.getId_beneficio());
                    insertBeneficio.setInt(2, hunter.getIdHunter());

                    int rowsAffected = insertBeneficio.executeUpdate();

                    if (rowsAffected > 0) {
                        int puntosDescontar = hunter.getPuntaje() - bene.getPuntos_requeridos();
                        try (PreparedStatement updatePuntaje = con.prepareStatement("UPDATE Hunters SET puntaje = puntaje - ? WHERE id_hunter = ?")) {
                            updatePuntaje.setInt(1, puntosDescontar);
                            updatePuntaje.setInt(2, hunter.getIdHunter());
                            updatePuntaje.executeUpdate();
                        }

                        con.commit();
                        hunter.setPuntaje(puntosDescontar);
                        manager.saveHunterSession(hunter);
                        success.postValue(true);
                    } else {
                        con.rollback();
                        loading.postValue(false);
                        error.postValue(true);
                    }
                } catch (Exception e) {
                    con.rollback();
                    e.printStackTrace();
                    error.postValue(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                error.postValue(true);
            }
        });
    }

    public void listarDescuentos_byIdHunter(MutableLiveData<List<Beneficio>> noEditableList, MutableLiveData<List<Beneficio>> list, MutableLiveData<Boolean> loading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error, Hunter hunter){
        CompletableFuture.supplyAsync(() -> {
            List<Beneficio> lBeneficios = new ArrayList<>();
            loading.postValue(true);
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT B.*, C.*" +
                         " FROM Beneficios B" +
                         " INNER JOIN Beneficios_Hunters BH ON B.id_beneficio = BH.id_beneficio" +
                         " INNER JOIN Comercios C ON B.id_comercio = C.id_comercio" +
                         " WHERE BH.id_hunter = ?;");
                ) {
                ps.setInt(1, hunter.getIdHunter());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Beneficio beneficio = new Beneficio();
                    beneficio.setId_beneficio(rs.getInt("id_beneficio"));
                    beneficio.setDescripcion(rs.getString("descripcion"));
                    beneficio.setPuntos_requeridos(rs.getInt("puntos_requeridos"));
                    beneficio.setId_comercio(new Comercio());
                    beneficio.getId_comercio().setId(rs.getInt("id_comercio"));
                    beneficio.getId_comercio().setRazonSocial(rs.getString("razon_social"));
                    lBeneficios.add(beneficio);
                }
            } catch (Exception e) {
                e.printStackTrace();
                loading.postValue(false);
                error.postValue(true);
            }
            return lBeneficios;
        }).thenAcceptAsync(beneficios -> {
            if (beneficios != null) {
                noEditableList.postValue(beneficios.isEmpty() ? new ArrayList<>() : beneficios);
                loading.postValue(false);
                success.postValue(true);
                list.postValue(beneficios.isEmpty() ? new ArrayList<>() : beneficios);
            } else {
                noEditableList.postValue(new ArrayList<>());
                list.postValue(new ArrayList<>());
                loading.postValue(false);
                success.postValue(true);
            }
        });
    }
}
