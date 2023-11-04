package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StockRepository {

    public void insertarNuevoStock(Context context, Stock stock) {
        CompletableFuture.runAsync(() -> {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement insertPs = con.prepareStatement("INSERT INTO Stocks (id_articulo, id_comercio, fecha_vencimiento, cantidad) VALUES (?, ?, ?, ?)");
            ) {
                insertPs.setInt(1, stock.getId_articulo().getIdArticulo());
                insertPs.setInt(2, stock.getId_comercio().getId());
                insertPs.setDate(3, (Date) stock.getFecha_vencimiento());
                insertPs.setInt(4, stock.getCantidad());

                int rowCount = insertPs.executeUpdate();

                if (rowCount > 0) {
                    Toast.makeText(context, "Stock agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al insertar Stock", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al insertar Stock", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public MutableLiveData<List<Stock>> getStock(MutableLiveData<List<Stock>> mlDataStocks, Articulo articulo, Comercio comercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Stock> lStocks = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT id_stock, id_articulo, id_comercio, fecha_vencimiento, cantidad FROM Stocks WHERE id_articulo = ? AND id_comercio = ?");
            ) {
                ps.setInt(1, articulo.getIdArticulo());
                ps.setInt(2, comercio.getId());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setId_stock(rs.getInt("id_stock"));
                    Articulo art = new Articulo();
                    art.setIdArticulo(rs.getInt("id_articulo"));
                    stock.setId_articulo(art);
                    Comercio comer = new Comercio();
                    comer.setId(rs.getInt("id_comercio"));
                    stock.setId_comercio(comer);
                    stock.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                    stock.setCantidad(rs.getInt("cantidad"));

                    lStocks.add(stock);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lStocks;

        }).thenAcceptAsync(stocks -> {
            if (stocks != null) {
                mlDataStocks.postValue(stocks.isEmpty() ? new ArrayList<>() : stocks);
            } else {
                mlDataStocks.postValue(new ArrayList<>());
            }
        });

        return mlDataStocks;
    }
    public void restarStock(Context context, Stock stock, int cantidad) {
        CompletableFuture.runAsync(() -> {

            try (Connection con = DBUtil.getConnection();
                 PreparedStatement selectPs = con.prepareStatement("SELECT id_stock, cantidad FROM Stocks WHERE id_articulo = ? AND id_comercio = ? FOR UPDATE");
                 PreparedStatement updatePs = con.prepareStatement("UPDATE Stocks SET cantidad = ? WHERE id_stock = ?");
            ) {
                con.setAutoCommit(false);

                selectPs.setInt(1, stock.getId_articulo().getIdArticulo());
                selectPs.setInt(2, stock.getId_comercio().getId());
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
                            Toast.makeText(context, "Stock restado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            con.rollback();
                            Toast.makeText(context, "No se pudo restar el stock", Toast.LENGTH_SHORT).show();
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
        });
    }

}
