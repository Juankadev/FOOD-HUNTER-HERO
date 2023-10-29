package com.example.ffh_rep.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.tasks.ModificarHunterTask;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import kotlin.jvm.internal.CollectionToArray;

public class HunterRepository {

    private ModificarHunterTask modificarTask;

    public void updateUserInfo(MutableLiveData<Hunter> mlHunter, Hunter hunter, Context ctx){
        CompletableFuture.runAsync(() -> {

            try {
                Connection con = DBUtil.getConnection();
                String query = "Update Hunters set nombre = ?, apellido = ?, dni = ?, sexo = ?, correo_electronico = ?, direccion = ? where id_hunter = ?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, hunter.getNombre());
                ps.setString(2, hunter.getApellido());
                ps.setString(3, hunter.getDni());
                ps.setString(4, hunter.getSexo());
                ps.setString(5, hunter.getCorreo_electronico());
                ps.setString(6, hunter.getDireccion());
                ps.setInt(7, hunter.getIdHunter());

                int rowsAffected = ps.executeUpdate();
                ps.close();
                DBUtil.closeConnection(con);
                if(rowsAffected > 0){
                    Toast.makeText(ctx, "Información actualizada con exito", Toast.LENGTH_LONG);
                    mlHunter.postValue(hunter);
                }
                else{
                    Toast.makeText(ctx, "Ocurrio un error al actualizar la información", Toast.LENGTH_LONG);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(ctx, "Ocurrio un error al actualizar la información", Toast.LENGTH_LONG);
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
