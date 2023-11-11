package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.LoginHunterCallback;
import com.example.ffh_rep.interfaces.LoginUsuarioCallback;
import com.example.ffh_rep.utils.DB_Env;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IniciarSesionTask extends AsyncTask<Void, Void, Usuario> {

    private Context ctx;
    private String username;
    private String password;
    private LoginUsuarioCallback luCallback;

    private LoginHunterCallback hunterCallback;

    public IniciarSesionTask(Context ctx, String username, String password, LoginUsuarioCallback luCallback) {
        this.ctx = ctx;
        this.username = username;
        this.password = password;
        this.luCallback = luCallback;
    }

    public IniciarSesionTask(Context ctx, String username, String password, LoginHunterCallback hunterCallback) {
        this.ctx = ctx;
        this.username = username;
        this.password = password;
        this.hunterCallback = hunterCallback;
    }

    @Override
    protected Usuario doInBackground(Void... voids) {
        try (Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD)) {
            String query = "SELECT u.id_usuario, u.username, u.password, u.estado, r.id_rol, r.descripcion " +
                    "FROM Usuarios u INNER JOIN Roles r ON r.id_rol = u.id_rol " +
                    "WHERE u.username = ? AND u.password = ? and estado = 1";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.password);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        return extractUsuarioFromResultSet(rs);
                    } else {
                        Log.d("Error en RS", "Hubo un error al obtener resultados");
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Usuario extractUsuarioFromResultSet(ResultSet rs) throws SQLException {
        Usuario uData = new Usuario();
        uData.setRol(new Rol());

        uData.setId_usuario(rs.getInt("id_usuario"));
        uData.setUsername(rs.getString("username"));
        uData.setPassword(rs.getString("password"));
        uData.setEstado(rs.getBoolean("estado"));
        uData.getRol().setIdRol(rs.getInt("id_rol"));
        uData.getRol().setDescripcion(rs.getString("descripcion"));

        return uData;
    }

    @Override
    protected void onPostExecute(Usuario user) {
        if (user != null) {
            if(luCallback != null){
                luCallback.doGetUserDescriptionByRole(user);
            }
        } else {
            luCallback.onErrorLogin();
            Toast.makeText(ctx, "Usuario o contraseña inválida", Toast.LENGTH_SHORT).show();
        }
    }
}

