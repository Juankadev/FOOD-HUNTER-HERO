package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ModificarHunterTask extends AsyncTask<Void, Void, Boolean> {

    private Hunter hun;


    public ModificarHunterTask(Hunter hunter){
        this.hun = hunter;
    }



    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "UPDATE Hunters set nombre = ?, apellido = ?, sexo = ?, telefono = ?, direccion = ?, fecha_nacimiento = ? where idHunter = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            preparedStatement.setString(1, hun.getNombre());
            preparedStatement.setString(2, hun.getApellido());
            preparedStatement.setString(3, hun.getSexo());
            preparedStatement.setString(4, hun.getTelefono());
            preparedStatement.setString(5, hun.getDireccion());
            preparedStatement.setDate(6, hun.getFecha_nacimiento());
            preparedStatement.setInt(7, hun.getIdHunter());

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error al modificar el hunter
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {

    }
}
