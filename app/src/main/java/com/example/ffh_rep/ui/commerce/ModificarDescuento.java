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

public class ModificarDescuento extends Fragment {

    private EditText txtDescripcion, txtPuntos;
    private Button btnModificarDescuento, btnVolverMisDescuentos;
    Context context = requireContext();
    DescuentoRepository descuentoRepository = new DescuentoRepository();

    public static AgregarDescuento newInstance() {
        return new AgregarDescuento();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_descuento, container, false);

        txtDescripcion = view.findViewById(R.id.edtModificarDescripcionDescuento);
        txtPuntos = view.findViewById(R.id.edtModificarPrecioDescuento);

        btnModificarDescuento = view.findViewById(R.id.btnModificarDescuentoOK);
        btnVolverMisDescuentos = view.findViewById(R.id.btnVolverMisDescuentosDesdeModificar);

        btnModificarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion = txtDescripcion.getText().toString();
                int puntos = Integer.parseInt(txtPuntos.getText().toString());

                ///Falta setearle el ID de comercio y el ID del descuento
                Beneficio beneficio = new Beneficio();
                beneficio.setDescripcion(descripcion);
                beneficio.setPuntos_requeridos(puntos);

                descuentoRepository.modificarDescuento(context, beneficio);
            }
        });

        return view;
    }

}