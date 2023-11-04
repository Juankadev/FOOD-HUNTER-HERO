package com.example.ffh_rep.viewmodels.admin;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.models.repositories.AdminRepository;
import com.example.ffh_rep.models.repositories.ComercioRepository;

import java.util.concurrent.CompletableFuture;

public class AdminHomeViewModel extends ViewModel {
    private Context ctx;
    private AdminRepository adminRepo;
    public AdminHomeViewModel(){}
    public Integer cantidadComercios;

    public AdminHomeViewModel(Context ctx) {
        this.ctx = ctx;
        this.adminRepo = new AdminRepository();
    }
    public CompletableFuture<Integer> getCantidadComercios(){ return adminRepo.getCantidadComercios(); }
    public CompletableFuture<Integer> getCantidadHunters(){ return adminRepo.getCantidadHunters(); }
}