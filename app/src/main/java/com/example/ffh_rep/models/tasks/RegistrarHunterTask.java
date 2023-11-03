package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.utils.DB_Env;
import com.example.ffh_rep.utils.GeneralHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class RegistrarHunterTask extends AsyncTask<Void, Void, Boolean> {
private Context ctx;
private Hunter hunter;
private RegistrarUsuarioCallback ruCallback;
public RegistrarHunterTask(Context ctx, Hunter hunter, RegistrarUsuarioCallback ruc) {
    this.ctx = ctx;
    this.hunter = hunter;
    this.ruCallback = ruc;
}
@Override
protected Boolean doInBackground(Void... voids) {
    try {
        Class.forName(DB_Env.DB_DRIVER);
        Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

        String query = "INSERT INTO Hunters (id_usuario, nombre, apellido, dni, sexo, correo_electronico, telefono, direccion, fecha_nacimiento, id_rango, puntaje) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, this.hunter.getUser().getId_usuario());
        preparedStatement.setString(2, this.hunter.getNombre());
        preparedStatement.setString(3, this.hunter.getApellido());
        preparedStatement.setString(4, this.hunter.getDni());
        preparedStatement.setString(5, this.hunter.getSexo());
        preparedStatement.setString(6, this.hunter.getCorreo_electronico());
        preparedStatement.setString(7, this.hunter.getTelefono());
        preparedStatement.setString(8, this.hunter.getDireccion());
        preparedStatement.setDate(9, this.hunter.getFecha_nacimiento());
        preparedStatement.setInt(10, 1);
        preparedStatement.setInt(11, 0);


        int rowsAffected = preparedStatement.executeUpdate();
        con.close();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

@Override
protected void onPostExecute(Boolean response) {
    if(response){
        ruCallback.onCompleteInsert("Hola", "Probando");
    }
    else{
        Toast.makeText(ctx, "Error al registrarse", Toast.LENGTH_SHORT).show();
    }
}

}