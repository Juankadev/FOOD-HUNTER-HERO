package com.example.ffh_rep.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Hunter;

public class RegistroHunter extends AppCompatActivity {
    private EditText et_nombre, et_apellido, et_telefono, et_direccion, et_dni, et_sexo, et_correo;
    private Button btnRegistro, btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_hunter);

        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_telefono = findViewById(R.id.et_telefono);
        et_direccion = findViewById(R.id.et_direccion);
        et_dni = findViewById(R.id.et_dni);
        et_sexo = findViewById(R.id.et_sexo);
        et_correo = findViewById(R.id.et_correo);
        btnRegistro = findViewById(R.id.btn_register);
        btnVolver = findViewById(R.id.btn_back_menu);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarHunter();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroHunter.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registrarHunter(){
        Hunter hunter = new Hunter();
        hunter.setNombre(et_nombre.getText().toString());
        hunter.setApellido(et_apellido.getText().toString());
        hunter.setDni(et_dni.getText().toString());
        hunter.setDireccion(et_direccion.getText().toString());
        hunter.setSexo(et_sexo.getText().toString());
        hunter.setCorreo_electronico(et_correo.getText().toString());
        hunter.setTelefono(et_telefono.getText().toString());


    }
}