package com.example.ffh_rep.ui.commerce;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.ffh_rep.utils.GeneralHelper;
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
    }

    public void setUpListeners() {
        btnAgregarDescuento.setOnClickListener(v-> addBeneficio());
        btnVolverMisDescuentos.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_agregarDescuento_to_fragmentAgregarDescuentoComercio));
    }

    private void addBeneficio(){
        /// OBTENGO EL COMERCIO PARA POSTERIORMENTE SACAR SU ID
        SessionManager session = new SessionManager(getContext());
        Comercio comercio = session.getCommerceSession();

        /// INSTANCIO UN OBJETO BENEFICIO Y SETEO LOS VALORES
        Beneficio beneficio = new Beneficio();
        beneficio.setId_comercio(comercio);
        beneficio.setDescripcion(txtDescripcion.getText().toString());
        beneficio.setPuntos_requeridos(Integer.parseInt(txtPuntos.getText().toString()));
        beneficio.setEstado(true);

        /// VERIFICO LOS INPUTS Y USO EL METODO PARA INSERTAR EL BENEFICIO
        if(validateInput()){
            mViewModel.insertarBeneficio(beneficio);
            txtDescripcion.setText("");
            txtPuntos.setText("");
        }
        else {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        boolean isValid = true;

        // Validar descripcion;
        if (txtDescripcion.getText().toString().isEmpty()) {
            txtDescripcion.setError("Este campo es requerido");
            isValid = false;
        }
        // Validar puntos
        if (txtPuntos.getText().toString().isEmpty()) {
            txtPuntos.setError("Este campo es requerido");
            isValid = false;
        } else if (!GeneralHelper.isNumeric(txtPuntos.getText().toString())) {
            txtPuntos.setError("El telefono debe ser numérico");
            isValid = false;
        } else if (Integer.parseInt(txtPuntos.getText().toString()) <= 0){
            txtPuntos.setError("Los puntos requeridos deben ser mayores a 0");
            isValid = false;
        }

        return isValid;
    }

}