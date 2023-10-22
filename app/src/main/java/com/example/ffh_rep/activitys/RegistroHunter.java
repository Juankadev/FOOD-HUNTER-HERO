package com.example.ffh_rep.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.tasks.RegistrarHunterTask;
import com.example.ffh_rep.tasks.RegistrarUsuario;

public class RegistroHunter extends AppCompatActivity implements RegistrarUsuarioCallback {

    private EditText etNombre, etApellido, etTelefono, etDireccion, etDni, etSexo, etCorreo;
    private Spinner spinnerSexo;
    private Button btnRegistro, btnVolver;
    private String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_hunter);

        extractIntentData();
        initializeViews();
        setupButtonClickListeners();
    }

    private void extractIntentData() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
    }

    private void initializeViews() {
        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etTelefono = findViewById(R.id.et_telefono);
        etDireccion = findViewById(R.id.et_direccion);
        etDni = findViewById(R.id.et_dni);
        etSexo = findViewById(R.id.et_sexo);
        etCorreo = findViewById(R.id.et_correo);
        btnRegistro = findViewById(R.id.btn_register);
        btnVolver = findViewById(R.id.btn_back_menu);
        spinnerSexo = findViewById(R.id.spinner_sexo);
        // Opciones para el Spinner
        String[] opcionesSexo = {"Masculino", "Femenino", "Otro"};
        // Adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Asigna el adaptador al Spinner
        spinnerSexo.setAdapter(adapter);
    }

    private void setupButtonClickListeners() {
        btnRegistro.setOnClickListener(v -> onRegistroButtonClick());
        btnVolver.setOnClickListener(v -> onVolverButtonClick());
    }

    private void onRegistroButtonClick() {
        Rol rol = new Rol(2, "Hunter");
        Usuario usuario = new Usuario(rol, username, password, "Activo");

        RegistrarUsuario registrarUsuarioTask = new RegistrarUsuario(this, usuario, this);
        registrarUsuarioTask.execute();
    }

    private void onVolverButtonClick() {
        Intent intent = new Intent(RegistroHunter.this, MainActivity.class);
        startActivity(intent);
    }

    private void registrarHunter(Integer userId) {
        Hunter hunter = buildHunterObject(userId);
        RegistrarHunterTask registrarHunterTask = new RegistrarHunterTask(RegistroHunter.this, hunter, RegistroHunter.this);
        registrarHunterTask.execute();
    }

    private Hunter buildHunterObject(Integer userId) {
        Hunter hunter = new Hunter();
        hunter.setUser(new Usuario());
        hunter.getUser().setId_usuario(userId);
        hunter.setNombre(etNombre.getText().toString());
        hunter.setApellido(etApellido.getText().toString());
        hunter.setDni(etDni.getText().toString());
        hunter.setDireccion(etDireccion.getText().toString());
        hunter.setSexo(spinnerSexo.getSelectedItem().toString());
        hunter.setCorreo_electronico(etCorreo.getText().toString());
        hunter.setTelefono(etTelefono.getText().toString());
        return hunter;
    }

    @Override
    public void onUsuarioInsertado(Integer userId) {
        registrarHunter(userId);
    }

    @Override
    public void onUsuarioError() {
        // Manejar errores si es necesario
    }

    @Override
    public void onCompleteInsert(String username, String password) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
