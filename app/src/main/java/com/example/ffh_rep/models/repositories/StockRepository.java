package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
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
            try (Connection con = DBUtil.getConnection()) {
                // Verificar si ya existe un registro con los mismos valores
                String selectQuery = "SELECT id_stock, cantidad FROM Stocks WHERE id_articulo = ? AND id_comercio = ? AND fecha_vencimiento = ?";
                try (PreparedStatement selectPs = con.prepareStatement(selectQuery)) {
                    selectPs.setInt(1, stock.getId_articulo().getIdArticulo());
                    selectPs.setInt(2, stock.getId_comercio().getId());
                    selectPs.setDate(3, stock.getFecha_vencimiento());

                    try (ResultSet resultSet = selectPs.executeQuery()) {
                        if (resultSet.next()) {
                            int existingStockId = resultSet.getInt("id_stock");
                            int existingQuantity = resultSet.getInt("cantidad");

                            int newQuantity = existingQuantity + stock.getCantidad();

                            String updateQuery = "UPDATE Stocks SET cantidad = ? WHERE id_stock = ?";
                            try (PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
                                updatePs.setInt(1, newQuantity);
                                updatePs.setInt(2, existingStockId);

                                int updateRowCount = updatePs.executeUpdate();

                                if (updateRowCount > 0) {
                                    mostrarToast(context, "Cantidad de stock actualizada exitosamente");
                                } else {
                                    mostrarToast(context, "Error al actualizar el stock");
                                }
                            }
                        } else {
                            String insertQuery = "INSERT INTO Stocks (id_articulo, id_comercio, fecha_vencimiento, cantidad) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement insertPs = con.prepareStatement(insertQuery)) {
                                insertPs.setInt(1, stock.getId_articulo().getIdArticulo());
                                insertPs.setInt(2, stock.getId_comercio().getId());
                                insertPs.setDate(3, stock.getFecha_vencimiento());
                                insertPs.setInt(4, stock.getCantidad());

                                int insertRowCount = insertPs.executeUpdate();

                                if (insertRowCount > 0) {
                                    mostrarToast(context, "Stock agregado exitosamente");
                                } else {
                                    mostrarToast(context, "Error al insertar el stock");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mostrarToast(context, "Error al insertar Stock");
            }
        });
    }

    private void mostrarToast(Context context, String message) {
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        });
    }

    public MutableLiveData<List<Stock>> getStock(MutableLiveData<List<Stock>> mlDataStocks, Articulo articulo, Comercio comercio) {
        CompletableFuture.supplyAsync(() -> {
            List<Stock> lStocks = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT id_stock, id_articulo, id_comercio, fecha_vencimiento, cantidad FROM Stocks WHERE id_articulo = ? AND id_comercio = ?");
            ) {
                Log.d("DEBUG", "-----------");
                Log.d("DEBUG", articulo.toString());
                Log.d("DEBUG", "-----------");
                Log.d("DEBUG", "-----------");
                Log.d("DEBUG", comercio.toString());
                Log.d("DEBUG", "-----------");

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

    public void getStockByComercioAndFechaVencimiento(MutableLiveData<List<Stock>> listaOriginal, MutableLiveData<List<Stock>> mlStocks, MutableLiveData<Boolean> loading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error, Comercio commerce){
        CompletableFuture.supplyAsync(() -> {
            loading.postValue(true);
            List<Stock> lStocks = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT S1.id_stock, S1.id_articulo, S1.id_comercio, S1.fecha_vencimiento, S1.cantidad, a.descripcion as articulodescripcion, a.precio, a.imagen, a.estado, c.id_categoria as idcategoria, c.descripcion as categoriadescripcion, m.id_marca as idmarca, m.descripcion as marcadescripcion, MIN(S2.fecha_vencimiento) as fecha_vencimiento_minima " +
                         "FROM Stocks AS S1 INNER JOIN Articulos a on a.id_articulo = S1.id_articulo " +
                         "INNER JOIN Categorias c on c.id_categoria = a.id_categoria " +
                         "INNER JOIN Marcas m on m.id_marca = a.id_marca " +
                         "LEFT JOIN Stocks AS S2 ON S1.id_articulo = S2.id_articulo " +
                         "AND S1.id_comercio = S2.id_comercio AND S2.fecha_vencimiento > S1.fecha_vencimiento " +
                         "WHERE S1.id_comercio = ? AND a.estado = 1 AND S1.cantidad > 0 GROUP BY S1.id_stock, S1.id_articulo, S1.id_comercio, S1.fecha_vencimiento, a.descripcion, a.precio, a.imagen, a.estado, c.id_categoria, c.descripcion, m.id_marca, m.descripcion;");
            ) {
                ps.setInt(1, commerce.getId());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setId_stock(rs.getInt("id_stock"));
                    Articulo art = new Articulo();
                    art.setIdArticulo(rs.getInt("id_articulo"));
                    art.setDescripcion(rs.getString("articulodescripcion"));
                    art.setPrecio(rs.getDouble("precio"));
                    art.setImagen(rs.getString("imagen"));
                    art.setMarca(new Marca());
                    art.getMarca().setIdMarca(rs.getInt("idmarca"));
                    art.getMarca().setDescripcion(rs.getString("marcadescripcion"));
                    art.setCategoria(new Categoria());
                    art.getCategoria().setIdCategoria(rs.getInt("idcategoria"));
                    art.getCategoria().setDescripcion(rs.getString("categoriadescripcion"));
                    art.setComercio(commerce);
                    stock.setId_articulo(art);
                    stock.setId_comercio(commerce);
                    stock.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                    stock.setCantidad(rs.getInt("cantidad"));

                    lStocks.add(stock);
                }
            } catch (Exception e) {
                e.printStackTrace();
                loading.postValue(false);
                error.postValue(true);
            }
            return lStocks;

        }).thenAcceptAsync(stocks -> {
            Log.d("Stocs", stocks.toString());
            loading.postValue(false);
            if (stocks != null) {
                listaOriginal.postValue(stocks);
                mlStocks.postValue(stocks.isEmpty() ? new ArrayList<>() : stocks);
                success.postValue(true);
            } else {
                success.postValue(true);
                mlStocks.postValue(new ArrayList<>());
            }
        });
    }

}
