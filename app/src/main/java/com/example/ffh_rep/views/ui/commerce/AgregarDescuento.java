package com.example.ffh_rep.views.ui.commerce;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.commerce.MisDescuentosComercioViewModel;
import com.example.ffh_rep.viewmodels.factory.DescuentosViewModelFactory;

public class AgregarDescuento extends Fragment {
    private MisDescuentosComercioViewModel mViewModel;
    private EditText txtDescripcion, txtPuntos;
    private Button btnAgregarDescuento, btnVolverMisDescuentos;

    public static AgregarDescuento newInstance() {
        return new AgregarDescuento();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio_agregar_descuento, container, false);

        initComponentes(view);
        setUpListeners();
        mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(MisDescuentosComercioViewModel.class);

        return view;
    }

    public void initComponentes(View view){
        txtDescripcion = view.findViewById(R.id.edtDesc);
        txtPuntos = view.findViewById(R.id.edtPuntos);
        btnAgregarDescuento = view.findViewById(R.id.btnAgregarDescuento);
        btnVolverMisDescuentos = view.findViewById(R.id.btnVolver);
    }

    public void setUpListeners() {
        btnVolverMisDescuentos.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_agregarDescuento_to_fragmentAgregarDescuentoComercio));
        btnAgregarDescuento.setOnClickListener(v-> addBeneficio());
    }

    private void addBeneficio() {
        /// OBTENGO EL COMERCIO PARA POSTERIORMENTE SACAR SU ID
        SessionManager session = new SessionManager(getContext());
        Comercio comercio = session.getCommerceSession();

        if (!txtPuntos.getText().toString().isEmpty() && !txtDescripcion.getText().toString().isEmpty()) {
            // Verificar que txtPuntos contenga un número mayor a 0
            int puntos = Integer.parseInt(txtPuntos.getText().toString());
            if (puntos > 0) {
                // INSTANCIO UN OBJETO BENEFICIO Y SETEO LOS VALORES
                Beneficio beneficio = new Beneficio();
                beneficio.setId_comercio(comercio);
                beneficio.setDescripcion(txtDescripcion.getText().toString());
                beneficio.setPuntos_requeridos(puntos);
                beneficio.setEstado(true);

                mViewModel.insertarBeneficio(beneficio);
                txtDescripcion.setText("");
                txtPuntos.setText("");
            } else {
                Toast.makeText(getContext(), "Por favor, ingrese un número mayor a 0 en el campo de puntos requeridos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}