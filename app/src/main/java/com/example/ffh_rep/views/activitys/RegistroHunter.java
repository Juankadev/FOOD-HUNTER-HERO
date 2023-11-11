package com.example.ffh_rep.views.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.entidades.Rol;
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.models.tasks.RegistrarHunterTask;
import com.example.ffh_rep.models.tasks.RegistrarUsuario;
import com.example.ffh_rep.utils.EmailSenderTask;
import com.example.ffh_rep.utils.EmailTemplate;
import com.example.ffh_rep.utils.GeneralHelper;

public class RegistroHunter extends AppCompatActivity implements RegistrarUsuarioCallback {

    private EditText etNombre, etApellido, etTelefono, etDireccion, etDni, etCorreo, etFechaNac;
    private Spinner spinnerSexo;
    private Button btnRegistro, btnVolver;
    private String username, password;
    private Hunter hunter;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_hunter);

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
     * Inicializa las vistas necesarias para la interfaz de registro (hunter).
     * Asigna las instancias de los elementos de la interfaz a las variables correspondientes.
     */
    private void initializeViews() {
        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etTelefono = findViewById(R.id.et_telefono);
        etDireccion = findViewById(R.id.et_direccion);
        etDni = findViewById(R.id.et_dni);
        etCorreo = findViewById(R.id.et_correo);
        btnRegistro = findViewById(R.id.btn_register);
        btnVolver = findViewById(R.id.btn_back_menu);
        spinnerSexo = findViewById(R.id.spinner_sexo);
        etFechaNac = findViewById(R.id.et_fechanacimiento);
        setupSpinner();
    }
    /**
     * Configura el Spinner de género con las opciones predefinidas.
     * Crea un ArrayAdapter con las opciones de género y lo asigna al Spinner.
     */
    private void setupSpinner() {
        String[] opcionesSexo = {"Masculino", "Femenino", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesSexo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);
    }
    /**
     * Configura los listeners de la interfaz.
     * Asigna los métodos correspondientes a los eventos.
     */
    private void setupListeners() {
        btnRegistro.setOnClickListener(v -> onRegistroButtonClick());
        btnVolver.setOnClickListener(v -> onVolverButtonClick());
        etFechaNac.setOnClickListener(v -> showDatePicker());
        etFechaNac.setOnFocusChangeListener((view, focus) -> {
            if(focus){
                showDatePicker();
            }
        });
    }
    /**
     * Maneja el clic en el botón de registro.
     * Crea un objeto Rol y un objeto Usuario con los datos proporcionados.
     * Inicia la tarea asincrónica para registrar al usuario.
     */
    private void onRegistroButtonClick() {
        if (validateFields())
        {
            Rol rol = new Rol(2, "Hunter");
            usuario = new Usuario(rol, username, password, true);

            RegistrarUsuario registrarUsuarioTask = new RegistrarUsuario(this, usuario, this);
            registrarUsuarioTask.execute();
        }
        else
        {
            Toast.makeText(this, "Por favor, antes de continuar que verifique la informacion provista sea correcta.", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Valida los campos del formulario de registro.
     * @return true si todos los campos son válidos, false de lo contrario.
     */
    private boolean validateFields()
    {
        boolean isValid = true;

        if(etApellido.getText().toString().isEmpty() || etDireccion.getText().toString().isEmpty() || etCorreo.getText().toString().isEmpty() ||
            etDni.getText().toString().isEmpty() || spinnerSexo.getSelectedItem().toString().isEmpty() || etNombre.getText().toString().isEmpty() ||
            etFechaNac.getText().toString().isEmpty() || etTelefono.getText().toString().isEmpty())
        {
            isValid = false;
        }

        if(!GeneralHelper.isValidEmailAddress(etCorreo.getText().toString()))
        {
            isValid = false;
        }

        return isValid;
    }
    /**
     * Maneja el clic en el botón de volver.
     */
    private void onVolverButtonClick() {
        Intent intent = new Intent(RegistroHunter.this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * Registra un cazador con la información proporcionada y ejecuta la tarea asincrónica.
     * @param userId ID del usuario asociado al cazador.
     */
    private void registrarHunter(Integer userId) {
        hunter = buildHunterObject(userId);
        Log.d("[registrarHunter]", hunter.toString());
        RegistrarHunterTask registrarHunterTask = new RegistrarHunterTask(RegistroHunter.this, hunter, RegistroHunter.this);
        registrarHunterTask.execute();
        sendRegistrationEmail();
    }
    /**
     * Construye un objeto cazador con la información proporcionada en los campos de la interfaz.
     * @param userId ID del usuario asociado al cazador.
     * @return Objeto Hunter con los datos ingresados.
     */
    private Hunter buildHunterObject(Integer userId) {
        Hunter hunter = new Hunter();
        hunter.setUser(new Usuario());
        hunter.getUser().setId_usuario(userId);
        hunter.setNombre(etNombre.getText().toString());
        hunter.setApellido(etApellido.getText().toString());
        hunter.setDni(etDni.getText().toString());
        hunter.setFecha_nacimiento(GeneralHelper.returnSQLFORMAT2(etFechaNac.getText().toString()));
        hunter.setDireccion(etDireccion.getText().toString());
        hunter.setSexo(spinnerSexo.getSelectedItem().toString());
        hunter.setCorreo_electronico(etCorreo.getText().toString());
        hunter.setTelefono(etTelefono.getText().toString());
        return hunter;
    }
    @Override
    public void onUsuarioInsertado(Integer userId) {
        usuario.setId_usuario(userId);
        registrarHunter(userId);
    }
    @Override
    public void onUsuarioError() {
        Intent intent = new Intent(RegistroHunter.this, MainActivity.class);
        startActivity(intent);
    }
    /**
     * Callback que se llama cuando se completa la inserción de un nuevo usuario.
     * Crea un Intent para iniciar la actividad principal (MainActivity) y navega a ella.
     * @param username Nombre de usuario del nuevo usuario.
     * @param password Contraseña del nuevo usuario.
     */
    @Override
    public void onCompleteInsert(String username, String password) {
        Intent intent = new Intent(this, NavigationController.class);
        hunter.setUser(usuario);
        intent.putExtra("hunter", hunter);
        startActivity(intent);
    }

    @Override
    public void onCompleteInsertAndRedirectToApp(Hunter hunter) {
        Intent intent = new Intent(this, NavigationController.class);
        intent.putExtra("hunter", hunter);
        startActivity(intent);
    }

    /**
     * Muestra un diálogo de selección de fecha (DatePickerDialog) y actualiza el campo de fecha de nacimiento
     * con la fecha seleccionada por el usuario.
     */
    public void showDatePicker(){
        DatePickerDialog dpdialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formattedMonth = (month + 1 < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                String date = year + "-" + formattedMonth + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                etFechaNac.setText(date);
            }
        }, 2000, 0, 1);
        dpdialog.show();
    }

    private void sendRegistrationEmail() {
        String subjectMail = "Registro de Hunter " + etNombre.getText().toString() + " Exitoso ";
        EmailTemplate plantilla = new EmailTemplate();
        String messageBodyMail = plantilla.templateRegistroExitosoHunter(
                etNombre.getText().toString(),
                etCorreo.getText().toString()
        );

        EmailSenderTask emailSenderTask = new EmailSenderTask(etCorreo.getText().toString(), subjectMail, messageBodyMail);
        emailSenderTask.execute();
    }

}
