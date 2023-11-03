package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ModificarArticuloTask extends AsyncTask<Void, Void, Boolean> {

    private Context ctx;
    private Articulo art;

    public ModificarArticuloTask(Context contexto, Articulo artic){
        this.ctx = contexto;
        this.art = artic;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "UPDATE Articulos set descripcion = ?, precio = ?, id_categoria = ?, id_marca = ? where id_articulo = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, art.getDescripcion());
            preparedStatement.setDouble(2, art.getPrecio());
            preparedStatement.setInt(3, art.getCategoria().getIdCategoria());
            preparedStatement.setInt(4, art.getMarca().getIdMarca());
            preparedStatement.setInt(5, art.getIdArticulo());

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error al agregar el artículo
        }
    }
    @Override
    protected void onPostExecute(Boolean response) {
        if(response){
            Toast.makeText(ctx, "Artículo Modificado exitosamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ctx, "Error, ID inexistente", Toast.LENGTH_SHORT).show();
        }
    }
}
