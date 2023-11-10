package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Rango;
import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.LoginUsuarioCallback;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsByRoleTask extends AsyncTask<Void, Void, Object> {

    private int IdRol;
    private int IdUser;
    private Usuario user;
    private Context ctx;

    private LoginUsuarioCallback luCallback;

    public UserDetailsByRoleTask(int id_role, Usuario user, int id_user,Context ctx, LoginUsuarioCallback luCallback){
        this.IdRol = id_role;
        this.IdUser = id_user;
        this.ctx = ctx;
        this.user = user;
        this.luCallback = luCallback;
    }
    @Override
    protected Object doInBackground(Void... voids) {
        Object obj = new Object();
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);
            String query = "";

            if(IdRol == 1){
                query = "Select c.id_comercio, c.cuit, c.razon_social, c.rubro, c.correo_electronico, c.telefono, c.direccion from Comercios c where c.aprobado like 'aprobado' and c.id_usuario = ?";
            }
            if(IdRol == 2){
                query = "Select h.id_hunter, h.nombre, h.apellido, h.dni, h.sexo, h.correo_electronico, h.telefono, h.fecha_nacimiento, h.id_rango, h.puntaje, r.descripcion as rangodesc from Hunters h inner join Rangos r on r.id_rango = h.id_rango where h.id_usuario = ?";
            }
            if(IdRol == 3){
                query = "Select id_usuario, id_rol, username, password, estado from Usuarios where id_usuario = ?";
            }

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, this.IdUser);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    if (IdRol == 1) {
                        obj = extractCommerceFromResult(rs);
                    }
                    if (IdRol == 2) {
                        obj = extractHunterFromResult(rs);
                    }
                    if (IdRol == 3) {
                        obj = extractAdminFromResult(rs);
                    }
                } else {
                    Log.d("Error en RS", "Hubo un error al obtener resultados");
                    return null;
                }
            }
            preparedStatement.close();
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return obj;
    }


    public Hunter extractHunterFromResult(ResultSet rs) throws SQLException {
        Hunter hunter = new Hunter();
        hunter.setUser(this.user);
        hunter.setId_rango(new Rango());
        hunter.getId_rango().setIdRango(rs.getInt("id_rango"));
        hunter.setIdHunter(rs.getInt("id_hunter"));
        hunter.getId_rango().setDescripcion(rs.getString("rangodesc"));
        hunter.setNombre(rs.getString("nombre"));
        hunter.setApellido(rs.getString("apellido"));
        hunter.setDni(rs.getString("dni"));
        hunter.setSexo(rs.getString("sexo"));
        hunter.setPuntaje(rs.getInt("puntaje"));
        hunter.setCorreo_electronico(rs.getString("correo_electronico"));
        hunter.setTelefono(rs.getString("telefono"));
        hunter.setDireccion(rs.getString("telefono"));
        hunter.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));

        return hunter;
    }

    public Comercio extractCommerceFromResult(ResultSet rs) throws SQLException {
        Comercio c = new Comercio();
        c.setUser(this.user);
        c.setId(rs.getInt("id_comercio"));
        c.setCuit(rs.getString("cuit"));
        c.setRazonSocial(rs.getString("razon_social"));
        c.setRubro(rs.getString("rubro"));
        c.setTelefono(rs.getString("" +
                "telefono"));
        c.setDireccion(rs.getString("direccion"));
        c.setEmail(rs.getString("correo_electronico"));

        return c;
    }
    public Usuario extractAdminFromResult(ResultSet rs) throws SQLException {
        //c.setUser(this.user);
        Rol rol = new Rol(3,"administrador");
        Usuario usuario = new Usuario(rol,"administrador","administrador",true);
        return usuario;
    }

    @Override
    protected void onPostExecute(Object o) {
            if(this.IdRol == 1){
                Comercio commerce = (Comercio)o;
                luCallback.onSuccessLoginCommerce(commerce);
            }

            if(this.IdRol == 2){
                Hunter hunter = (Hunter)o;
                luCallback.onSuccessLoginHunter(hunter);
            }

            if(this.IdRol == 3){
                Usuario usuario = (Usuario)o;
                luCallback.onSuccessLoginAdmin(usuario);
            }

    }
}
