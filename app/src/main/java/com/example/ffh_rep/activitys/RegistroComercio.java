package com.example.ffh_rep.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.tasks.RegistrarComercioTask;
import com.example.ffh_rep.tasks.RegistrarUsuario;
import com.example.ffh_rep.utils.EmailTemplate;
import com.example.ffh_rep.utils.EmailSenderTask;

public class RegistroComercio extends AppCompatActivity implements RegistrarUsuarioCallback {

    private EditText etRazonSocial, etCuit, etRubro, etEmailC, etTelefonoC, etDireccionC;
    private Button btnRegistro, btnVolver;
    private String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comercio);

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
        etRazonSocial = findViewById(R.id.et_razonsocial);
        etCuit = findViewById(R.id.et_cuit);
        etRubro = findViewById(R.id.et_rubro);
        etEmailC = findViewById(R.id.et_email_c);
        etTelefonoC = findViewById(R.id.et_telefono_c);
        etDireccionC = findViewById(R.id.et_direccion_c);
        btnRegistro = findViewById(R.id.btnRegistrarme);
        btnVolver = findViewById(R.id.btnVolver);
    }

    private void setupButtonClickListeners() {
        btnRegistro.setOnClickListener(v -> onRegistroButtonClick());
        btnVolver.setOnClickListener(v -> onVolverButtonClick());
    }

    private void onRegistroButtonClick() {
        Rol rol = new Rol(1, "Comercio");
        Usuario usuario = new Usuario(rol, username, password, "Activo");

        RegistrarUsuario registrarUsuarioTask = new RegistrarUsuario(this, usuario, this);
        registrarUsuarioTask.execute();
    }

    private void onVolverButtonClick() {
        Intent intent = new Intent(RegistroComercio.this, MainActivity.class);
        startActivity(intent);
    }

    private void registro(Integer userId) {
        Comercio comercio = buildComercioObject(userId);
        RegistrarComercioTask registrarComercioTask = new RegistrarComercioTask(this, comercio, this);
        registrarComercioTask.execute();

        sendRegistrationEmail();
    }

    private Comercio buildComercioObject(Integer userId) {
        Comercio comercio = new Comercio();
        comercio.setUser(new Usuario());
        comercio.getUser().setId_usuario(userId);
        comercio.setRazonSocial(etRazonSocial.getText().toString());
        comercio.setCuit(etCuit.getText().toString());
        comercio.setRubro(etRubro.getText().toString());
        comercio.setEmail(etEmailC.getText().toString());
        comercio.setTelefono(etTelefonoC.getText().toString());
        comercio.setDireccion(etDireccionC.getText().toString());
        comercio.setAprobado("Aprobado");
        return comercio;
    }

    private void sendRegistrationEmail() {
        String subjectMail = "Registro de Comercio " + etRazonSocial.getText().toString() + " Exitoso ";
        EmailTemplate plantilla = new EmailTemplate();
        String messageBodyMail = plantilla.templateRegistroExitosoComercio(
                etRazonSocial.getText().toString(),
                etEmailC.getText().toString(),
                etEmailC.getText().toString()
        );

        EmailSenderTask emailSenderTask = new EmailSenderTask(etEmailC.getText().toString(), subjectMail, messageBodyMail);
        emailSenderTask.execute();
    }

    @Override
    public void onUsuarioInsertado(Integer userId) {
        registro(userId);
    }

    @Override
    public void onUsuarioError() {
        Toast.makeText(this, "Ha ocurrido un error al crear su usuario", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCompleteInsert(String username, String password) {
        Intent intent = new Intent(RegistroComercio.this, MainActivity.class);
        startActivity(intent);
    }
}