package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.DB_Env;
import com.example.ffh_rep.utils.EmailSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class RegistrarHunterTask extends AsyncTask<Void, Void, Boolean> {
private Context ctx;
private Hunter hunter;
public RegistrarHunterTask(Context ctx, Hunter hunter) {
    this.ctx = ctx;
    this.hunter = hunter;
}
@Override
protected Boolean doInBackground(Void... voids) {
    try {
        Class.forName(DB_Env.DB_DRIVER);
        Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

        String query = "INSERT INTO Hunter (id_usuario, nombre, apellido, dni, sexo, correo_electronico, telefono, direccion, fecha_nacimiento, id_rango, puntaje) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, this.hunter.getNombre());
        preparedStatement.setString(3, this.hunter.getApellido());
        preparedStatement.setString(4, this.hunter.getDni());
        preparedStatement.setString(5, this.hunter.getSexo());
        preparedStatement.setString(6, this.hunter.getCorreo_electronico());
        preparedStatement.setString(7, this.hunter.getTelefono());
        preparedStatement.setString(8, this.hunter.getDireccion());
        preparedStatement.setDate(9,this.hunter.getFecha_nacimiento());
        preparedStatement.setInt(10, 1);
        preparedStatement.setInt(11, 0);

        con.close();

        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

@Override
protected void onPostExecute(Boolean response) {
    if(response){
        Toast.makeText(ctx, "Registro exitoso", Toast.LENGTH_SHORT).show();

    }
    else{
        Toast.makeText(ctx, "Error al registrarse", Toast.LENGTH_SHORT).show();
    }
}

}
