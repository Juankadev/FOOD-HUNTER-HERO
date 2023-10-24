package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ModificarComercioTask extends AsyncTask<Void, Void, Boolean> {
    private Context ctx;
    private Comercio com;

    public ModificarComercioTask(Context contexto, Comercio comer){
        this.ctx = contexto;
        this.com = comer;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "UPDATE Comercios set rubro = ?, correo_electronico = ?, telefono = ?, direccion = ? where Id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, com.getRubro());
            preparedStatement.setString(2, com.getEmail());
            preparedStatement.setString(3, com.getTelefono());
            preparedStatement.setString(4, com.getDireccion());
            preparedStatement.setInt(5, com.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error al modificar el comercio
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response){
            Toast.makeText(ctx, "Comercio Modificado exitosamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ctx, "Error, ID inexistente", Toast.LENGTH_SHORT).show();
        }
    }
}
