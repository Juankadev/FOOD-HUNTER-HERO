package com.example.ffh_rep.models.repositories;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AdminRepository {
    public String getCantidadComercios() {
        CompletableFuture<Integer> task_comercios_aprobados = CompletableFuture.supplyAsync(() -> {
            Integer cantidadComercios = 0;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as cantidad_comercios FROM Comercios WHERE aprobado LIKE 'Aprobado' ")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cantidadComercios = rs.getInt("cantidad_comercios");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cantidadComercios;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Integer result = task_comercios_aprobados.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result.toString();
    }
    public String getCantidadHunters() {
        CompletableFuture<Integer> task_cantidad_hunters = CompletableFuture.supplyAsync(() -> {
            Integer cantidadHunters = 0;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) as cantidad_hunters FROM Hunters ")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cantidadHunters = rs.getInt("cantidad_hunters");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cantidadHunters;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Integer result = task_cantidad_hunters.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result.toString();
    }
    public String getCantidadHuntersRangoMaximo() {
        CompletableFuture<Integer> task_result = CompletableFuture.supplyAsync(() -> {
            Integer cant = 0;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select count(*) as cazadores_rango_maximo from Hunters where id_rango = (SELECT MAX(id_rango) from Rangos)")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cant = rs.getInt("cazadores_rango_maximo");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cant;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Integer result = task_result.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result.toString();
    }
    public String getCantidadHuntersRangoMinimo() {
        CompletableFuture<Integer> task_result = CompletableFuture.supplyAsync(() -> {
            Integer cant = 0;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select count(*) as cazadores_rango_minimo from Hunters where id_rango = 1")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cant = rs.getInt("cazadores_rango_minimo");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cant;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Integer result = task_result.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result.toString();
    }
    public String getCantidadProductosCazados(String desde, String hasta) {
        CompletableFuture<Integer> task_result = CompletableFuture.supplyAsync(() -> {
            Integer cant = 0;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select COALESCE(SUM(cxa.Cantidad),0) as cantidad_total from Cazas as c inner join Caza_x_Articulo as cxa on c.id_caza = cxa.ID_caza where fecha between ? and ?")) {
                ps.setString(1, desde);
                ps.setString(2, hasta);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cant = rs.getInt("cantidad_total");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cant;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Integer result = task_result.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result.toString();
    }
    public String getArticuloMayorCazas(String desde, String hasta) {
        CompletableFuture<String> task_result = CompletableFuture.supplyAsync(() -> {
            String descripcion = "";
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select a.descripcion as descripcion, COALESCE(SUM(cxa.Cantidad),0) as cantidad from Cazas as c inner join Caza_x_Articulo as cxa on c.id_caza = cxa.ID_caza inner join Articulos as a on a.id_articulo = cxa.id_articulo where c.fecha between ? and ? GROUP BY a.descripcion ORDER BY cantidad DESC LIMIT 1")) {
                ps.setString(1, desde);
                ps.setString(2, hasta);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        descripcion = rs.getString("descripcion");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return descripcion;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        String result = task_result.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result;
    }
    public String getCantidadCompras(String desde, String hasta) {
        CompletableFuture<Integer> task_result = CompletableFuture.supplyAsync(() -> {
            Integer cant = 0;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select COUNT(*) as cantidad_compras from Cazas where fecha between ? and ?")) {
                ps.setString(1, desde);
                ps.setString(2, hasta);
                System.out.println(desde);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cant = rs.getInt("cantidad_compras");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cant;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Integer result = task_result.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result.toString();
    }
    //Devuelve 3 categorias principales
    public Map getCategoriasMasCazadas(String desde, String hasta) {
        CompletableFuture<Map> task_result = CompletableFuture.supplyAsync(() -> {
            Map<String,Integer> categorias = new HashMap<>();

            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select cat.descripcion as descripcion, COALESCE(SUM(cxa.Cantidad),0) as cantidad from Cazas as c inner join Caza_x_Articulo as cxa on c.id_caza = cxa.ID_caza inner join Articulos as a on a.id_articulo = cxa.id_articulo inner join Categorias as cat on a.id_categoria = cat.id_categoria where c.fecha between ? and ? GROUP BY cat.descripcion ORDER BY cantidad DESC LIMIT 3")) {
                ps.setString(1, desde);
                ps.setString(2, hasta);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        categorias.put(rs.getString("descripcion"),rs.getInt("cantidad"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return categorias;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Map<String,Integer> result = task_result.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result;
    }
    public String[] getEstadisticas(String desde, String hasta) {
        String[] results = new String[]{
                getCantidadHuntersRangoMaximo(),
                getCantidadHuntersRangoMinimo(),
                getCantidadProductosCazados(desde,hasta),
                getCantidadComercios(),
                getArticuloMayorCazas(desde,hasta),
                getCantidadCompras(desde,hasta),
        };

        return results;
    }

}

