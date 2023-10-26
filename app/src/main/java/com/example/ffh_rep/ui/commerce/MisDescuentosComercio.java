package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.factory.CarritoViewModelFactory;
import com.example.ffh_rep.repositories.DescuentoRepository;
import com.example.ffh_rep.ui.hunter.CarritoViewModel;

public class MisDescuentosComercio extends Fragment {

    private EditText shopName, descDescuento, puntosDescuento, idDescuento;
    private Button btnMisArticulos, btnEliminarDescuento, btnModificarDescuento;
    DescuentoRepository descuentoRepository = new DescuentoRepository();

    Context context = requireContext();
    public static MisDescuentosComercio newInstance() {
        return new MisDescuentosComercio();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio_mis_descuentos, container, false);

        initComponentes(view);

        btnEliminarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = Integer.parseInt(idDescuento.getText().toString());

                Beneficio beneficio = new Beneficio();
                beneficio.setId_beneficio(id);

                descuentoRepository.eliminarDescuento(context, beneficio);
            }
        });

        btnModificarDescuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MisDescuentosComercio.this).navigate(R.id.modificarDescuento);
                // ENVIARLE EL ID DEL DESCUENTO A MODIFICAR
            }
        });

      /*  btnMisArticulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MisDescuentosComercio.this).navigate(R.id.fragment_comercio_articulos);
            }
        });*/

        return view;
    }

    public void initComponentes(View view){
        shopName = view.findViewById(R.id.et_ShopName);
        idDescuento = view.findViewById(R.id.IdDescuento);
        descDescuento = view.findViewById(R.id.etDescDescuento);
        puntosDescuento = view.findViewById(R.id.etPuntosReq);

        btnMisArticulos = view.findViewById(R.id.btnMisArticulos);
        btnEliminarDescuento = view.findViewById(R.id.btnEliminarDescuento);
        btnModificarDescuento = view.findViewById(R.id.btnModificarDescuento);
    }

}