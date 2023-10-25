package com.example.ffh_rep.ui.hunter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
    private Button btnEditarInformacion, btnEliminarCuenta;
    private Hunter userSession;

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
        btnEliminarCuenta.setOnClickListener(v -> {});
        btnEditarInformacion.setOnClickListener(v -> updateInformation());
    }

    private void settingObservers(){
        mViewModel.getHunterData().observe(getViewLifecycleOwner(), new Observer<Hunter>() {
            @Override
            public void onChanged(Hunter hunter) {
                settingInputs(hunter);
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

}