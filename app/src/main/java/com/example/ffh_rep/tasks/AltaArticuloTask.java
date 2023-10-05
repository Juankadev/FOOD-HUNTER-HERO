package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.interfaces.AltaArticuloCallback;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AltaArticuloTask extends AsyncTask<Void, Void, Boolean> {
    private Context ctx;
    private Articulo articulo;
    private AltaArticuloCallback rac;

    public AltaArticuloTask(Context ctx, Articulo articulo, AltaArticuloCallback rac) {
        this.ctx = ctx;
        this.articulo = articulo;
        this.rac = rac;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "INSERT INTO Articulos (id_articulo, descripcion, precio, imagen, id_categoria, id_marca, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, this.articulo.getIdArticulo());
            preparedStatement.setString(2, this.articulo.getDescripcion());
            preparedStatement.setDouble(3, this.articulo.getPrecio());
            preparedStatement.setString(4, this.articulo.getImagen());
            preparedStatement.setInt(5, this.articulo.getCategoria().getIdCategoria());
            preparedStatement.setInt(6, this.articulo.getMarca().getIdMarca()); 
            preparedStatement.setString(7, this.articulo.getEstado());

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
            Toast.makeText(ctx, "Alta de artículo exitosa", Toast.LENGTH_SHORT).show();
            rac.onArticuloRegistrado("Alta de artículo exitosa");
        } else {
            Toast.makeText(ctx, "Error al dar de alta el artículo", Toast.LENGTH_SHORT).show();
        }
    }
}
