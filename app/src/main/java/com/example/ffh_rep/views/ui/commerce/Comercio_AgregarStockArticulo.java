package com.example.ffh_rep.views.ui.commerce;




import android.app.DatePickerDialog;
import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Articulo;

import com.example.ffh_rep.entidades.Stock;

import com.example.ffh_rep.models.repositories.StockRepository;
import com.example.ffh_rep.utils.GeneralHelper;

import java.sql.Date;


public class Comercio_AgregarStockArticulo extends Fragment {

    private EditText txtFechaVencimientoArticulo;
    private EditText txtCantidad;
    private Button btnAgregarStockArticulo;
    private Button btnVolver;
    private Articulo article;

    public static Comercio_AgregarStockArticulo newInstance() {
        return new Comercio_AgregarStockArticulo ();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio__agregar_stock_articulo, container, false);

        txtFechaVencimientoArticulo = view.findViewById(R.id.txtFechaVencimientoArticulo);
        txtCantidad = view.findViewById(R.id.txtCantidad);
        btnAgregarStockArticulo = view.findViewById(R.id.btnAgregarStockArticulo);
        btnVolver = view.findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("articuloSeleccionadoStock")){
                article = (Articulo) bundle.getSerializable("articuloSeleccionadoStock");
            }
        }

        setupListeners();


        btnAgregarStockArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fechaVencimientoText = txtFechaVencimientoArticulo.getText().toString().trim();
                String cantidadText = txtCantidad.getText().toString().trim();

                if (fechaVencimientoText.isEmpty() || cantidadText.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Stock stock = new Stock();
                stock.setId_comercio(article.getComercio());
                stock.setId_articulo(article);
                stock.setCantidad(Integer.valueOf(txtCantidad.getText().toString()));
                stock.setFecha_vencimiento(Date.valueOf(txtFechaVencimientoArticulo.getText().toString()));
                Log.d("fecha antes de mandar", stock.getFecha_vencimiento().toString());

                insertarStock(stock);

            }
        });

        return view;
    }

    public void redirectToStocks(){
        Navigation.findNavController(requireView()).navigate(R.id.action_comercio_AgregarStockArticulo_to_comercio_Ver_Stock_x_Articulo);
    }

    public void showDatePicker(){
        DatePickerDialog dpdialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                txtFechaVencimientoArticulo.setText(date);
            }
        }, 2023, 11, 3);

        dpdialog.show();
    }


    private void insertarStock(Stock stock) {
        StockRepository stockRepository = new StockRepository();
        Context context = requireContext();
        stockRepository.insertarNuevoStock(context, stock);
        clearFields();
        //redirectToStocks();
    }

    private void setupListeners() {

        txtFechaVencimientoArticulo.setOnClickListener(v-> showDatePicker());
        // Comente esto porque generaba que que al retroceder desde esta vista se triggeree el date picker 3 veces en una vista incorrecta
        // Deberiamos revisarlo.
        /*txtFechaVencimientoArticulo.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                showDatePicker();
            }
        });*/
    }

    public void goBack() {
        requireActivity().onBackPressed();
    }

    private void clearFields() {
        txtCantidad.setText("");
        txtFechaVencimientoArticulo.setText("");
    }



}

