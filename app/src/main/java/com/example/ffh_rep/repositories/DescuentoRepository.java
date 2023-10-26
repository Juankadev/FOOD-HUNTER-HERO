package com.example.ffh_rep.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.utils.DBUtil;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DescuentoRepository {

    public void modificarDescuento(Context context, Beneficio beneficio) {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "UPDATE Beneficios set descripcion = ?, puntos_requeridos = ? where id_beneficio = ? AND where id_comercio = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(query);

                    preparedStatement.setString(1, beneficio.getDescripcion());
                    preparedStatement.setInt(2, beneficio.getPuntos_requeridos());

                    int rowsAffected = preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();

                    return rowsAffected > 0;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Descuento modificado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al modificar descuento", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void agregarDescuento(Context context, Beneficio beneficio) {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "INSERT INTO Beneficios (id_comercio, descripcion, puntos_requeridos) " +
                            "VALUES (?, ?, ?)";

                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setInt(1, beneficio.getId_comercio().getId());
                    preparedStatement.setString(2, beneficio.getDescripcion());
                    preparedStatement.setInt(3, beneficio.getPuntos_requeridos());

                    int rowsAffected = preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();

                    return rowsAffected > 0;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Descuento  agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al agregar descuento", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void eliminarDescuento(Context context, Beneficio beneficio) {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "UPDATE Beneficios set estado = 0 where id_beneficio = ? AND where id_comercio = ?";

                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setInt(1, beneficio.getId_beneficio());
                    preparedStatement.setInt(2, beneficio.getId_comercio().getId());


                    int rowsAffected = preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();

                    return rowsAffected > 0;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Descuento eliminado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminar descuento", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
