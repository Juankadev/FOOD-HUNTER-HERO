package com.example.ffh_rep.views.ui.admin;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentAdminEstadisticasBinding;
import com.example.ffh_rep.databinding.FragmentAprobarComerciosListBinding;
import com.example.ffh_rep.viewmodels.admin.AdminAprobarComerciosViewModel;
import com.example.ffh_rep.viewmodels.admin.AdminEstadisticasViewModel;
import com.example.ffh_rep.viewmodels.factory.AprobarComerciosViewModelFactory;
import com.example.ffh_rep.views.adapters.AprobarComerciosViewAdapter;

import java.util.ArrayList;
import java.util.Date;

public class AdminEstadisticas extends Fragment {

    private AdminEstadisticasViewModel mViewModel;
    private EditText et_desde, et_hasta;
    private FragmentAdminEstadisticasBinding binding;

    public static AdminEstadisticas newInstance() {
        return new AdminEstadisticas();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminEstadisticasBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initComponents(view);
        setupListeners();
        return view;
    }

    public void initComponents(View view){
        et_desde = view.findViewById(R.id.et_desde);
        et_hasta = view.findViewById(R.id.et_hasta);
        //mViewModel = new ViewModelProvider(requireActivity(), new AprobarComerciosViewModelFactory(getActivity())).get(AdminAprobarComerciosViewModel.class);
    }

    private void setupListeners() {
        et_desde.setOnClickListener(v -> showDatePickerDesde());
        et_hasta.setOnClickListener(v -> showDatePickerHasta());
    }

    public void showDatePickerDesde(){
        DatePickerDialog dpdialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                et_desde.setText(date);
            }
        }, 1990, 0, 1);

        dpdialog.show();
    }


    public void showDatePickerHasta(){
        DatePickerDialog dpdialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                et_hasta.setText(date);
            }
        }, 2023, 11, 11);

        dpdialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminEstadisticasViewModel.class);
        // TODO: Use the ViewModel
    }

}