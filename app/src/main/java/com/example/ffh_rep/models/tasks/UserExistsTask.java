package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase asincrónica para verificar si un nombre de usuario ya existe en la base de datos.
 */
public class UserExistsTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private String username;

    /**
     * Constructor de la tarea.
     *
     * @param context  Contexto de la aplicación.
     * @param username Nombre de usuario a verificar.
     */
    public UserExistsTask(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    /**
     * Método que se ejecuta en segundo plano para realizar la verificación de la existencia del usuario.
     *
     * @param voids Parámetros de entrada (no utilizados).
     * @return `true` si el usuario existe, `false` si no existe o hay un error.
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Cargar el controlador de la base de datos y establecer la conexión
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            // Consulta para contar el número de usuarios con el nombre de usuario dado
            String query = "SELECT COUNT(*) AS count FROM Usuarios WHERE username = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                // Ejecutar la consulta y obtener el resultado
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        return count > 0;
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false; // En caso de error
    }
}
