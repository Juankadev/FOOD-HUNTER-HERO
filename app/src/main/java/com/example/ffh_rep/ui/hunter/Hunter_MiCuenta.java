package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.databinding.FragmentHunterMiCuentaBinding;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.factory.HunterMiCuentaViewModelFactory;
import com.example.ffh_rep.utils.SessionManager;

public class Hunter_MiCuenta extends Fragment {

    private HunterMiCuentaViewModel mViewModel;
    private FragmentHunterMiCuentaBinding binding;
    private TextView et_nombre, et_apellido, et_dni, et_sexo, et_correo, et_direccion;
    private Button btnEditarInformacion, btnEliminarCuenta, btnEditAction, btnCancel;
    private Hunter userSession;
    private String originalNombre, originalApellido, originalDNI, originalSexo, originalCorreo, originalDireccion;


    private SessionManager sessionManager;

    public static Hunter_MiCuenta newInstance() {
        return new Hunter_MiCuenta();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHunterMiCuentaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initComponents(view);

        sessionManager = new SessionManager(requireActivity());
        userSession = sessionManager.getHunterSession();

        mViewModel.setHunterDataWithHunte(this.userSession);

        settingObservers();
        settingListeners();
        return view;
    }
    private void initComponents(View view) {
        et_apellido = view.findViewById(R.id.et_apellido);
        et_nombre = view.findViewById(R.id.et_nombre);
        et_dni = view.findViewById(R.id.et_dni);
        et_sexo = view.findViewById(R.id.et_sexo);
        et_correo = view.findViewById(R.id.et_correo);
        et_direccion = view.findViewById(R.id.et_direccion);
        btnEditarInformacion = view.findViewById(R.id.btn_editInformacion);
        btnEliminarCuenta = view.findViewById(R.id.btn_deleteaccount);
        btnEditAction = view.findViewById(R.id.btn_edit_actioner);
        btnCancel = view.findViewById(R.id.btn_Cancel_Edit);

        mViewModel = new ViewModelProvider(requireActivity(), new HunterMiCuentaViewModelFactory(getActivity())).get(HunterMiCuentaViewModel.class);
    }

    private void settingInputs(Hunter user){
        et_apellido.setText(user.getApellido());
        et_nombre.setText(user.getNombre());
        et_dni.setText(user.getDni());
        et_sexo.setText(user.getSexo());
        et_correo.setText(user.getCorreo_electronico());
        et_direccion.setText(user.getDireccion());
    }

    public void settingListeners(){
        btnEliminarCuenta.setOnClickListener(v -> deleteMyAccount());
        btnEditarInformacion.setOnClickListener(v -> enabledInputs(true));
        btnEditAction.setOnClickListener(v -> updateInformation());
        btnCancel.setOnClickListener(v-> enabledInputs(false));
    }

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
    }

    private void updateInformation(){
        Hunter updateHunter = new Hunter();

        updateHunter.setIdHunter(this.userSession.getIdHunter());
        updateHunter.setApellido(et_apellido.getText().toString());
        updateHunter.setNombre(et_nombre.getText().toString());
        updateHunter.setDni(et_dni.getText().toString());
        updateHunter.setSexo(et_sexo.getText().toString());
        updateHunter.setCorreo_electronico(et_correo.getText().toString());
        updateHunter.setDireccion(et_direccion.getText().toString());

        mViewModel.updateInformation(updateHunter);
    }

    private void enabledInputs(boolean _var){
        et_apellido.setEnabled(_var);
        et_nombre.setEnabled(_var);
        et_dni.setEnabled(_var);
        et_sexo.setEnabled(_var);
        et_correo.setEnabled(_var);
        et_direccion.setEnabled(_var);

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

    private void saveOriginals(){
        originalNombre = et_nombre.getText().toString();
        originalApellido = et_apellido.getText().toString();
        originalDNI = et_dni.getText().toString();
        originalSexo = et_sexo.getText().toString();
        originalCorreo = et_correo.getText().toString();
        originalDireccion = et_direccion.getText().toString();
    }

    private void rollbackEdit(){
        et_nombre.setText(originalNombre);
        et_apellido.setText(originalApellido);
        et_dni.setText(originalDNI);
        et_sexo.setText(originalSexo);
        et_correo.setText(originalCorreo);
        et_direccion.setText(originalDireccion);
    }

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