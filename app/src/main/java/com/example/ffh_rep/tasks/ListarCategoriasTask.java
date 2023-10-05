package com.example.ffh_rep.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.interfaces.ListarCategoriasCallback;
import com.example.ffh_rep.utils.DB_Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ListarCategoriasTask extends AsyncTask<Void, Void, List<Categoria>> {
    private Context ctx;
    private ListarCategoriasCallback lcc;

    public ListarCategoriasTask(Context ctx, ListarCategoriasCallback lcc) {
        this.ctx = ctx;
        this.lcc = lcc;
    }

    @Override
    protected List<Categoria> doInBackground(Void... voids) {
        List<Categoria> categorias = new ArrayList<>();

        try {
            Class.forName(DB_Env.DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_Env.DB_URL_MYSQL, DB_Env.DB_USER, DB_Env.DB_PASSWORD);

            String query = "SELECT id_categoria, descripcion FROM Categorias";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idCategoria = resultSet.getInt("id_categoria");
                String descripcion = resultSet.getString("descripcion");

                Categoria categoria = new Categoria(idCategoria, descripcion);
                categorias.add(categoria);
            }

            resultSet.close();
            preparedStatement.close();
            con.close();

            return categorias;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Categoria> categorias) {
        if (categorias != null) {
            lcc.onCategoriasListadas(categorias);
        } else {
            lcc.onListarCategoriasError("Error al listar categorías");
        }
    }
}
