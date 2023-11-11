package com.example.ffh_rep.views.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.ffh_rep.models.tasks.RegistrarComercioTask;
import com.example.ffh_rep.models.tasks.RegistrarUsuario;
import com.example.ffh_rep.utils.EmailTemplate;
import com.example.ffh_rep.utils.EmailSenderTask;
import com.example.ffh_rep.utils.GeneralHelper;

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
        setupListeners();
    }
    /**
     * Extrae datos del Intent pasado a la actividad.
     */
    private void extractIntentData() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
    }
    /**
     * Inicializa las vistas necesarias para la interfaz de registro (comercio).
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
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
    /**
     * Configura los listeners de la interfaz.
     * Asigna los métodos correspondientes a los eventos.
     */
    private void setupListeners() {
        btnRegistro.setOnClickListener(v -> onRegistroButtonClick());
        btnVolver.setOnClickListener(v -> onVolverButtonClick());
    }
    /**
     * Maneja el clic en el botón de registro.
     * Crea un objeto Rol y un objeto Usuario con los datos proporcionados.
     * Inicia la tarea asincrónica para registrar al usuario.
     */
    private void onRegistroButtonClick() {
        Rol rol = new Rol(1, "Comercio");
        Usuario usuario = new Usuario(rol, username, password, true);

        RegistrarUsuario registrarUsuarioTask = new RegistrarUsuario(this, usuario, this);
        registrarUsuarioTask.execute();
    }
    /**
     * Valida los campos del formulario de registro.
     * @return true si todos los campos son válidos, false de lo contrario.
     */
    public boolean validateFields() {
        boolean isValid = true;

        if (etCuit.getText().toString().isEmpty() || etDireccionC.getText().toString().isEmpty() || etEmailC.getText().toString().isEmpty() ||
            etRubro.getText().toString().isEmpty() || etRazonSocial.getText().toString().isEmpty() || etTelefonoC.getText().toString().isEmpty())
        {
            isValid = false;
        }

        if(!GeneralHelper.isValidEmailAddress(etEmailC.getText().toString()))
        {
            isValid = false;
        }
        return isValid;
    }
    /**
     * Maneja el clic en el botón de volver.
     */
    private void onVolverButtonClick() {
        Intent intent = new Intent(RegistroComercio.this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * Registra un cmercio con la información proporcionada y ejecuta la tarea asincrónica.
     * @param userId ID del usuario asociado al comercio.
     */
    private void registrarComercio(Integer userId) {
        if (validateFields())
        {
            Comercio comercio = buildComercioObject(userId);
            RegistrarComercioTask registrarComercioTask = new RegistrarComercioTask(this, comercio, this);
            registrarComercioTask.execute();

            sendRegistrationEmail();
        }
        else
        {
            Toast.makeText(this, "Por favor, antes de continuar verifique todos los datos.", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Construye un objeto comercio con la información proporcionada en los campos de la interfaz.
     * @param userId ID del usuario asociado al comercio.
     * @return Objeto Comercio con los datos ingresados.
     */
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
        comercio.setAprobado("Pendiente");
        return comercio;
    }
    /**
     * Envía un correo electrónico de confirmación de registro al comercio.
     * Construye el asunto del correo y el cuerpo del mensaje utilizando
     * la plantilla de correo electrónico proporcionada por EmailTemplate.
     * Inicia una tarea asincrónica para enviar el correo electrónico.
     */
    private void sendRegistrationEmail() {
        String subjectMail = "Registro de Comercio " + etRazonSocial.getText().toString() + " Exitoso ";
        EmailTemplate plantilla = new EmailTemplate();
        String messageBodyMail = plantilla.templateRegistroExitosoComercio(
                etRazonSocial.getText().toString(),
                etEmailC.getText().toString()
        );

        EmailSenderTask emailSenderTask = new EmailSenderTask(etEmailC.getText().toString(), subjectMail, messageBodyMail);
        emailSenderTask.execute();
    }

    @Override
    public void onUsuarioInsertado(Integer userId) {
        registrarComercio(userId);
    }

    @Override
    public void onUsuarioError() {
        Toast.makeText(this, "Ha ocurrido un error al crear su usuario", Toast.LENGTH_LONG).show();
    }
    /**
     * Callback que se llama cuando se completa la inserción de un nuevo usuario.
     * Crea un Intent para iniciar la actividad principal (MainActivity) y navega a ella.
     * @param username Nombre de usuario del nuevo usuario.
     * @param password Contraseña del nuevo usuario.
     */
    @Override
    public void onCompleteInsert(String username, String password) {
        Intent intent = new Intent(RegistroComercio.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCompleteInsertAndRedirectToApp(Hunter hunter) {

    }
}