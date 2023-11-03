package com.example.ffh_rep.views.ui.admin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffh_rep.R;
import com.example.ffh_rep.viewmodels.admin.AdminEstadisticasViewModel;

public class AdminEstadisticas extends Fragment {

    private AdminEstadisticasViewModel mViewModel;

    public static AdminEstadisticas newInstance() {
        return new AdminEstadisticas();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_estadisticas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminEstadisticasViewModel.class);
        // TODO: Use the ViewModel
    }

}