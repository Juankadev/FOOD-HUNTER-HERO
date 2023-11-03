package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistrarReseñaTask extends AsyncTask<Void, Void, Boolean> {
    private Context ctx;
    private Resenia res;

    public RegistrarReseñaTask(Context contexto, Resenia resenia){
        this.ctx = contexto;
        this.res = resenia;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "INSERT INTO Resenias (id_comercio, id_usuario, calificacion) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, res.getComercio().getId());
            preparedStatement.setInt(2, res.getUsuario().getId_usuario());
            preparedStatement.setInt(3, res.getCalificacion());

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error al registrar reseña
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response){
            Toast.makeText(ctx, "Reseña registrada exitosamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ctx, "Error, reseña invalida", Toast.LENGTH_SHORT).show();
        }
    }

}
