package com.example.ffh_rep.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.utils.DBUtil;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DescuentoRepository {

    /**
     * Obtiene la lista de beneficios activos desde la base de datos.
     *
     * @param mlDataBeneficios MutableLiveData que se actualizará con la lista de beneficios obtenida.
     * @return MutableLiveData actualizado con la lista de beneficios activos o una lista vacía en caso de error o ausencia de datos.
     */
    public MutableLiveData<List<Beneficio>> listarDescuentos(MutableLiveData<List<Beneficio>> mlDataBeneficios) {
        CompletableFuture.supplyAsync(() -> {
            List<Beneficio> lBeneficios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Beneficios b WHERE b.estado = 1");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Beneficio aData = new Beneficio();
                    int id = rs.getInt("id_beneficio");
                    String descripcion = rs.getString("descripcion");
                    int puntos_requeridos = rs.getInt("puntos_requeridos");
                    aData.setId_beneficio(id);
                    aData.setDescripcion(descripcion);
                    aData.setPuntos_requeridos(puntos_requeridos);
                    lBeneficios.add(aData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lBeneficios;
        }).thenAcceptAsync(articulos -> mlDataBeneficios.postValue(articulos));
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
                 PreparedStatement ps = con.prepareStatement("INSERT INTO Beneficios (id_comercio, descripcion, puntos_requeridos) VALUES (?, ?, ?)")) {

                ps.setInt(1, beneficio.getId_comercio().getId());
                ps.setString(2, beneficio.getDescripcion());
                ps.setInt(3, beneficio.getPuntos_requeridos());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    Toast.makeText(context, "Beneficio agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al insertar el Beneficio", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, "Beneficio actualizado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al actualizar el beneficio", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, "Beneficio eliminado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminado el beneficio", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al eliminado el beneficio", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
