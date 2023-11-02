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
import android.widget.Toast;

import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioAgregardescuentoBinding;
import com.example.ffh_rep.databinding.FragmentModificarDescuentoBinding;
import com.example.ffh_rep.entidades.Beneficio;
import com.example.ffh_rep.factory.DescuentosViewModelFactory;
import com.example.ffh_rep.repositories.DescuentoRepository;
import com.example.ffh_rep.ui.hunter.Hunter_Home;
import com.example.ffh_rep.utils.GeneralHelper;

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
    }

    private void setupListeners() {
        btnModificarDescuento.setOnClickListener((v-> modBeneficio()));
        btnVolverMisDescuentos.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_modificarDescuento_to_fragmentAgregarDescuentoComercio));
    }

    private void modBeneficio(){
        // OBTENGO DESCRIPCION Y PUNTOS DE LOS TXT
        String descripcion = txtDescripcion.getText().toString();
        int puntos = Integer.parseInt(txtPuntos.getText().toString());

        /// INSTANCIO UN OBJETO BENEFICIO Y SETEO LOS VALORES
        Beneficio beneficio = new Beneficio();
        beneficio.setDescripcion(descripcion);
        beneficio.setPuntos_requeridos(puntos);
        ///Falta enviarle el ID del descuento a modificar

        /// VERIFICO LOS INPUTS Y USO EL METODO PARA INSERTAR EL BENEFICIO
        if(validateInput()){
            mViewModel.editarBeneficio(beneficio);
            /// VACIO LOS TXT
            txtDescripcion.setText("");
            txtPuntos.setText("");
        }
        else {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        boolean isValid = true;
        // Validar descripcion
        String desc = txtDescripcion.getText().toString();
        if (desc.isEmpty()) {
            txtDescripcion.setError("Este campo es requerido");
            isValid = false;
        }
        // Validar puntos
        String puntos = txtPuntos.getText().toString();
        if (puntos.isEmpty()) {
            txtPuntos.setError("Este campo es requerido");
            isValid = false;
        } else if (!GeneralHelper.isNumeric(puntos)) {
            txtPuntos.setError("El telefono debe ser numérico");
            isValid = false;
        } else if (Integer.parseInt(puntos) <= 0){
            txtPuntos.setError("Los puntos requeridos deben ser mayores a 0");
            isValid = false;
        }

        return isValid;
    }

}