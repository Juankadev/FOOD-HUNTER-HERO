package com.example.ffh_rep.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.tasks.RegistrarHunterTask;
import com.example.ffh_rep.tasks.RegistrarUsuario;

public class RegistroHunter extends AppCompatActivity implements RegistrarUsuarioCallback {
    private EditText et_nombre, et_apellido, et_telefono, et_direccion, et_dni, et_sexo, et_correo;
    private Button btnRegistro, btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_hunter);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

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
                Rol rol = new Rol();
                rol.setIdRol(2);
                rol.setDescripcion("Hunter");

                Usuario u = new Usuario(rol, username, password, "Activo");
                RegistrarUsuario ru = new RegistrarUsuario(RegistroHunter.this, u, RegistroHunter.this);
                ru.execute();
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

    private void registrarHunter(Integer userId){
        Hunter hunter = new Hunter();
        hunter.setUser(new Usuario());
        hunter.getUser().setId_usuario(userId);
        hunter.setNombre(et_nombre.getText().toString());
        hunter.setApellido(et_apellido.getText().toString());
        hunter.setDni(et_dni.getText().toString());
        hunter.setDireccion(et_direccion.getText().toString());
        hunter.setSexo(et_sexo.getText().toString());
        hunter.setCorreo_electronico(et_correo.getText().toString());
        hunter.setTelefono(et_telefono.getText().toString());

        RegistrarHunterTask rht = new RegistrarHunterTask(RegistroHunter.this, hunter, RegistroHunter.this);
        rht.execute();

    }

    @Override
    public void onUsuarioInsertado(Integer userId) {
        registrarHunter(userId);
    }

    @Override
    public void onUsuarioError() {

    }

    @Override
    public void onCompleteInsert(String username, String password) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}