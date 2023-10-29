package com.example.ffh_rep.ui.commerce;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioAgregardescuentoBinding;
import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.factory.DescuentosViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

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
        View view = inflater.inflate(R.layout.fragment_comercio_agregardescuento, container, false);

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
        btnVolverMisDescuentos.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.commerce_MisArticulos));
    }

    private void addBeneficio(){
        // OBTENGO DESCRIPCION Y PUNTOS DE LOS TXT
        String descripcion = txtDescripcion.getText().toString();
        int puntos = Integer.parseInt(txtPuntos.getText().toString());

        /// OBTENGO EL COMERCIO PARA POSTERIORMENTE SACAR SU ID
        Comercio comercio;
        SessionManager session = new SessionManager(getContext());
        comercio = session.getCommerceSession();

        /// INSTANCIO UN OBJETO BENEFICIO Y SETEO LOS VALORES
        Beneficio beneficio = new Beneficio();
        beneficio.setDescripcion(descripcion);
        beneficio.setPuntos_requeridos(puntos);
        beneficio.setId_comercio(comercio);

        /// USO EL METODO PARA INSERTAR EL BENEFICIO
        mViewModel.insertarBeneficio(beneficio);

        /// VACIO LOS TXT
        txtDescripcion.setText("");
        txtPuntos.setText("");
    }

}