package com.example.ffh_rep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.activitys.NavigationController;
import com.example.ffh_rep.activitys.RegistroBasic;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.LoginUsuarioCallback;
import com.example.ffh_rep.tasks.IniciarSesionTask;

public class MainActivity extends AppCompatActivity implements LoginUsuarioCallback {

    private EditText etUsername, etPassword;
    private Button btnIniciarSesion, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupButtonClickListeners();
    }

    private void initializeViews() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistro = findViewById(R.id.btnRegistro);
    }

    private void setupButtonClickListeners() {
        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());
        btnRegistro.setOnClickListener(v -> irARegistro());
    }

    private void iniciarSesion() {
        IniciarSesionTask task = new IniciarSesionTask(
                this,
                etUsername.getText().toString(),
                etPassword.getText().toString(),
                this
        );
        task.execute();
    }

    private void irARegistro() {
        Intent intent = new Intent(MainActivity.this, RegistroBasic.class);
        startActivity(intent);
    }

    @Override
    public void onSuccessLogin(Usuario user) {
        Intent intent = new Intent(MainActivity.this, NavigationController.class);
        intent.putExtra("usuario", user);
        startActivity(intent);
    }
}
