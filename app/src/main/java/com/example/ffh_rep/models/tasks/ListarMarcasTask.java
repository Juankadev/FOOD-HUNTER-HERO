package com.example.ffh_rep.models.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.interfaces.ListarMarcasCallback;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ListarMarcasTask extends AsyncTask<Void, Void, List<Marca>> {
    private Context ctx;
    private ListarMarcasCallback lmc;

    public ListarMarcasTask(Context ctx, ListarMarcasCallback lmc) {
        this.ctx = ctx;
        this.lmc = lmc;
    }

    @Override
    protected List<Marca> doInBackground(Void... voids) {
        List<Marca> marcas = new ArrayList<>();

        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "SELECT id_marca, descripcion FROM Marcas";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idMarca = resultSet.getInt("id_marca");
                String descripcion = resultSet.getString("descripcion");

                Marca marca = new Marca(idMarca, descripcion);
                marcas.add(marca);
            }

            resultSet.close();
            preparedStatement.close();
            con.close();

            return marcas;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Marca> marcas) {
        if (marcas != null) {
            lmc.onMarcasListadas(marcas);
        } else {
            lmc.onListarMarcasError("Error al listar marcas");
        }
    }
}
