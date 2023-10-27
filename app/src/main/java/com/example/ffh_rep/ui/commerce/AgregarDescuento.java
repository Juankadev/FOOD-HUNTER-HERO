package com.example.ffh_rep.ui.commerce;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioAgregardescuentoBinding;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.factory.DescuentosViewModelFactory;

public class AgregarDescuento extends Fragment {
    private FragmentComercioAgregardescuentoBinding binding;
    private EditText txtDescripcion, txtPuntos;
    private Button btnAgregarDescuento, btnVolverMisDescuentos;
    private MisDescuentosComercioViewModel mViewModel;

    public static AgregarDescuento newInstance() {
        return new AgregarDescuento();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentComercioAgregardescuentoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initComponentes(view);
        setUpListeners();
        return view;
    }

    public void initComponentes(View view){
        txtDescripcion = view.findViewById(R.id.edtDesc);
        txtPuntos = view.findViewById(R.id.edtPuntos);

        btnAgregarDescuento = view.findViewById(R.id.btnAgregarDescuento);
        btnVolverMisDescuentos = view.findViewById(R.id.btnVolver);

        mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(MisDescuentosComercioViewModel.class);

    }

    public void setUpListeners() {
        btnAgregarDescuento.setOnClickListener(v-> addBeneficio());

        btnVolverMisDescuentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AgregarDescuento.this).navigate(R.id.fragmentAgregarDescuentoComercio);
            }
        });

    }

    private void addBeneficio(){
        String descripcion = txtDescripcion.getText().toString();
        int puntos = Integer.parseInt(txtPuntos.getText().toString());
        ///Falta enviarle ID del comercio en donde se agrega el descuento
        Beneficio beneficio = new Beneficio();
        beneficio.setDescripcion(descripcion);
        beneficio.setPuntos_requeridos(puntos);
        mViewModel.insertarBeneficio(beneficio);

        txtDescripcion.setText("");
        txtPuntos.setText("");
    }

}