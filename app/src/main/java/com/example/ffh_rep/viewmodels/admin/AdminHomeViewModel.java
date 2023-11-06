package com.example.ffh_rep.viewmodels.admin;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.ffh_rep.models.repositories.AdminRepository;

public class AdminHomeViewModel extends ViewModel {
    private Context ctx;
    private AdminRepository adminRepo;
    public AdminHomeViewModel(){}

    public AdminHomeViewModel(Context ctx) {
        this.ctx = ctx;
        this.adminRepo = new AdminRepository();
    }
    public String getCantidadComercios(){ return adminRepo.getCantidadComercios(); }
    public String getCantidadHunters(){ return adminRepo.getCantidadHunters(); }
}