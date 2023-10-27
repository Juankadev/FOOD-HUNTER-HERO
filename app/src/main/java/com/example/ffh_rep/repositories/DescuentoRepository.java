package com.example.ffh_rep.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.utils.DBUtil;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DescuentoRepository {
    public void listarDescuentos(MutableLiveData<List<Beneficio>> listBeneficios){
        new AsyncTask<Void, Void, List<Beneficio>>(){
            @Override
            protected List<Beneficio> doInBackground(Void... voids) {
                List<Beneficio> lBeneficios = new ArrayList<>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    con = DBUtil.getConnection();
                    String query = "Select * from Beneficios b where b.estado = 1";
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();

                    while(rs.next()){
                        Beneficio bData = new Beneficio();
                        int id = rs.getInt("id_beneficio");
                        String descripcion = rs.getString("descripcion");
                        int puntos_requeridos = rs.getInt("puntos_requeridos");
                        bData.setId_beneficio(id);
                        bData.setDescripcion(descripcion);
                        bData.setPuntos_requeridos(puntos_requeridos);
                        lBeneficios.add(bData);
                    }
                    rs.close();
                    ps.close();
                    DBUtil.closeConnection(con);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                Log.d("Beneficios task", lBeneficios.toString());
                return lBeneficios;
            }

            @Override
            protected void onPostExecute(List<Beneficio> beneficio) {
                super.onPostExecute(beneficio);
                if(beneficio.isEmpty()){
                    listBeneficios.postValue(new ArrayList<>());
                }
                else{
                    listBeneficios.postValue(beneficio);
                }
            }
        }.execute();
    }

    public void modificarDescuento(Context context, Beneficio beneficio) {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "UPDATE Beneficios set descripcion = ?, puntos_requeridos = ? where id_beneficio = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(query);

                    preparedStatement.setString(1, beneficio.getDescripcion());
                    preparedStatement.setInt(2, beneficio.getPuntos_requeridos());
                    preparedStatement.setInt(3, beneficio.getId_beneficio());

                    int rowsAffected = preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();

                    return rowsAffected > 0;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Descuento modificado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al modificar descuento", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void agregarDescuento(Context context, Beneficio beneficio) {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "INSERT INTO Beneficios (id_comercio, descripcion, puntos_requeridos) " +
                            "VALUES (?, ?, ?)";

                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setInt(1, beneficio.getId_comercio().getId());
                    preparedStatement.setString(2, beneficio.getDescripcion());
                    preparedStatement.setInt(3, beneficio.getPuntos_requeridos());

                    int rowsAffected = preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();

                    return rowsAffected > 0;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Descuento  agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al agregar descuento", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void eliminarDescuento(Context context, Beneficio beneficio) {

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                try {
                    Class.forName(DB_Env.DB_DRIVER);
                    Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

                    String query = "UPDATE Beneficios set estado = 0 where id_beneficio = ?";

                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setInt(1, beneficio.getId_beneficio());

                    int rowsAffected = preparedStatement.executeUpdate();
                    preparedStatement.close();
                    con.close();

                    return rowsAffected > 0;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result) {
                    Toast.makeText(context, "Descuento eliminado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminar descuento", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
