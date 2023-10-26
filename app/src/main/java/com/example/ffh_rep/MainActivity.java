package com.example.ffh_rep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ffh_rep.activitys.NavigationController;
import com.example.ffh_rep.activitys.RegistroBasic;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.LoginUsuarioCallback;
import com.example.ffh_rep.tasks.IniciarSesionTask;
import com.example.ffh_rep.tasks.UserDetailsByRoleTask;

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

    /**
     * Inicializa las vistas necesarias para la interfaz de inicio de sesión.
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    private void initializeViews() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistro = findViewById(R.id.btnRegistro);
    }
    /**
     * Configura los listeners de clic para los botones de la interfaz.
     * Asigna los métodos correspondientes a los eventos de clic en los botones.
     */
    private void setupButtonClickListeners() {
        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());
        btnRegistro.setOnClickListener(v -> irARegistro());
    }
    /**
     * Inicia el proceso de inicio de sesión.
     * Verifica que se completen todos los campos antes de ejecutar la tarea de inicio de sesión.
     * Si algún campo falta, muestra un mensaje de advertencia.
     */
    private void iniciarSesion() {
        if(validateFields())
        {
            IniciarSesionTask task = new IniciarSesionTask(
                    this,
                    etUsername.getText().toString(),
                    etPassword.getText().toString(),
                    this
            );
            task.execute();
        }
        else
        {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Valida que los campos de nombre de usuario y contraseña estén completos.
     *
     * @return true si los campos están completos, false de lo contrario.
     */
    private boolean validateFields()
    {
        return !(etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty());
    }
    /**
     * Navega hacia la actividad de registro básico.
     */
    private void irARegistro() {
        Intent intent = new Intent(MainActivity.this, RegistroBasic.class);
        startActivity(intent);
    }
    /**
     * Maneja la acción exitosa de inicio de sesión, redirigiendo a la pantalla de navegación.
     *
     * @param user Usuario que ha iniciado sesión exitosamente.
     */
    @Override
    public void onSuccessLogin(Usuario user) {
        Intent intent = new Intent(MainActivity.this, NavigationController.class);
        intent.putExtra("usuario", user);
        startActivity(intent);
    }
    /**
     * Maneja la acción exitosa de inicio de sesión para cazadores, redirigiendo a la pantalla de navegación.
     *
     * @param hunter Cazador que ha iniciado sesión exitosamente.
     */
    @Override
    public void onSuccessLoginHunter(Hunter hunter) {
        Intent intent = new Intent(MainActivity.this, NavigationController.class);
        intent.putExtra("hunter", hunter);
        startActivity(intent);
    }
    /**
     * Maneja la acción exitosa de inicio de sesión para comercios, redirigiendo a la pantalla de navegación.
     *
     * @param commerce Comercio que ha iniciado sesión exitosamente.
     */
    @Override
    public void onSuccessLoginCommerce(Comercio commerce) {
        Intent intent = new Intent(MainActivity.this, NavigationController.class);
        intent.putExtra("comercio", commerce);
        startActivity(intent);
    }
    /**
     * Obtiene y maneja la descripción del usuario según su rol, iniciando una tarea asíncrona.
     *
     * @param user Usuario para el cual se desea obtener la descripción.
     */
    @Override
    public void doGetUserDescriptionByRole(Usuario user) {
        UserDetailsByRoleTask  userDetailsTask = new UserDetailsByRoleTask(user.getRol().getIdRol(), user, user.getId_usuario(), this, this);
        userDetailsTask.execute();
    }
}
