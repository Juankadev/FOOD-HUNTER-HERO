package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.utils.DB_Env;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarComercioTask extends AsyncTask<Void, String, Boolean> {
    private Context ctx;
    private Comercio comercio;
    private RegistrarUsuarioCallback ruc;
    public RegistrarComercioTask(Context ctx, Comercio comercio, RegistrarUsuarioCallback ruc) {
        this.ctx = ctx;
        this.comercio = comercio;
        this.ruc = ruc;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "INSERT INTO Comercios (id_usuario, cuit, razon_social, rubro, correo_electronico, telefono, direccion, aprobado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, this.comercio.getUser().getId_usuario());
            preparedStatement.setString(2, this.comercio.getCuit());
            preparedStatement.setString(3, this.comercio.getRazonSocial());
            preparedStatement.setString(4, this.comercio.getRubro());
            preparedStatement.setString(5, this.comercio.getEmail());
            preparedStatement.setString(6, this.comercio.getTelefono());
            preparedStatement.setString(7, this.comercio.getDireccion());
            preparedStatement.setString(8, this.comercio.getAprobado());

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            query = "UPDATE Usuarios set estado = 0 where id_usuario = ?";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, this.comercio.getUser().getId_usuario());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            con.close();

            return rowsAffected > 0;

        }
         catch (SQLException e){
            e.printStackTrace();
            String sqlState = e.getSQLState();
             if("23000".equals(sqlState)) {
                 if (e.getMessage().contains("correo_electronico")) {
                     publishProgress("El Correo electrónico ya se encuentra en uso");
                     eliminarUsuarioActual();
                 }
             }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response){
            Toast.makeText(ctx, "Registro exitoso", Toast.LENGTH_SHORT).show();
            ruc.onCompleteInsert("Hola","Hola");
        }
        else{
            Toast.makeText(ctx, "Error al registrarse", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(String... messages) {
        super.onProgressUpdate(messages);
        Toast.makeText(ctx, messages[0], Toast.LENGTH_SHORT).show();
    }

    private void eliminarUsuarioActual() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Connection con = null;
                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "DELETE FROM Usuarios WHERE id_usuario = ?";
                    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                        preparedStatement.setInt(1, comercio.getUser().getId_usuario());
                        preparedStatement.executeUpdate();
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        }.execute();
    }
}
