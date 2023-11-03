package com.example.ffh_rep.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CompletableFuture;

public class StockRepository {

    public MutableLiveData<Boolean> insertarNuevoStock(MutableLiveData<Boolean> mlDataSuccess, Articulo articulo, Comercio comercio, int cantidad, Date fechaVencimiento) {
        CompletableFuture.runAsync(() -> {
            boolean success = false;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement insertPs = con.prepareStatement("INSERT INTO Stocks (id_articulo, id_comercio, fecha_vencimiento, cantidad) VALUES (?, ?, ?, ?)");
            ) {
                insertPs.setInt(1, articulo.getIdArticulo());
                insertPs.setInt(2, comercio.getId());
                insertPs.setDate(3, fechaVencimiento);
                insertPs.setInt(4, cantidad);

                int rowCount = insertPs.executeUpdate();

                if (rowCount > 0) {
                    success = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mlDataSuccess.postValue(success);
        });

        return mlDataSuccess;
    }
    public MutableLiveData<Stock> getStock(MutableLiveData<Stock> mlDataStock, Articulo articulo, Comercio comercio) {
        CompletableFuture.supplyAsync(() -> {
            Stock stock = new Stock();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT id_stock, id_articulo, id_comercio, fecha_vencimiento, cantidad FROM Stocks WHERE id_articulo = ? AND id_comercio = ?");
            ) {
                ps.setInt(1, articulo.getIdArticulo());
                ps.setInt(2, comercio.getId());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    stock.setId_stock(rs.getInt("id_stock"));
                    Articulo art = new Articulo();
                    art.setIdArticulo(rs.getInt("id_articulo"));
                    stock.setId_articulo(art);
                    Comercio comer = new Comercio();
                    comer.setId(rs.getInt("id_comercio"));
                    stock.setId_comercio(comer);
                    stock.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                    stock.setCantidad(rs.getInt("cantidad"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stock;
        }).thenAcceptAsync(stock -> {
            if (stock != null) {
                mlDataStock.postValue(stock);
            } else {
                mlDataStock.postValue(new Stock());
            }
        });

        return mlDataStock;
    }
    public MutableLiveData<Boolean> restarStock(MutableLiveData<Boolean> mlDataSuccess, Articulo articulo, Comercio comercio, int cantidad) {
        CompletableFuture.runAsync(() -> {
            boolean success = false;
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement selectPs = con.prepareStatement("SELECT id_stock, cantidad FROM Stocks WHERE id_articulo = ? AND id_comercio = ? FOR UPDATE");
                 PreparedStatement updatePs = con.prepareStatement("UPDATE Stocks SET cantidad = ? WHERE id_stock = ?");
            ) {
                con.setAutoCommit(false);

                selectPs.setInt(1, articulo.getIdArticulo());
                selectPs.setInt(2, comercio.getId());
                ResultSet rs = selectPs.executeQuery();

                if (rs.next()) {
                    int idStock = rs.getInt("id_stock");
                    int stockCantidad = rs.getInt("cantidad");

                    if (stockCantidad >= cantidad && cantidad > 0) {
                        int nuevaCantidad = stockCantidad - cantidad;

                        updatePs.setInt(1, nuevaCantidad);
                        updatePs.setInt(2, idStock);
                        int rowCount = updatePs.executeUpdate();

                        if (rowCount > 0) {
                            con.commit();
                            success = true;
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mlDataSuccess.postValue(success);
        });

        return mlDataSuccess;
    }

}
