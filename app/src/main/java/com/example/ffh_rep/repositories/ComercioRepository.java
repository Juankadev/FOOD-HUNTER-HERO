package com.example.ffh_rep.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ComercioRepository {
    /**
     * Obtiene y retorna la lista de comercios aprobados desde la base de datos de manera asíncrona.
     * Utiliza un CompletableFuture para ejecutar la consulta en un hilo separado y actualiza el MutableLiveData con los resultados.
     *
     * @param mlDataComercio MutableLiveData que se actualizará con la lista de comercios obtenida.
     * @return MutableLiveData que contiene la lista de comercios, actualizado de forma asíncrona.
     */
    public MutableLiveData<List<Comercio>> getComercios(MutableLiveData<List<Comercio>> mlDataComercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Comercio> lComercios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Comercios c WHERE c.aprobado LIKE 'Aprobado'");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_comercio");
                    String razonsocial = rs.getString("razon_social");
                    String cuit = rs.getString("cuit");
                    String rubro = rs.getString("rubro");
                    String email = rs.getString("correo_electronico");
                    String telefono = rs.getString("telefono");
                    String direccion = rs.getString("direccion");
                    String aprobado = rs.getString("aprobado");
                    Comercio cData = new Comercio(id, razonsocial, cuit, rubro, email, telefono, direccion, aprobado);
                    lComercios.add(cData);
                }
            } catch (Exception e) {
                // Manejar la excepción adecuadamente, por ejemplo, registrándola o lanzándola de nuevo
                e.printStackTrace();
            }
            return lComercios;
        }).thenAcceptAsync(comercios -> mlDataComercio.postValue(comercios));
        return mlDataComercio;
    }
    /**
     * Obtiene y retorna la lista de comercios cuya razón social contiene la cadena proporcionada de manera asíncrona.
     * Utiliza un CompletableFuture para ejecutar la consulta en un hilo separado y actualiza el MutableLiveData con los resultados.
     *
     * @param mlDataComercio MutableLiveData que se actualizará con la lista de comercios obtenida.
     * @param strComercio Cadena que se utilizará para buscar comercios por razón social.
     */
    public void getComercioByName(MutableLiveData<List<Comercio>> mlDataComercio, String strComercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Comercio> lComercios = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Comercios c WHERE c.razon_social LIKE ?")) {
                ps.setString(1, "%" + strComercio.trim() + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id_comercio");
                        String razonsocial = rs.getString("razon_social");
                        String cuit = rs.getString("cuit");
                        String rubro = rs.getString("rubro");
                        String email = rs.getString("correo_electronico");
                        String telefono = rs.getString("telefono");
                        String direccion = rs.getString("direccion");
                        String aprobado = rs.getString("aprobado");
                        Comercio cData = new Comercio(id, razonsocial, cuit, rubro, email, telefono, direccion, aprobado);
                        lComercios.add(cData);
                    }
                }
            } catch (Exception e) {
                // Manejar la excepción
                e.printStackTrace();
            }
            return lComercios;
        }).thenAcceptAsync(comercios -> mlDataComercio.postValue(comercios));
    }
}
