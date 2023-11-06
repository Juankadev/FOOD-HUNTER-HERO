package com.example.ffh_rep.views.ui.admin;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentAdminEstadisticasBinding;
import com.example.ffh_rep.viewmodels.admin.AdminEstadisticasViewModel;
import com.example.ffh_rep.viewmodels.factory.AdminEstadisticasViewModelFactory;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AdminEstadisticas extends Fragment {

    private AdminEstadisticasViewModel mViewModel;
    private EditText et_desde, et_hasta;
    private Button btn_filtrar;
    private PieChart pie_chart;
    private TextView result_cantidad_compras, result_articulo_mayor_cazas, result_cazadores_rango_maximo, result_cazadores_rango_minimo, result_comercios_aprobados, result_productos_cazados;
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
        pie_chart = view.findViewById(R.id.pie_chart);
        result_cazadores_rango_maximo = view.findViewById(R.id.result_cazadores_rango_maximo);
        result_cazadores_rango_minimo = view.findViewById(R.id.result_cazadores_rango_minimo);
        result_productos_cazados = view.findViewById(R.id.result_productos_cazados);
        result_comercios_aprobados = view.findViewById(R.id.result_comercios_aprobados);
        result_articulo_mayor_cazas = view.findViewById(R.id.result_articulo_mayor_cazas);
        result_cantidad_compras = view.findViewById(R.id.result_cantidad_compras);
        mViewModel = new ViewModelProvider(requireActivity(), new AdminEstadisticasViewModelFactory(getActivity())).get(AdminEstadisticasViewModel.class);
    }

    private void setupListeners() {
        et_desde.setOnClickListener(v -> showDatePickerDesde());
        et_hasta.setOnClickListener(v -> showDatePickerHasta());
        btn_filtrar.setOnClickListener(v -> filtrar());
    }

    private boolean validarFechas(String desde, String hasta){
        if (!desde.isEmpty() && !hasta.isEmpty())
        {
            // Define un formato para las fechas
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            //COMPARAR FECHAS
            try {
                // Convierte las cadenas de texto a objetos Date
                Date desde_date = dateFormat.parse(desde);
                Date hasta_date = dateFormat.parse(hasta);

                // Realiza la comparación de fechas
                if (desde_date.before(hasta_date) || desde_date.equals(hasta_date)) {
                    Toast.makeText(getActivity(), "Procesando...", Toast.LENGTH_LONG).show();
                    return true;
                }
                else{
                    Toast.makeText(getActivity(), "Verifique el rango de fechas ingresado", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (ParseException e) {
                Toast.makeText(getActivity(), "Ha ocurrido un error interno", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Las fechas no pueden estar vacias", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void filtrar() {
        String desde = et_desde.getText().toString();
        String hasta = et_hasta.getText().toString();
        try {
            if(validarFechas(desde, hasta)){
                //formatear fechas a formato phpmyadmin
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date_desde = inputFormat.parse(desde);
                Date date_hasta = inputFormat.parse(hasta);
                desde = outputFormat.format(date_desde);
                hasta = outputFormat.format(date_hasta);

                setEstadisticas(desde,hasta);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Obtener estadisticas de la db y setear textviews
    private void setEstadisticas(String desde, String hasta){
        String[] results = mViewModel.getEstadisticas(desde,hasta);
        result_cazadores_rango_maximo.setText(results[0]);
        result_cazadores_rango_minimo.setText(results[1]);
        result_productos_cazados.setText(results[2]);
        result_comercios_aprobados.setText(results[3]);
        result_articulo_mayor_cazas.setText(results[4]);
        result_cantidad_compras.setText(results[5]);

        setPieChart(desde,hasta);
    }

    private void setPieChart(String desde, String hasta){
        Map<String,Integer> categorias = mViewModel.getCategoriasMasCazadas(desde,hasta);

        ArrayList<Integer> colores = new ArrayList<>();
        colores.add(Color.parseColor("#FFA726"));
        colores.add(Color.parseColor("#66BB6A"));
        colores.add(Color.parseColor("#2986F6"));

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for(String categoria: categorias.keySet()){
            pieEntries.add(new PieEntry(categorias.get(categoria).intValue(), categoria));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"- CATEGORIAS");
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(colores);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);

        pie_chart.getDescription().setEnabled(false);
        pie_chart.setNoDataText("No hay datos disponibles");
        pie_chart.setNoDataTextColor(Color.BLACK);
        pie_chart.setData(pieData);
        pie_chart.invalidate();
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
        //los set piden una fecha en milisegundos
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
        // TODO: Use the ViewModel
    }

}