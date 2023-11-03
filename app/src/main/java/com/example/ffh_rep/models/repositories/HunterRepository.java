package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.models.tasks.ModificarHunterTask;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CompletableFuture;

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
                    mlHunter.postValue(hunter);
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
                String query = "Update Usuarios set estado = '0' where id_usuario = ?";
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
}
