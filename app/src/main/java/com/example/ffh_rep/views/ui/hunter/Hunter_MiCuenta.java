package com.example.ffh_rep.views.ui.hunter;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterMiCuentaBinding;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.viewmodels.factory.HunterMiCuentaViewModelFactory;
import com.example.ffh_rep.utils.GeneralHelper;
import com.example.ffh_rep.utils.SessionManager;
import com.example.ffh_rep.viewmodels.hunter.HunterMiCuentaViewModel;

public class Hunter_MiCuenta extends Fragment {

    private HunterMiCuentaViewModel mViewModel;
    private FragmentHunterMiCuentaBinding binding;
    private TextView et_nombre, et_apellido, et_fechanacimiento, et_telefono, et_correo, et_direccion, txtEditarActioner, txtPuntos, txtRango;
    private Spinner spinnerSexo;
    private Button btnEditarInformacion, btnEliminarCuenta, btnEditAction, btnCancel;
    private CardView btnEditarActionerWithProgress;
    private ProgressBar pgBarEditar;
    private Hunter userSession, updateHunter;
    private String originalNombre, originalApellido, originalFechaNacimiento, originalTelefono, originalSexo, originalCorreo, originalDireccion;
    private SessionManager sessionManager;


    public static Hunter_MiCuenta newInstance() {
        return new Hunter_MiCuenta();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHunterMiCuentaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initializeViews(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();

        mViewModel.setHunterDataWithHunte(this.userSession);

        settingObservers();
        setupListeners();
        return view;
    }
    /**
     * Inicializa las vistas necesarias para la interfaz de registro.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    private void initializeViews(View view) {
        et_apellido = view.findViewById(R.id.et_apellido);
        et_nombre = view.findViewById(R.id.et_nombre);
        et_fechanacimiento = view.findViewById(R.id.et_fechanacimiento);
        spinnerSexo = view.findViewById(R.id.spinner_sexo);
        et_correo = view.findViewById(R.id.et_correo);
        et_direccion = view.findViewById(R.id.et_direccion);
        et_telefono = view.findViewById(R.id.et_telefono);
        txtPuntos = view.findViewById(R.id.txtMisPuntos);
        txtRango = view.findViewById(R.id.txtRango);
        btnEditarInformacion = view.findViewById(R.id.btn_editInformacion);
        btnEliminarCuenta = view.findViewById(R.id.btn_deleteaccount);
        btnEditAction = view.findViewById(R.id.btn_edit_actioner);
        btnCancel = view.findViewById(R.id.btn_Cancel_Edit);
        btnEditarActionerWithProgress = view.findViewById(R.id.cv_actioner_edit);

        txtEditarActioner = view.findViewById(R.id.txtEditarActioner);
        pgBarEditar = view.findViewById(R.id.progressbarEditar);
        setupSpinner();
        mViewModel = new ViewModelProvider(requireActivity(), new HunterMiCuentaViewModelFactory(getActivity())).get(HunterMiCuentaViewModel.class);
    }
    /**
     * Configura el Spinner de género con las opciones predefinidas.
     * Crea un ArrayAdapter con las opciones de género y lo asigna al Spinner.
     */
    private void setupSpinner() {
        String[] opcionesSexo = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, opcionesSexo) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);
        spinnerSexo.setEnabled(false);
    }
    /**
     * Configura los elementos de entrada (EditText y Spinner) con los datos del usuario.
     *
     * @param user Objeto Hunter que contiene la información del usuario.
     */
    private void settingInputs(Hunter user){
        et_apellido.setText(user.getApellido());
        et_nombre.setText(user.getNombre());
        et_fechanacimiento.setText(user.getFecha_nacimiento().toString());
        et_correo.setText(user.getCorreo_electronico());
        et_telefono.setText(user.getTelefono());
        et_direccion.setText(user.getDireccion());
        txtPuntos.setText(String.valueOf(user.getPuntaje()));
        txtRango.setText(user.getId_rango().getDescripcion());
        String sexo = user.getSexo();
        if ("Masculino".equals(sexo)) {
            spinnerSexo.setSelection(0);
        } else if ("Femenino".equals(sexo)) {
            spinnerSexo.setSelection(1);
        } else if ("Otro".equals(sexo)) {
            spinnerSexo.setSelection(2);
        }
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
        et_fechanacimiento.setOnClickListener(v-> showDatePicker());
        et_fechanacimiento.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                showDatePicker();
            }
        });
    }
    /**
     * Configura los observadores de la interfaz.
     * Implementa las funciones correspondientes.
     */
    private void settingObservers(){
        mViewModel.getHunterData().observe(getViewLifecycleOwner(), new Observer<Hunter>() {
            @Override
            public void onChanged(Hunter hunter) {
                settingInputs(hunter);
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
     * Crea un objeto Hunter con los datos actualizados y lo envía al ViewModel para su procesamiento.
     */
    private void updateInformation(){

        if(validateInput()){

            updateHunter = new Hunter();

            updateHunter.setIdHunter(this.userSession.getIdHunter());
            updateHunter.setApellido(et_apellido.getText().toString());
            updateHunter.setNombre(et_nombre.getText().toString());

            updateHunter.setFecha_nacimiento(GeneralHelper.returnSQLFORMAT2(et_fechanacimiento.getText().toString()));

            updateHunter.setSexo(spinnerSexo.getSelectedItem().toString());
            updateHunter.setTelefono(et_telefono.getText().toString());
            updateHunter.setCorreo_electronico(et_correo.getText().toString());
            updateHunter.setDireccion(et_direccion.getText().toString());
            Log.d("fecha antes de mandar", updateHunter.getFecha_nacimiento().toString());

            mViewModel.updateInformation(updateHunter);
        }
        else{
            Toast.makeText(requireContext(), "Por favor, completa bien los campos", Toast.LENGTH_LONG);
        }
    }
    /**
     * Habilita o deshabilita la edición de los campos de información del usuario y ajusta la visibilidad
     * de los botones según el estado proporcionado. Si '_var' es verdadero, habilita la edición, oculta
     * los botones de editar y eliminar, y muestra los botones de confirmar y cancelar. Si '_var' es falso,
     * deshabilita la edición, revierte los cambios y muestra los botones de editar y eliminar.
     */
    private void enabledInputs(boolean _var){
        et_apellido.setEnabled(_var);
        et_nombre.setEnabled(_var);
        spinnerSexo.setEnabled(_var);
        et_correo.setEnabled(_var);
        et_direccion.setEnabled(_var);
        et_fechanacimiento.setEnabled(_var);
        et_telefono.setEnabled(_var);

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
        originalNombre = et_nombre.getText().toString();
        originalApellido = et_apellido.getText().toString();
        originalFechaNacimiento = et_fechanacimiento.getText().toString();
        originalTelefono = et_telefono.getText().toString();
        originalSexo = spinnerSexo.getSelectedItem().toString();
        originalCorreo = et_correo.getText().toString();
        originalDireccion = et_direccion.getText().toString();
    }
    /**
     * Revierte la edición restaurando los valores originales a los campos de información del usuario.
     */
    private void rollbackEdit(){
        et_nombre.setText(originalNombre);
        et_apellido.setText(originalApellido);
        et_fechanacimiento.setText(originalFechaNacimiento);
        et_telefono.setError(originalTelefono);
        et_correo.setText(originalCorreo);
        et_direccion.setText(originalDireccion);
        if ("Masculino".equals(originalSexo)) {
            spinnerSexo.setSelection(0);
        } else if ("Femenino".equals(originalSexo)) {
            spinnerSexo.setSelection(1);
        } else if ("Otro".equals(originalSexo)) {
            spinnerSexo.setSelection(2);
        }
    }

    private void closeSession() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean validateInput() {
        boolean isValid = true;
        // Validar el nombre
        String nombre = et_nombre.getText().toString();
        if (nombre.isEmpty()) {
            et_nombre.setError("Este campo es requerido");
            isValid = false;
        }

        // Validar el apellido
        String apellido = et_apellido.getText().toString();
        if (apellido.isEmpty()) {
            et_apellido.setError("Este campo es requerido");
            isValid = false;
        }

        String dni = et_fechanacimiento.getText().toString();
        if (dni.isEmpty()) {
            et_fechanacimiento.setError("Este campo es requerido");
            isValid = false;
        }
        String telefono = et_telefono.getText().toString();
        if(telefono.isEmpty()){
            et_telefono.setError("Este campo es requerido");
            isValid = false;
        }
        else if(!GeneralHelper.isNumeric(telefono)){
            et_telefono.setError("Por favor, solo ingrese números");
            isValid = false;
        }
        String correo = et_correo.getText().toString();
        if (correo.isEmpty()) {
            et_correo.setError("Este campo es requerido");
            isValid = false;
        } else if (!GeneralHelper.isValidEmailAddress(correo)) {
            et_correo.setError("Correo electrónico inválido");
            isValid = false;
        }

        String direccion = et_direccion.getText().toString();
        if(direccion.isEmpty()){
            et_direccion.setError("Este campo es requerido");
            isValid= false;
        }

        return isValid;
    }


    public void showDatePicker(){
        DatePickerDialog dpdialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formattedMonth = (month + 1 < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                String date = year + "-" + formattedMonth + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                et_fechanacimiento.setText(date);
            }
        }, 2023, 11, 3);

        dpdialog.show();
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
                    updateHunter.setId_rango(userSession.getId_rango());
                    updateHunter.setUser(userSession.getUser());
                    updateHunter.setPuntaje(userSession.getPuntaje());
                    mViewModel.setHunterDataWithHunte(updateHunter);
                    sessionManager.saveHunterSession(updateHunter);
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