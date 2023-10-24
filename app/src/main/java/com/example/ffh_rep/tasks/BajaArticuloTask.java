package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BajaArticuloTask extends AsyncTask<Void, Void, Boolean> {

    private Context ctx;
    private Articulo articulo;
    public BajaArticuloTask(Context ctx, Articulo articulo) {
        this.ctx = ctx;
        this.articulo = articulo;
¿    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "UPDATE Articulos set estado = 0 where id_articulo = ?";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, this.articulo.getIdArticulo());

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
            Toast.makeText(ctx, "Baja de artículo exitosa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "Error al dar de baja el artículo", Toast.LENGTH_SHORT).show();
        }
    }
}
