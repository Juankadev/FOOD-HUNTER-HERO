package com.example.ffh_rep.viewmodels.admin;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.models.repositories.AdminRepository;

public class AdminEstadisticasViewModel extends ViewModel {
    private Context ctx;
    private  AdminRepository adminRepo;

    public AdminEstadisticasViewModel() {}
    public AdminEstadisticasViewModel(Context ctx) {
        this.ctx = ctx;
        this.adminRepo = new AdminRepository();
    }

    public String[] getEstadisticas(String desde, String hasta){ return adminRepo.getEstadisticas(desde,hasta); }
}