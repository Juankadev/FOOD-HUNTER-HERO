package com.example.ffh_rep.ui.commerce;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioAgregardescuentoBinding;
import com.example.ffh_rep.databinding.FragmentModificarDescuentoBinding;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.factory.DescuentosViewModelFactory;
import com.example.ffh_rep.repositories.DescuentoRepository;
import com.example.ffh_rep.ui.hunter.Hunter_Home;

public class ModificarDescuento extends Fragment {
    private MisDescuentosComercioViewModel mViewModel;
    private EditText txtDescripcion, txtPuntos;
    private Button btnModificarDescuento, btnVolverMisDescuentos;

    public static ModificarDescuento newInstance() {
        return new ModificarDescuento();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_descuento, container, false);

        initComponentes(view);
        setupListeners();

        return view;
    }

    public void initComponentes(View view){
        txtDescripcion = view.findViewById(R.id.edtModificarDescripcionDescuento);
        txtPuntos = view.findViewById(R.id.edtModificarPrecioDescuento);
        btnModificarDescuento = view.findViewById(R.id.btnModificarDescuentoOK);
        btnVolverMisDescuentos = view.findViewById(R.id.btnVolverMisDescuentosDesdeModificar);
        mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(MisDescuentosComercioViewModel.class);
    }

    private void setupListeners() {
        btnModificarDescuento.setOnClickListener((v-> modBeneficio()));
        btnVolverMisDescuentos.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.commerce_MisArticulos));
    }

    private void modBeneficio(){
        // OBTENGO DESCRIPCION Y PUNTOS DE LOS TXT
        String descripcion = txtDescripcion.getText().toString();
        int puntos = Integer.parseInt(txtPuntos.getText().toString());

        /// INSTANCIO UN OBJETO BENEFICIO Y SETEO LOS VALORES
        ///Falta enviarle el ID del descuento a modificar
        Beneficio beneficio = new Beneficio();
        beneficio.setDescripcion(descripcion);
        beneficio.setPuntos_requeridos(puntos);

        /// USO EL METODO PARA INSERTAR EL BENEFICIO
        mViewModel.editarBeneficio(beneficio);

        /// VACIO LOS TXT
        txtDescripcion.setText("");
        txtPuntos.setText("");
    }

}