package com.example.ffh_rep.repositories;

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

public class HunterRepository {

    private ModificarHunterTask modificarTask;

    public void updateUserInfo(MutableLiveData<Hunter> mlHunter, Hunter hunter){

        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                Connection con = null;
                PreparedStatement ps = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Update Hunters set nombre = ?, apellido = ?, dni = ?, sexo = ?, correo_electronico = ?, direccion = ? where id_hunter = ?";
                    ps = con.prepareStatement(query);

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
                    return rowsAffected > 0;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(aBoolean){
                    mlHunter.postValue(hunter);
                }
                else{
                    Log.d("ERROR", "ERROR EN EDITAR INFORMACION");
                }
            }
        }.execute();
        }
    }
