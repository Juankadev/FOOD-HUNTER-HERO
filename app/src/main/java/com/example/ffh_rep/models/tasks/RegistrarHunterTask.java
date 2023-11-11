package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CpuUsageInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Rango;
import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.utils.DB_Env;
import com.example.ffh_rep.utils.GeneralHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class RegistrarHunterTask extends AsyncTask<Void, String, Hunter> {
private Context ctx;
private Hunter hunter;
private RegistrarUsuarioCallback ruCallback;
public RegistrarHunterTask(Context ctx, Hunter hunter, RegistrarUsuarioCallback ruc) {
    this.ctx = ctx;
    this.hunter = hunter;
    this.ruCallback = ruc;
}
    @Override
    protected Hunter doInBackground(Void... voids) {
        Hunter h = null;
        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "INSERT INTO Hunters (id_usuario, nombre, apellido, dni, sexo, correo_electronico, telefono, direccion, fecha_nacimiento, id_rango, puntaje) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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

            ResultSet rs = preparedStatement.getGeneratedKeys();
            int insertedHunterId = -1;

            if (rs.next()) {
                insertedHunterId = rs.getInt(1);
            }

            String joinQuery = "SELECT h.id_hunter, h.nombre, h.apellido, h.dni, h.sexo, h.correo_electronico, h.telefono, h.direccion, h.fecha_nacimiento, h.puntaje, " +
                    " u.id_usuario, u.username, u.password," +
                    " r.id_rango, r.descripcion as rango," +
                    " rol.id_rol, rol.descripcion as rol" +
                    " FROM Hunters h" +
                    " INNER JOIN Rangos r on r.id_rango = h.id_rango" +
                    " INNER JOIN Usuarios u on u.id_usuario = h.id_usuario" +
                    " INNER JOIN Roles rol on rol.id_rol = u.id_rol" +
                    " WHERE h.id_hunter = ?";

            PreparedStatement joinStatement = con.prepareStatement(joinQuery);
            joinStatement.setInt(1, insertedHunterId);

            ResultSet joinResult = joinStatement.executeQuery();



            if (joinResult.next()) {
                Rol rol = new Rol();
                rol.setIdRol(joinResult.getInt("id_rol"));
                rol.setDescripcion(joinResult.getString("rol"));
                Rango rango = new Rango();
                rango.setIdRango(joinResult.getInt("id_rango"));
                rango.setDescripcion(joinResult.getString("rango"));
                Usuario u = new Usuario();
                u.setId_usuario(joinResult.getInt("id_usuario"));
                u.setUsername(joinResult.getString("username"));
                u.setPassword(joinResult.getString("password"));
                u.setRol(rol);
                h = new Hunter();
                h.setIdHunter(joinResult.getInt("id_hunter"));
                h.setUser(u);
                h.setId_rango(rango);
                h.setNombre(joinResult.getString("nombre"));
                h.setApellido(joinResult.getString("apellido"));
                h.setDireccion(joinResult.getString("direccion"));
                h.setTelefono(joinResult.getString("telefono"));
                h.setDni(joinResult.getString("dni"));
                h.setSexo(joinResult.getString("sexo"));
                h.setCorreo_electronico(joinResult.getString("correo_electronico"));
                h.setFecha_nacimiento(joinResult.getDate("fecha_nacimiento"));
                h.setPuntaje(joinResult.getInt("puntaje"));
            }
            else{
                Log.d("rESULT", joinResult.toString());
            }

            con.close();

            if (rowsAffected > 0 && h != null) {
                return h;
            } else {
                return null;
            }

        } catch (SQLException e){
                String sqlState = e.getSQLState();
                if("23000".equals(sqlState)){
                    if(e.getMessage().contains("correo_electronico")){
                        publishProgress("El Correo electrónico ya se encuentra en uso");
                        eliminarUsuarioActual();
                    }
                return null;
                }
                else{
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return h;
    }

    @Override
    protected void onProgressUpdate(String... messages) {
        super.onProgressUpdate(messages);
        Toast.makeText(ctx, messages[0], Toast.LENGTH_SHORT).show();
    }

    @Override
protected void onPostExecute(Hunter response) {
    if(response != null){
        ruCallback.onCompleteInsertAndRedirectToApp(response);
    }
    else{
        Toast.makeText(ctx, "Error al registrarse", Toast.LENGTH_SHORT).show();
    }
}

    private void eliminarUsuarioActual() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Connection con = null;
                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "DELETE FROM Usuarios WHERE id_usuario = ?";
                    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                        preparedStatement.setInt(1, hunter.getUser().getId_usuario());
                        preparedStatement.executeUpdate();
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        }.execute();
    }
}
