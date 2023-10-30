package com.example.ffh_rep.ui.commerce;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioMiCuentaBinding;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.factory.ComercioMiCuentaViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

public class Comercio_MiCuenta  extends Fragment {

    private ComercioMiCuentaViewModel mViewModel;
    private FragmentComercioMiCuentaBinding binding;
    private TextView et_rubro_mc, et_correo_mc, et_telefono_mc, et_direccion_mc;
    private Button btnEditarInformacion, btnEliminarCuenta, btnEditAction, btnCancel, btnVolver;
    private Comercio userSession;
    private String originalRubro, originalCorreo, originalTelefono, originalDireccion;
    private SessionManager sessionManager;

    public static Comercio_MiCuenta newInstance() {
        return new Comercio_MiCuenta();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentComercioMiCuentaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initializeViews(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getCommerceSession();

        mViewModel.setCommerceDataWithCommerce(this.userSession);

        settingObservers();
        setupListeners();
        return view;
    }

    /**
     * Inicializa las vistas necesarias para la interfaz de registro.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    private void initializeViews(View view) {
        et_rubro_mc = view.findViewById(R.id.et_rubro_mc);
        et_correo_mc = view.findViewById(R.id.et_correo_mc);
        et_telefono_mc = view.findViewById(R.id.et_telefono_mc);
        et_direccion_mc = view.findViewById(R.id.et_direccion_mc);

        btnEditarInformacion = view.findViewById(R.id.btn_editarInfoComercio);
        btnEliminarCuenta = view.findViewById(R.id.btn_eliminarInfoComercio);
        btnEditAction = view.findViewById(R.id.btn_edit_actioner2);
        btnCancel = view.findViewById(R.id.btn_Cancel_Edit2);
        btnVolver = view.findViewById(R.id.btn_back_menu3);

        mViewModel = new ViewModelProvider(requireActivity(), new ComercioMiCuentaViewModelFactory(getActivity())).get(ComercioMiCuentaViewModel.class);
    }
    /**
     * Configura los elementos de entrada (EditText) con los datos del usuario.
     *
     * @param user Objeto Comercio que contiene la información del usuario.
     */
    private void settingInputs(Comercio user){
        et_rubro_mc.setText(user.getRubro());
        et_correo_mc.setText(user.getEmail());
        et_telefono_mc.setText(user.getTelefono());
        et_direccion_mc.setText(user.getDireccion());
    }
    /**
     * Configura los listeners de la interfaz.
     * Asigna los métodos correspondientes a los eventos.
     */
    private void setupListeners() {
        btnEditarInformacion.setOnClickListener(v -> enabledInputs(true));
        btnEliminarCuenta.setOnClickListener(v -> deleteMyAccount());
        btnEditAction.setOnClickListener(v -> updateInformation());
        btnCancel.setOnClickListener(v-> enabledInputs(false));
        ///btnVolver.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.pantalla));
    }
    /**
     * Configura los observadores de la interfaz.
     * Implementa las unciones correspondientes.
     */
    private void settingObservers(){
        mViewModel.getCommerceData().observe(getViewLifecycleOwner(), new Observer<Comercio>() {
            @Override
            public void onChanged(Comercio comercio) {
                settingInputs(comercio);
            }
        });

        mViewModel.getDeleteAccount().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(requireContext(), "Cuenta eliminada con exito!", Toast.LENGTH_SHORT).show();
                    closeSession();
                }
            }
        });
    }
    /**
     * Actualiza la información del usuario con los valores ingresados en los campos de edición.
     * Crea un objeto Comercio con los datos actualizados y lo envía al ViewModel para su procesamiento.
     */
    private void updateInformation(){
        Comercio updateCommerce = new Comercio();

        updateCommerce.setId(this.userSession.getId());
        updateCommerce.setRubro(et_rubro_mc.getText().toString());
        updateCommerce.setEmail(et_correo_mc.getText().toString());
        updateCommerce.setDireccion(et_direccion_mc.getText().toString());
        updateCommerce.setTelefono(et_telefono_mc.toString());

        mViewModel.updateInformation(updateCommerce);
    }

    /**
     * Habilita o deshabilita la edición de los campos de información del usuario y ajusta la visibilidad
     * de los botones según el estado proporcionado. Si '_var' es verdadero, habilita la edición, oculta
     * los botones de editar y eliminar, y muestra los botones de confirmar y cancelar. Si '_var' es falso,
     * deshabilita la edición, revierte los cambios y muestra los botones de editar y eliminar.
     */
    private void enabledInputs(boolean _var){
        et_rubro_mc.setEnabled(_var);
        et_correo_mc.setEnabled(_var);
        et_direccion_mc.setEnabled(_var);
        et_telefono_mc.setEnabled(_var);

        if(_var){
            saveOriginals();
            btnEditarInformacion.setVisibility(View.GONE);
            btnEliminarCuenta.setVisibility(View.GONE);

            btnEditAction.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        }
        else{
            rollbackEdit();
            btnEditarInformacion.setVisibility(View.VISIBLE);
            btnEliminarCuenta.setVisibility(View.VISIBLE);

            btnEditAction.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }
    }
    /**
     * Guarda los valores originales de los campos de información del usuario antes de realizar
     * modificaciones. Esto permite revertir los cambios en caso de que se cancele la edición.
     */
    private void saveOriginals(){
        originalRubro = et_rubro_mc.getText().toString();
        originalCorreo = et_correo_mc.getText().toString();
        originalDireccion = et_direccion_mc.getText().toString();
        originalTelefono = et_telefono_mc.getText().toString();
    }
    /**
     * Revierte la edición restaurando los valores originales a los campos de información del usuario.
     */
    private void rollbackEdit(){
        et_rubro_mc.setText(originalRubro);
        et_correo_mc.setText(originalCorreo);
        et_direccion_mc.setText(originalDireccion);
        et_telefono_mc.setText(originalTelefono);
    }
    /**
     * Muestra un cuadro de diálogo de confirmación para eliminar la cuenta del usuario.
     * Si el usuario confirma, se invoca el método para eliminar la cuenta a través del ViewModel.
     */
    private void deleteMyAccount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Eliminar cuenta");
        builder.setMessage("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Elimina la cuenta aquí
                mViewModel.eliminarMiCuenta();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void closeSession() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
