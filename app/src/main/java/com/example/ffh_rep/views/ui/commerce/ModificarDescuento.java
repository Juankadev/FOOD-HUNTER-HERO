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
import com.example.ffh_rep.viewmodels.commerce.MisDescuentosComercioViewModel;
import com.example.ffh_rep.viewmodels.factory.DescuentosViewModelFactory;

public class ModificarDescuento extends Fragment {
    private MisDescuentosComercioViewModel mViewModel;
    private EditText txtDescripcion, txtPuntos;
    private Button btnModificarDescuento, btnVolverMisDescuentos;
    private Beneficio b;
    private  Bundle bundle;

    public static ModificarDescuento newInstance() {
        return new ModificarDescuento();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comercio_modificar_descuento, container, false);

        /**
         * Este método se utiliza para obtener el Bundle que se adjuntó a este fragmento cuando fue creado.
         * El Bundle es una forma de pasar datos entre fragmentos.
         */
        bundle = getArguments();
        /**
         * El resultado de bundle.getSerializable("idBeneficioSelect") se almacena en la variable b.
         * Aquí, se está realizando un casting para asegurarse de que el objeto obtenido sea de tipo Beneficio.
         */
        b = (Beneficio) bundle.getSerializable("idBeneficioSelect");

        initComponentes(view);
        setupListeners();
        fillInputs();

        mViewModel = new ViewModelProvider(requireActivity(), new DescuentosViewModelFactory(getActivity())).get(MisDescuentosComercioViewModel.class);

        return view;
    }

    public void initComponentes(View view){
        txtDescripcion = view.findViewById(R.id.edtModificarDescripcionDescuento);
        txtPuntos = view.findViewById(R.id.edtModificarPrecioDescuento);
        btnModificarDescuento = view.findViewById(R.id.btnModificarDescuentoOK);
        btnVolverMisDescuentos = view.findViewById(R.id.btnVolverMisDescuentosDesdeModificar);
    }

    public void fillInputs(){
        txtDescripcion.setText(b.getDescripcion().toString());
        txtPuntos.setText(b.getPuntos_requeridos().toString());
    }

    private void setupListeners() {
        btnModificarDescuento.setOnClickListener((v-> modBeneficio()));
        btnVolverMisDescuentos.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_modificarDescuento_to_fragmentAgregarDescuentoComercio));
    }

    private void modBeneficio(){

        if (!txtPuntos.getText().toString().isEmpty() && !txtDescripcion.getText().toString().isEmpty()) {
            // Verificar que txtPuntos contenga un número mayor a 0
            int puntos = Integer.parseInt(txtPuntos.getText().toString());
            if (puntos > 0) {
                // INSTANCIO UN OBJETO BENEFICIO Y SETEO LOS VALORES
                Beneficio beneficio = new Beneficio();

                beneficio.setDescripcion(txtDescripcion.getText().toString());
                beneficio.setPuntos_requeridos(puntos);
                beneficio.setId_beneficio(b.getId_beneficio());

                mViewModel.editarBeneficio(beneficio);

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