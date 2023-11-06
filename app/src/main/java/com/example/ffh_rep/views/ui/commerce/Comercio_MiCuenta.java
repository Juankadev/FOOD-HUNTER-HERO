package com.example.ffh_rep.views.ui.commerce;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentComercioMiCuentaBinding;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.viewmodels.factory.ComercioMiCuentaViewModelFactory;
import com.example.ffh_rep.utils.GeneralHelper;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.commerce.ComercioMiCuentaViewModel;

public class Comercio_MiCuenta extends Fragment {

    private ComercioMiCuentaViewModel mViewModel;
    private FragmentComercioMiCuentaBinding binding;
    private TextView et_cuit_mc, et_razonsocial_mc, et_rubro_mc, et_correo_mc, et_telefono_mc, et_direccion_mc, txtEditarActioner;
    private Button btnEditarInformacion, btnEliminarCuenta, btnEditAction, btnCancel;
    private CardView btnEditarActionerWithProgress;
    private ProgressBar pgBarEditar;
    private Comercio userSession, updateCommerce;
    private String originalCuit, originalRazonSocial, originalRubro, originalCorreo, originalTelefono, originalDireccion;
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
        et_cuit_mc = view.findViewById(R.id.et_cuit_mc);
        et_razonsocial_mc = view.findViewById(R.id.et_razonsocial);
        et_rubro_mc = view.findViewById(R.id.et_rubro);
        et_correo_mc = view.findViewById(R.id.et_email_c);
        et_telefono_mc = view.findViewById(R.id.et_telefono_mc);
        et_direccion_mc = view.findViewById(R.id.et_direccion_mc);

        btnEditarInformacion = view.findViewById(R.id.btn_editarInfoComercio);
        btnEliminarCuenta = view.findViewById(R.id.btn_eliminarInfoComercio);
        btnEditAction = view.findViewById(R.id.btn_edit_actioner2);
        btnCancel = view.findViewById(R.id.btn_Cancel_Edit2);
        btnEditarActionerWithProgress = view.findViewById(R.id.cv_actioner_edit);

        txtEditarActioner = view.findViewById(R.id.txtEditarActioner);
        pgBarEditar = view.findViewById(R.id.progressbarEditar);

        mViewModel = new ViewModelProvider(requireActivity(), new ComercioMiCuentaViewModelFactory(getActivity())).get(ComercioMiCuentaViewModel.class);
    }
    /**
     * Configura los elementos de entrada (EditText) con los datos del usuario.
     *
     * @param user Objeto Comercio que contiene la información del usuario.
     */
    private void settingInputs(Comercio user){
        et_cuit_mc.setText(user.getCuit());
        et_razonsocial_mc.setText(user.getRazonSocial());
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
        btnEliminarCuenta.setOnClickListener(v -> deleteMyAccount());
        btnEditarInformacion.setOnClickListener(v -> enabledInputs(true));
        btnEditarActionerWithProgress.setOnClickListener(v -> updateInformation());
        btnCancel.setOnClickListener(v-> enabledInputs(false));
    }
    /**
     * Configura los observadores de la interfaz.
     * Implementa las funciones correspondientes.
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

        mViewModel.getUpdatingInfo().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    pgBarEditar.setVisibility(View.VISIBLE);
                    txtEditarActioner.setText("Actualizando...");
                }
                else{
                    pgBarEditar.setVisibility(View.GONE);
                    txtEditarActioner.setText("Editar");
                }
            }
        });

        mViewModel.getSuccessUpdate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    updateMessage(true);
                }
            }
        });

        mViewModel.getErrorUpdate().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    updateMessage(false);
                }
            }
        });
    }
    /**
     * Actualiza la información del usuario con los valores ingresados en los campos de edición.
     * Crea un objeto Comercio con los datos actualizados y lo envía al ViewModel para su procesamiento.
     */
    private void updateInformation(){
        if(validateInput()){

            updateCommerce = new Comercio();

            updateCommerce.setId(this.userSession.getId());
            updateCommerce.setRubro(et_rubro_mc.getText().toString());
            updateCommerce.setEmail(et_correo_mc.getText().toString());
            updateCommerce.setDireccion(et_direccion_mc.getText().toString());
            updateCommerce.setTelefono(et_telefono_mc.toString());

            mViewModel.updateInformation(updateCommerce);
        }
        else{
            Toast.makeText(requireContext(), "Por favor, complete bien los campos", Toast.LENGTH_LONG);
        }
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
            btnEditarActionerWithProgress.setVisibility(View.VISIBLE);

            //btnEditAction.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        }
        else{
            rollbackEdit();
            btnEditarInformacion.setVisibility(View.VISIBLE);
            btnEditarActionerWithProgress.setVisibility(View.GONE);
            btnEliminarCuenta.setVisibility(View.VISIBLE);

            //btnEditAction.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }
    }
    /**
     * Guarda los valores originales de los campos de información del usuario antes de realizar
     * modificaciones. Esto permite revertir los cambios en caso de que se cancele la edición.
     */
    private void saveOriginals(){
        originalCuit = et_cuit_mc.getText().toString();
        originalRazonSocial = et_razonsocial_mc.getText().toString();
        originalRubro = et_rubro_mc.getText().toString();
        originalCorreo = et_correo_mc.getText().toString();
        originalDireccion = et_direccion_mc.getText().toString();
        originalTelefono = et_telefono_mc.getText().toString();
    }
    /**
     * Revierte la edición restaurando los valores originales a los campos de información del usuario.
     */
    private void rollbackEdit(){
        et_cuit_mc.setText(originalCuit);
        et_razonsocial_mc.setText(originalRazonSocial);
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

    private boolean validateInput() {
        boolean isValid = true;
        // Validar rubro
        String rubro = et_rubro_mc.getText().toString();
        if (rubro.isEmpty()) {
            et_rubro_mc.setError("Este campo es requerido");
            isValid = false;
        }
        // Validar telefono
        String telefono = et_telefono_mc.getText().toString();
        if (telefono.isEmpty()) {
            et_telefono_mc.setError("Este campo es requerido");
            isValid = false;
        }
        // Validar correo
        String correo = et_correo_mc.getText().toString();
        if (correo.isEmpty()) {
            et_correo_mc.setError("Este campo es requerido");
            isValid = false;
        } else if (!GeneralHelper.isValidEmailAddress(correo)) {
            et_correo_mc.setError("Correo electrónico inválido");
            isValid = false;
        }
        // Validar direccion
        String direccion = et_direccion_mc.getText().toString();
        if(direccion.isEmpty()){
            et_direccion_mc.setError("Este campo es requerido");
            isValid= false;
        }

        return isValid;
    }

    private void updateMessage(boolean value){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Actualizar información");
        if(value) {
            builder.setMessage("Se ha actualizado la informacion con exito!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mViewModel.setErrorUpdate(false);
                    mViewModel.setSuccessUpdate(false);
                    mViewModel.setUpdatingInfo(false);
                    enabledInputs(false);
                    mViewModel.setCommerceDataWithCommerce(updateCommerce);
                    sessionManager.saveCommerceSession(updateCommerce);
                    dialog.dismiss();
                }
            });
        }
        else {
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mViewModel.setErrorUpdate(false);
                    mViewModel.setSuccessUpdate(false);
                    mViewModel.setUpdatingInfo(false);
                    enabledInputs(false);
                    dialog.dismiss();
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
