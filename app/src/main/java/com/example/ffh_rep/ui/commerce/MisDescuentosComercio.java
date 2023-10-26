package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.repositories.DescuentoRepository;

public class MisDescuentosComercio extends Fragment {

    private EditText shopName, descDescuento, puntosDescuento;
    private Button btnMisArticulos, btn_MisDescuentos, btnEliminarDescuento, btnModificarDescuento;
    DescuentoRepository descuentoRepository = new DescuentoRepository();

    Context context = requireContext();
    public static MisDescuentosComercio newInstance() {
        return new MisDescuentosComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio_mis_descuentos, container, false);

        shopName = view.findViewById(R.id.et_ShopName);
        descDescuento = view.findViewById(R.id.etDescDescuento);
        puntosDescuento = view.findViewById(R.id.etPuntosReq);

        btnMisArticulos = view.findViewById(R.id.btnMisArticulos);
        btn_MisDescuentos = view.findViewById(R.id.btn_MisDescuentos);
        btnEliminarDescuento = view.findViewById(R.id.btnEliminarDescuento);
        btnModificarDescuento = view.findViewById(R.id.btnModificarDescuento);

        btnEliminarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NombreComercio = shopName.getText().toString();
                String descripcion = descDescuento.getText().toString();

                Beneficio beneficio = new Beneficio();
                beneficio.getId_comercio().setRazonSocial(NombreComercio);
                beneficio.setDescripcion(descripcion);

                descuentoRepository.eliminarDescuento(context, beneficio);
            }
        });

        return view;
    }

}