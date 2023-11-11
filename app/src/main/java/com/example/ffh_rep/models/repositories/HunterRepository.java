package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Rango;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.models.tasks.ModificarHunterTask;
import com.example.ffh_rep.utils.DBUtil;
import com.example.ffh_rep.utils.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class HunterRepository {

    private ModificarHunterTask modificarTask;

    public void updateUserInfo(MutableLiveData<Hunter> mlHunter, Hunter hunter, MutableLiveData<Boolean> updatingInfo, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
            updatingInfo.postValue(true);
            try {
                Connection con = DBUtil.getConnection();
                String query = "Update Hunters set nombre = ?, apellido = ?, fecha_nacimiento = ?, sexo = ?, correo_electronico = ?, direccion = ?, telefono = ? where id_hunter = ?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, hunter.getNombre());
                ps.setString(2, hunter.getApellido());
                Log.d("nacimiento", hunter.getFecha_nacimiento().toString());
                ps.setDate(3, hunter.getFecha_nacimiento());
                ps.setString(4, hunter.getSexo());
                ps.setString(5, hunter.getCorreo_electronico());
                ps.setString(6, hunter.getDireccion());
                ps.setString(7, hunter.getTelefono());
                ps.setInt(8, hunter.getIdHunter());

                int rowsAffected = ps.executeUpdate();
                ps.close();
                DBUtil.closeConnection(con);
                if(rowsAffected > 0){
                    updatingInfo.postValue(false);
                    success.postValue(true);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                error.postValue(false);
                updatingInfo.postValue(false);
            }
        });
    }

    public void eliminarCuenta(Hunter hunter, MutableLiveData<Boolean> isDelete, Context ctx){
        CompletableFuture.runAsync(() -> {
            try {
                Connection con = DBUtil.getConnection();
                String query = "Update Usuarios set estado = 0 where id_usuario = ?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, hunter.getUser().getId_usuario());

                int rowsAffected = ps.executeUpdate();
                ps.close();
                DBUtil.closeConnection(con);
                if(rowsAffected > 0){
                    isDelete.postValue(true);
                }
                else{
                    Toast.makeText(ctx, "Ocurrio un error al eliminar tu cuenta", Toast.LENGTH_LONG);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(ctx, "Ocurrio un error al eliminar tu cuenta", Toast.LENGTH_LONG);
            }
        });
    }

    public void actualizarRango(Hunter mlHunter, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error, SessionManager session) {
        CompletableFuture.runAsync(() -> {
            try {
                Hunter hunter = mlHunter;
                Connection con = DBUtil.getConnection();
                    List<Rango> listaRangos = obtenerListaRangos(con);
                    Rango _rango = obtenerSiguienteRango(listaRangos, hunter.getPuntaje(), hunter.getId_rango());
                    hunter.setId_rango(_rango);

                String updateQuery = "UPDATE Hunters SET id_rango = ? WHERE id_hunter = ?";
                try (PreparedStatement updateStatement = con.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, hunter.getId_rango().getId_rango());
                    updateStatement.setInt(2, hunter.getIdHunter());
                    updateStatement.executeUpdate();
                }
                Hunter _hunter = obtenerHunterPorId(con, hunter.getIdHunter());
                _hunter.setId_rango(_rango);
                session.saveHunterSession(_hunter);
                success.postValue(true);
                DBUtil.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
                error.postValue(false);
            }
        });
    }

    public Rango obtenerSiguienteRango(List<Rango> lista, int _currPuntaje, Rango _currRango){
            Rango siguienteRango = null;
            List<Rango> _array = lista.subList(_currRango.getId_rango(), lista.size());

            for (Rango rango : _array) {
                if (_currPuntaje >= rango.getPuntaje()) {
                    Log.d("Puntaje actual", String.valueOf(_currPuntaje));
                    Log.d("puntaje objetido", rango.toString());
                    siguienteRango = rango;
                    break;
                }
            }
            if(siguienteRango == null){
                siguienteRango = _currRango;
            }
            return siguienteRango;
    }

    private List<Rango> obtenerListaRangos(Connection con) throws SQLException {
        List<Rango> listaRangos = new ArrayList<>();
        String query = "SELECT * FROM Rangos ORDER BY puntaje ASC";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            Rango rango = new Rango(
                    resultSet.getInt("id_rango"),
                    resultSet.getString("descripcion"),
                    resultSet.getInt("puntaje")
            );
            listaRangos.add(rango);
        }

        ps.close();
        resultSet.close();

        return listaRangos;
    }

    private Hunter obtenerHunterPorId(Connection con, int idHunter) throws SQLException {
        String query = "SELECT h.id_hunter, h.nombre, h.apellido, h.dni, h.sexo, h.correo_electronico, h.telefono, h.direccion, h.fecha_nacimiento, h.puntaje," +
                " u.id_usuario, u.username, u.password," +
                " r.id_rango, r.descripcion as rango" +
                " FROM Hunters h" +
                " INNER JOIN Usuarios u on u.id_usuario = h.id_usuario" +
                " INNER JOIN Rangos r on r.id_rango = h.id_rango" +
                " WHERE id_hunter = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idHunter);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    Usuario u = new Usuario();
                    u.setUsername(resultSet.getString("username"));
                    u.setPassword(resultSet.getString("password"));
                    u.setId_usuario(resultSet.getInt("id_usuario"));
                    Rango rango = new Rango();
                    rango.setIdRango(resultSet.getInt("id_rango"));
                    rango.setDescripcion(resultSet.getString("rango"));
                    Hunter h = new Hunter(
                            resultSet.getInt("id_hunter"),
                            u,
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("dni"),
                            resultSet.getString("sexo"),
                            resultSet.getString("correo_electronico"),
                            resultSet.getString("telefono"),
                            resultSet.getString("direccion"),
                            resultSet.getDate("fecha_nacimiento"),
                            resultSet.getInt("puntaje")
                    );
                    h.setId_rango(rango);
                    return h;
                }
            }
        }
        return null;
    }
}
