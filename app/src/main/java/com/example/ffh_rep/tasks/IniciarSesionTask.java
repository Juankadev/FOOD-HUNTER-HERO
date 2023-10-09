package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.LoginUsuarioCallback;
import com.example.ffh_rep.utils.DB_Env;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IniciarSesionTask extends AsyncTask<Void, Void, Usuario> {

    //private Articulo articulo;
    private Context ctx;
    private String username;
    private String password;
    private LoginUsuarioCallback luCallback;
    public IniciarSesionTask(Context ctx, String username, String password, LoginUsuarioCallback luCallback) {
        this.ctx = ctx;
        this.username = username;
        this.password = password;
        this.luCallback = luCallback;
    }
    @Override
    protected Usuario doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);
            //System.out.println("Se conecta a la DB");
            String query = "SELECT u.id_usuario, u.username, u.password, u.estado, r.id_rol, r.descripcion FROM Usuarios u inner join Roles r on r.id_rol = u.id_rol WHERE u.username = ? AND u.password = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.password);

            ResultSet rs = preparedStatement.executeQuery();
            Usuario uData = new Usuario();
            uData.setRol(new Rol());
            if(rs.next()){
                Integer idUser = rs.getInt("id_usuario");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String estado = rs.getString("estado");
                Integer idRol = rs.getInt("id_rol");
                String descripcion = rs.getString("descripcion");

                uData.setId_usuario(idUser);
                uData.setUsername(username);
                uData.setPassword(password);
                uData.setEstado(estado);
                uData.getRol().setIdRol(idRol);
                uData.getRol().setDescripcion(descripcion);
            }
            else{
                Log.d("Error en RS", "Hubo un error al obtener results");
                return null;
            }
            rs.close();
            preparedStatement.close();
            con.close();
            return uData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Usuario user) {
        if(user != null){
            Toast.makeText(ctx, "Haz ingresado con exito", Toast.LENGTH_SHORT).show();
            Log.d("En TASk Iniciar sesion", user.toString());
            luCallback.onSuccessLogin(user);
        }
        else{
            Toast.makeText(ctx, "Usuario o contraseña invalida", Toast.LENGTH_SHORT).show();
        }
    }

}
