package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Resenia;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AdminRepository {
    public CompletableFuture<Integer> getCantidadComercios() {
        return CompletableFuture.supplyAsync(() -> {
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
    }
    public CompletableFuture<Integer> getCantidadHunters() {
        return CompletableFuture.supplyAsync(() -> {
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
    }

}

