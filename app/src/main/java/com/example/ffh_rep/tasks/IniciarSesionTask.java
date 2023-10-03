package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.utils.DB_Env;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

    public class IniciarSesionTask extends AsyncTask<Void, Void, Boolean> {

    //private Articulo articulo;
    private Context ctx;
    private String username;
    private String password;
    public IniciarSesionTask(Context ctx, String username, String password) {
        this.ctx = ctx;
        this.username = username;
        this.password = password;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);
            //System.out.println("Se conecta a la DB");
            String query = "SELECT COUNT(*) AS count FROM Usuarios WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.password);

            ResultSet rs = preparedStatement.executeQuery();


            int rowCount=0;
            if (rs.next()) {
                rowCount = rs.getInt("count");
                //System.out.println("Cantidad de registros devueltos: " + rowCount);
            }
            con.close();
            return rowCount>0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response){
            Toast.makeText(ctx, "Sesion iniciada", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ctx, "Usuario o contraseña invalida", Toast.LENGTH_SHORT).show();
        }
    }

}
