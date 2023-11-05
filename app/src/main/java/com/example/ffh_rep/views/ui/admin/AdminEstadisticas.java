package com.example.ffh_rep.views.ui.admin;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentAdminEstadisticasBinding;
import com.example.ffh_rep.viewmodels.admin.AdminEstadisticasViewModel;
import com.example.ffh_rep.viewmodels.factory.AdminEstadisticasViewModelFactory;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AdminEstadisticas extends Fragment {

    private AdminEstadisticasViewModel mViewModel;
    private EditText et_desde, et_hasta;
    private Button btn_filtrar;
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
        btn_filtrar = view.findViewById(R.id.filtrar);
        //mViewModel = new ViewModelProvider(requireActivity(), new AdminEstadisticasViewModelFactory(getActivity())).get(AdminEstadisticasViewModel.class);
    }

    private void setupListeners() {
        et_desde.setOnClickListener(v -> showDatePickerDesde());
        et_hasta.setOnClickListener(v -> showDatePickerHasta());
        btn_filtrar.setOnClickListener(v -> filtrar());
    }

    private void filtrar() {
        String desde_string = et_desde.getText().toString();
        String hasta_string = et_hasta.getText().toString();

        if (!desde_string.isEmpty() && !hasta_string.isEmpty())
        {
            // Define un formato para las fechas
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd//MM/yyyy");

            try {
                // Convierte las cadenas de texto a objetos Date
                Date desde_date = dateFormat.parse(desde_string);
                Date hasta_date = dateFormat.parse(hasta_string);

                // Realiza la comparación de fechas
                if (desde_date.before(hasta_date) || desde_date.equals(hasta_date)) {
                    Toast.makeText(getActivity(), "Filtro exitoso", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Verifique el rango de fechas ingresado", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //RegistrarUsuario registrarUsuarioTask = new RegistrarUsuario(this, usuario, this);
            //registrarUsuarioTask.execute();
        }
        else
        {
            Toast.makeText(getActivity(), "Las fechas no pueden estar vacias", Toast.LENGTH_SHORT).show();
        }

    }

    public void showDatePickerDesde(){
        int minYear = 2000;
        int maxYear = 2023;
        int initialYear = 2023; // El año inicial seleccionado

        DatePickerDialog dpdialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                et_desde.setText(date);
            }
        }, initialYear, 0, 1);

        // Configura el rango máximo y minimo de años disponibles
        dpdialog.getDatePicker().setMinDate(getDateInMillis(minYear, 0, 1));
        dpdialog.getDatePicker().setMaxDate(getDateInMillis(maxYear, 11, 11));

        dpdialog.show();
    }

    private long getDateInMillis(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }


    public void showDatePickerHasta(){
        int minYear = 2000;
        int maxYear = 2023;
        int initialYear = 2023; // El año inicial seleccionado

        DatePickerDialog dpdialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                et_hasta.setText(date);
            }
        }, initialYear, 11, 11);

        // Configura el rango máximo y minimo de años disponibles
        dpdialog.getDatePicker().setMinDate(getDateInMillis(minYear, 0, 1));
        dpdialog.getDatePicker().setMaxDate(getDateInMillis(maxYear, 10, 11));

        dpdialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdminEstadisticasViewModel.class);
        // TODO: Use the ViewModel
    }

}