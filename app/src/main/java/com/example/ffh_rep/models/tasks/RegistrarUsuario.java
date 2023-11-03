package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.utils.DBUtil;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrarUsuario extends AsyncTask<Void, Void, Integer> {

    private Context ctx;
    private Usuario usuario;
    private RegistrarUsuarioCallback ruCallback;

    public RegistrarUsuario(Context context, Usuario usuario, RegistrarUsuarioCallback ruc){
        this.ctx = context;
        this.usuario = usuario;
        this.ruCallback = ruc;
    }
    public RegistrarUsuario(Context context, Usuario usuario){
        this.ctx = context;
        this.usuario = usuario;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);
            String query = "Insert into Usuarios (id_rol, username, password, estado) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, this.usuario.getRol().getIdRol());
            ps.setString(2, this.usuario.getUsername());
            ps.setString(3, this.usuario.getPassword());
            ps.setBoolean(4, true);

            int rowsAffected = ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int uID = -1;

            if(rs.next()){
                uID = rs.getInt(1);
            }

            ps.close();
            con.close();

            return uID;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer userId) {
        if(userId != -1){
            Log.d("ID", String.valueOf(userId));
            ruCallback.onUsuarioInsertado(userId);
        }
        else{
            ruCallback.onUsuarioError();
        }
    }
}
