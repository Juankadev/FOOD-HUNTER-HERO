package com.example.ffh_rep.ui.commerce;

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

public class AgregarDescuento extends Fragment {

    private EditText txtDescripcion, txtPuntos;
    private Button btnAgregarDescuento, btnVolverMisDescuentos;
    Context context = requireContext();
    DescuentoRepository descuentoRepository = new DescuentoRepository();

    public static AgregarDescuento newInstance() {
        return new AgregarDescuento();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio_agregardescuento, container, false);

        txtDescripcion = view.findViewById(R.id.edtDesc);
        txtPuntos = view.findViewById(R.id.edtPuntos);

        btnAgregarDescuento = view.findViewById(R.id.btnAgregarDescuento);
        btnVolverMisDescuentos = view.findViewById(R.id.btnVolver);

        btnAgregarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descripcion = txtDescripcion.getText().toString();
                int puntos = Integer.parseInt(txtPuntos.getText().toString());

                ///Falta setearle el ID de comercio
                Beneficio beneficio = new Beneficio();
                beneficio.setDescripcion(descripcion);
                beneficio.setPuntos_requeridos(puntos);

                descuentoRepository.agregarDescuento(context, beneficio);
            }
        });

        return view;
    }

}