package com.example.ffh_rep.models.repositories;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CompletableFuture;

public class AdminRepository {
    public Integer getCantidadComercios() {
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
        return result;
    }
    public Integer getCantidadHunters() {
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
        return result;
    }
    public Integer getCantidadHuntersRangoMaximo() {
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
        return result;
    }
    public Integer getCantidadHuntersRangoMinimo() {
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
        return result;
    }
    public Integer getCantidadProductosCazados() {
        CompletableFuture<Integer> task_result = CompletableFuture.supplyAsync(() -> {
            Integer cant = 0;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select COALESCE(SUM(cantidad),0) as productos_cazados from Caza_x_Articulo")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cant = rs.getInt("productos_cazados");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cant;
        });

        // Espera a que el CompletableFuture se complete y obtiene el resultado
        Integer result = task_result.join(); // join() bloquea hasta que se complete la tarea completablefuture
        return result;
    }
    public Integer[] getEstadisticas() {
        Integer[] results = new Integer[]{
                getCantidadHuntersRangoMaximo(),
                getCantidadHuntersRangoMinimo(),
                getCantidadProductosCazados(),
                getCantidadComercios()
        };
        return results;
    }

}
