package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.interfaces.AltaArticuloCallback;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AñadirDescuentoTask extends AsyncTask<Void, Void, Boolean> {

    private Context ctx;
    private Beneficio ben;

    public AñadirDescuentoTask(Context ctx, Beneficio beneficio) {
        this.ctx = ctx;
        this.ben = beneficio;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "INSERT INTO Articulos (id_comercio, descripcion, puntos_requeridos) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, this.ben.getId_comercio().getId());
            preparedStatement.setString(2, this.ben.getDescripcion());
            preparedStatement.setInt(3, this.ben.getPuntos_requeridos());

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
    protected void onPostExecute(Boolean response) {
        if (response) {
            Toast.makeText(ctx, "Descuento agregado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "Error al agregarse el descuento", Toast.LENGTH_SHORT).show();
        }
    }
}
