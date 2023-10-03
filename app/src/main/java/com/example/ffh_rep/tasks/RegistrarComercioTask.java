package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.DB_Env;
import com.example.ffh_rep.utils.EmailSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistrarComercioTask extends AsyncTask<Void, Void, Boolean> {
    private Context ctx;
    private Comercio comercio;
    public RegistrarComercioTask(Context ctx, Comercio comercio) {
        this.ctx = ctx;
        this.comercio = comercio;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "INSERT INTO Comercio (id_usuario, cuit, razon_social, rubro, correo_electronico, telefono, direccion, aprobado) " +
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
            con.close();

            String recipient = this.comercio.getEmail();
            String subject = this.comercio.getRazonSocial() +", bienvenidx a Food Hunter Hero \uD83D\uDE0E" ;
            String messageText = EmailSender.plantillaRegistroExitoso(this.comercio.getRazonSocial(), this.comercio.getEmail(), this.comercio.getEmail());

            EmailSender.sendEmail(recipient, subject, messageText);

            return rowsAffected > 0;

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
