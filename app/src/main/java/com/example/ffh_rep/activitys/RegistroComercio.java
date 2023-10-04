package com.example.ffh_rep.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.MainActivity;
import com.example.ffh_rep.R;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Hunter;
import com.example.ffh_rep.utils.EmailSender;
import com.example.ffh_rep.utils.EmailSenderTask;

public class RegistroComercio extends AppCompatActivity {

    private EditText et_razonsocial, et_cuit, et_rubro, et_email_c, et_telefono_c, et_direccion_c;
    private Button btnRegistro, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comercio);

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
                registrarComercio();
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

    private void registrarComercio(){
        //Comercio comercio = new Comercio();
        //comercio.setRazonSocial(et_razonsocial.getText().toString());
        //comercio.setCuit(et_cuit.getText().toString());
        /*comercio.setRubro(et_rubro.getText().toString());
        comercio.setEmail(et_email_c.getText().toString());
        comercio.setTelefono(et_telefono_c.getText().toString());
        comercio.setDireccion(et_direccion_c.getText().toString());*/
        String username = "sendertoreset@gmail.com";
        String password = "agjrqmkskscduqsa";
        String recipientEmail = "ichigohollow667@gmail.com";
        String subject = "Hola";
        String messageBody = "Probando";

        EmailSenderTask emailSenderTask = new EmailSenderTask(username, password, recipientEmail, subject, messageBody);
        emailSenderTask.execute();


    }
}