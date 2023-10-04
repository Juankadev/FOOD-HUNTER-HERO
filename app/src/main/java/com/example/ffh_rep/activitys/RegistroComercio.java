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
import com.example.ffh_rep.entidades.Usuario;
import com.example.ffh_rep.interfaces.RegistrarUsuarioCallback;
import com.example.ffh_rep.tasks.RegistrarComercioTask;
import com.example.ffh_rep.tasks.RegistrarUsuario;
import com.example.ffh_rep.utils.EmailSender;
import com.example.ffh_rep.utils.EmailSenderTask;

public class RegistroComercio extends AppCompatActivity implements RegistrarUsuarioCallback {

    private EditText et_razonsocial, et_cuit, et_rubro, et_email_c, et_telefono_c, et_direccion_c;
    private Button btnRegistro, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comercio);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        et_razonsocial = findViewById(R.id.et_razonsocial);
        et_cuit = findViewById(R.id.et_cuit);
        et_rubro = findViewById(R.id.et_rubro);
        et_email_c = findViewById(R.id.et_email_c);
        et_telefono_c = findViewById(R.id.et_telefono_c);
        et_direccion_c = findViewById(R.id.et_direccion_c);
        btnRegistro = findViewById(R.id.btnRegistrarme);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario(1, username, password, "Activo");
                RegistrarUsuario ru = new RegistrarUsuario(RegistroComercio.this, u, RegistroComercio.this);
                ru.execute();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroComercio.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void registro(Integer uID){
        Comercio comercio = new Comercio();
        comercio.setUser(new Usuario());
        comercio.getUser().setId_usuario(uID);
        comercio.setRazonSocial(et_razonsocial.getText().toString());
        comercio.setCuit(et_cuit.getText().toString());
        comercio.setRubro(et_rubro.getText().toString());
        comercio.setEmail(et_email_c.getText().toString());
        comercio.setTelefono(et_telefono_c.getText().toString());
        comercio.setDireccion(et_direccion_c.getText().toString());
        comercio.setAprobado("Aprobado");

        RegistrarComercioTask rct = new RegistrarComercioTask(this, comercio);
        rct.execute();

        /*String username = "sendertoreset@gmail.com";
        String password = "agjrqmkskscduqsa";
        String recipientEmail = "ichigohollow667@gmail.com";
        String subject = "Hola";
        String messageBody = "Probando";

        EmailSenderTask emailSenderTask = new EmailSenderTask(username, password, recipientEmail, subject, messageBody);
        emailSenderTask.execute();*/


    }

    @Override
    public void onUsuarioInsertado(Integer userId) {
        Log.d("Recibido", String.valueOf(userId));
        registro(userId);
    }

    @Override
    public void onUsuarioError() {
        Toast.makeText(this, "Ha ocurrido un error al crear su usuario", Toast.LENGTH_LONG).show();
    }
}