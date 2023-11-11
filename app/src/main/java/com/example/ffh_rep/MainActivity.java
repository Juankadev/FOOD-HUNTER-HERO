package com.example.ffh_rep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffh_rep.views.activitys.NavigationController;
import com.example.ffh_rep.views.activitys.RegistroBasic;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.LoginUsuarioCallback;
import com.example.ffh_rep.models.tasks.IniciarSesionTask;
import com.example.ffh_rep.models.tasks.UserDetailsByRoleTask;


public class MainActivity extends AppCompatActivity implements LoginUsuarioCallback {

    private EditText etUsername, etPassword;
    private Button btnIniciarSesion, btnRegistro;
    private CardView  initSession;
    private ProgressBar pgBarInit;
    private TextView initSessionText;


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
        initSession = findViewById(R.id.initDispatcher);
        pgBarInit = findViewById(R.id.progressBar);
        initSessionText = findViewById(R.id.tv_initcontent);
    }
    /**
     * Configura los listeners de clic para los botones de la interfaz.
     * Asigna los métodos correspondientes a los eventos de clic en los botones.
     */
    private void setupButtonClickListeners() {
        initSession.setOnClickListener(v -> iniciarSesion());
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
            pgBarInit.setVisibility(View.VISIBLE);
            initSessionText.setText("Iniciando...");
            IniciarSesionTask task = new IniciarSesionTask(
                    this,
                    etUsername.getText().toString(),
                    etPassword.getText().toString(),
                    this
            );
            btnIniciarSesion.setEnabled(false);
            btnIniciarSesion.setBackgroundColor(Color.parseColor("#E2E2E2"));
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
        pgBarInit.setVisibility(View.GONE);
        initSessionText.setText("¡Bienvenido!");
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
        pgBarInit.setVisibility(View.GONE);
        initSessionText.setText("¡Bienvenido!");
        Intent intent = new Intent(MainActivity.this, NavigationController.class);
        intent.putExtra("comercio", commerce);
        startActivity(intent);
    }

    @Override
    public void onSuccessLoginAdmin(Usuario user) {
        pgBarInit.setVisibility(View.GONE);
        initSessionText.setText("¡Bienvenido!");
        Intent intent = new Intent(MainActivity.this, NavigationController.class);
        intent.putExtra("usuario", user);
        startActivity(intent);
    }

    @Override
    public void onErrorLogin() {
        pgBarInit.setVisibility(View.GONE);
        initSessionText.setText("Iniciar Sesión");
        btnIniciarSesion.setEnabled(true);
        btnIniciarSesion.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onErrorLoginHunter() {
        pgBarInit.setVisibility(View.GONE);
        initSessionText.setText("Iniciar Sesión");
        btnIniciarSesion.setEnabled(true);
        btnIniciarSesion.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onErrorLoginCommerce() {
        pgBarInit.setVisibility(View.GONE);
        initSessionText.setText("Iniciar Sesión");
        btnIniciarSesion.setEnabled(true);
        btnIniciarSesion.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    /**
     * Obtiene y maneja la descripción del usuario según su rol, iniciando una tarea asíncrona.
     *
     * @param user Usuario para el cual se desea obtener la descripción.
     */
    @Override
    public void doGetUserDescriptionByRole(Usuario user) {
        pgBarInit.setVisibility(View.GONE);
        initSessionText.setText("¡Bienvenido!");
        UserDetailsByRoleTask  userDetailsTask = new UserDetailsByRoleTask(user.getRol().getIdRol(), user, user.getId_usuario(), this, this);
        userDetailsTask.execute();
    }
}
