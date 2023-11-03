package com.example.ffh_rep.views.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.models.tasks.UserExistsTask;

public class RegistroBasic extends AppCompatActivity {

    private EditText etRUser, etRPass, etRcPass;
    private Button btnBack, btnHunter, btnCommerce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_basic);

        initializeViews();
        setupListeners();
    }
    /**
     * Inicializa las vistas necesarias para la interfaz de registro.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    private void initializeViews() {
        etRUser = findViewById(R.id.et_r_user);
        etRPass = findViewById(R.id.et_r_password);
        etRcPass = findViewById(R.id.et_rc_password);

        btnBack = findViewById(R.id.btn_rc_back);
        btnCommerce = findViewById(R.id.btn_rc_commerce);
        btnHunter = findViewById(R.id.btn_rc_Hunter);
    }
    /**
     * Configura los listeners de la interfaz.
     * Asigna los métodos correspondientes a los eventos.
     */
    private void setupListeners() {
        btnHunter.setOnClickListener(v -> onTypeSelected(RegistroHunter.class));
        btnCommerce.setOnClickListener(v -> onTypeSelected(RegistroComercio.class));
        btnBack.setOnClickListener(v -> goBackToMainActivity());
    }
    /**
     * Método llamado al seleccionar un tipo de registro (Hunter o Comercio).
     * Verifica la validez de los campos y, si son válidos, inicia la actividad de registro correspondiente.
     *
     * @param registrationType Clase de la actividad de registro seleccionada.
     */
    private void onTypeSelected(Class<?> registrationType) {
        // Verificar si los campos son válidos antes de iniciar la actividad de registro
        if (validateFields()) {
            // Crear un intent para iniciar la actividad de registro
            Intent intent = new Intent(RegistroBasic.this, registrationType);

            // Agregar datos extra al intent (nombre de usuario y contraseña)
            intent.putExtra("username", etRUser.getText().toString());
            intent.putExtra("password", etRPass.getText().toString());

            // Iniciar la actividad de registro
            startActivity(intent);
        }
    }
    /**
     * Valida los campos del formulario de registro.
     * Verifica si el nombre de usuario ya existe y si las contraseñas coinciden.
     *
     * @return true si todos los campos son válidos, false de lo contrario.
     */
    private boolean validateFields() {
        boolean isValid = true;

        // Obtener los valores de los campos
        String username = etRUser.getText().toString();
        String password = etRPass.getText().toString();
        String confirmPassword = etRcPass.getText().toString();

        // Verificar si los campos están vacíos
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            isValid = false;
        }

        // Verificar si las contraseñas coinciden
        if (!password.equals(confirmPassword)) {
            isValid = false;
        }

        // Mostrar mensaje de error si los campos no son válidos
        if (!isValid) {
            Toast.makeText(RegistroBasic.this, "Por favor, antes de continuar verifique todos los datos.", Toast.LENGTH_SHORT).show();
            return isValid;
        }

        // Verificar si el nombre de usuario ya existe en la base de datos
        try {
            UserExistsTask task = new UserExistsTask(this, username);
            boolean userExists = task.execute().get(); // Esperar a que se complete la tarea

            // Si el nombre de usuario ya existe, marcar como no válido y mostrar mensaje de error
            if (userExists) {
                isValid = false;
                Toast.makeText(RegistroBasic.this, "El nombre de usuario ya está en uso.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }
    /**
     * Navega de nuevo a la actividad principal (MainActivity).
     */
    private void goBackToMainActivity() {
        Intent intent = new Intent(RegistroBasic.this, MainActivity.class);
        startActivity(intent);
    }
}