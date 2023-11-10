package com.example.ffh_rep.models.repositories;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.ItemCarrito;
import com.example.ffh_rep.entidades.JSONQRRequest;
import com.example.ffh_rep.entidades.QrObject;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class QrRepository {

    public void insertQrData(QrObject qr, MutableLiveData<Boolean> loading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error, MutableLiveData<Integer> generatedId){
        CompletableFuture.runAsync(() -> {
            loading.postValue(true);
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement insertPs = con.prepareStatement("INSERT INTO Generar_Qr (id_comercio, id_hunter, estado) VALUES (?, ?, ?)",  Statement.RETURN_GENERATED_KEYS);
            ) {
                insertPs.setInt(1, qr.getCommerce().getId());
                insertPs.setInt(2, qr.getHunter().getIdHunter());
                insertPs.setInt(3, 0);

                int rowCount = insertPs.executeUpdate();



                if (rowCount > 0) {
                    ResultSet generatedKeys = insertPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int idGenerado = generatedKeys.getInt(1);
                        generatedId.postValue(idGenerado);
                    }
                    loading.postValue(false);
                    success.postValue(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                loading.postValue(false);
                error.postValue(true);
            }
        });
    }

    public void qrDelete(JSONQRRequest request, MutableLiveData<Boolean> success){
        CompletableFuture.runAsync(()->{
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM Generar_Qr WHERE id_qr = ?")) {
                ps.setInt(1, request.getIdQr());

                int rowsAffected = ps.executeUpdate();
                if(rowsAffected > 0){
                    success.setValue(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void rejectHunt(JSONQRRequest request, MutableLiveData<Boolean> loading, MutableLiveData<Boolean> succes, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
        loading.postValue(true);
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("UPDATE Generar_Qr SET estado = 2 WHERE id_qr = ?")) {
                ps.setInt(1, request.getIdQr());

                int rowsAffected = ps.executeUpdate();
                if(rowsAffected > 0){
                    loading.postValue(false);
                    succes.postValue(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                loading.postValue(false);
                error.postValue(true);
            }
        });
    }
            public void aprobeHunt(JSONQRRequest request, MutableLiveData<Boolean> loading, MutableLiveData<Boolean> success, MutableLiveData<Boolean> error){
        CompletableFuture.runAsync(() -> {
            Log.d("aprobe", "INICIANDO");
            loading.postValue(true);
            try (Connection con = DBUtil.getConnection()) {
                con.setAutoCommit(false);

                try {
                    int idCaza = generarCaza(con, request);
                    agregarProductosALaCaza(con, idCaza, request.getlCarrito());
                    modificarStock(con, request.getlCarrito());
                    modificarPuntosHunter(con, request.getIdHunter(), request.getPuntos());
                    cambiarEstadoGenerarQR(con, request);
                    con.commit();
                    loading.postValue(false);
                    success.postValue(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    con.rollback();
                } finally {
                    con.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                loading.postValue(false);
                error.postValue(true);
            }
        });
    }

    //AUXILIARES PARA APROBAR CAZA
    private int generarCaza(Connection con, JSONQRRequest request) throws SQLException {
        Log.d("aprobe", "PASO 1");
        int idCazaGenerado;
        String insertCazaQuery = "INSERT INTO Cazas (id_hunter, id_comercio, puntos, fecha) VALUES (?, ?, ?, CURTIME())";

        try (PreparedStatement insertCazaStmt = con.prepareStatement(insertCazaQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertCazaStmt.setInt(1, request.getIdHunter());
            insertCazaStmt.setInt(2, request.getIdComercio());
            insertCazaStmt.setInt(3, request.getPuntos());

            insertCazaStmt.executeUpdate();

            ResultSet generatedKeys = insertCazaStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                idCazaGenerado = generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se generó un ID de caza.");
            }
        }

        return idCazaGenerado;
    }

    private void agregarProductosALaCaza(Connection con, int idCaza, List<ItemCarrito> productos) throws SQLException {
        Log.d("aprobe", "PASO 2");
        String insertCazaProductoQuery = "INSERT INTO Caza_x_Articulo (ID_caza, id_articulo, Cantidad) VALUES (?, ?, ?)";

        try (PreparedStatement insertProductoStmt = con.prepareStatement(insertCazaProductoQuery)) {
            for (ItemCarrito producto : productos) {
                insertProductoStmt.setInt(1, idCaza);
                insertProductoStmt.setInt(2, producto.getArtc().getId_articulo().getIdArticulo());
                insertProductoStmt.setInt(3, producto.getCantidad());
                insertProductoStmt.executeUpdate();
            }
        }
    }

    private void modificarStock(Connection con, List<ItemCarrito> productos) throws SQLException {
        Log.d("aprobe", "PASO 3");
        String selectStockQuery = "SELECT id_articulo, cantidad FROM Stocks WHERE id_stock = ?";
        String updateStockQuery = "UPDATE Stocks SET cantidad = ? WHERE id_stock = ?";

        try (PreparedStatement selectStockStmt = con.prepareStatement(selectStockQuery);
             PreparedStatement updateStockStmt = con.prepareStatement(updateStockQuery)) {

            for (ItemCarrito producto : productos) {
                int idArticulo = producto.getArtc().getId_stock();
                int cantidadElegida = producto.getCantidad();
                selectStockStmt.setInt(1, idArticulo);
                ResultSet resultSet = selectStockStmt.executeQuery();
                if (resultSet.next()) {
                    int cantidadBase = resultSet.getInt("cantidad");
                    int nuevaCantidad = cantidadBase - cantidadElegida;
                    updateStockStmt.setInt(1, nuevaCantidad);
                    updateStockStmt.setInt(2, idArticulo);
                    updateStockStmt.executeUpdate();
                }
            }
        }
    }

    private void modificarPuntosHunter(Connection con, int idHunter, int puntosASumar) throws SQLException {
        Log.d("aprobe", "PASO 4");
        String selectPuntosHunterQuery = "SELECT puntaje FROM Hunters WHERE id_hunter = ?";
        String updatePuntosHunterQuery = "UPDATE Hunters SET puntaje = ? WHERE id_hunter = ?";

        try (PreparedStatement selectPuntosHunterStmt = con.prepareStatement(selectPuntosHunterQuery);
             PreparedStatement updatePuntosHunterStmt = con.prepareStatement(updatePuntosHunterQuery)) {
            selectPuntosHunterStmt.setInt(1, idHunter);
            ResultSet resultSet = selectPuntosHunterStmt.executeQuery();
            if (resultSet.next()) {
                int puntosActuales = resultSet.getInt("puntaje");
                int nuevoPuntaje = puntosActuales + puntosASumar;
                updatePuntosHunterStmt.setInt(1, nuevoPuntaje);
                updatePuntosHunterStmt.setInt(2, idHunter);
                updatePuntosHunterStmt.executeUpdate();
            }
        }
    }
    private void cambiarEstadoGenerarQR(Connection con, JSONQRRequest request) throws SQLException {
        Log.d("aprobe", "PASO 5");
        String updateGenerarQRQuery = "UPDATE Generar_Qr SET estado = ? WHERE id_comercio = ? and id_hunter = ?";

        try (PreparedStatement updateGenerarQRStmt = con.prepareStatement(updateGenerarQRQuery)) {
            updateGenerarQRStmt.setInt(1, 1);
            updateGenerarQRStmt.setInt(2, request.getIdComercio());
            updateGenerarQRStmt.setInt(3, request.getIdHunter());
            updateGenerarQRStmt.executeUpdate();
        }
    }

    public void pollingIntoQrState(MutableLiveData<Boolean> qrState, JSONQRRequest qr, MutableLiveData<Boolean> enabled, MutableLiveData<Boolean> reject) {
        Handler handler = new Handler(Looper.getMainLooper());
        long pollingInterval = 5000;

        if(enabled.getValue()){
        Runnable pollRunnable = new Runnable() {
            @Override
            public void run() {
                if(!enabled.getValue()){
                    return;
                }
                CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                    return checkQRState(qr.getIdQr());
                });

                future.thenAcceptAsync(state -> {
                    if (state == 1) {
                        handler.post(() -> {
                            qrState.postValue(true);
                            handler.removeCallbacks(this);
                            enabled.postValue(false);
                        });
                    } else if (state == 2) {
                        handler.post(() -> {
                            handler.removeCallbacks(this);
                            reject.postValue(true);
                            enabled.postValue(false);
                        });
                    } else if (state == -1) {
                    }
                }).exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });

                if (future.join() != 1 && future.join() != 2) {
                    handler.postDelayed(this, pollingInterval);
                }
            }
        };

        handler.post(pollRunnable);
        }

    }


    private int checkQRState(int idQr) {
        String sql = "SELECT estado FROM Generar_Qr WHERE id_qr = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idQr);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("estado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
