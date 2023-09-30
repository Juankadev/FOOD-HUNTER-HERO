package com.example.ffh_rep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffh_rep.tasks.IniciarSesionTask;

public class MainActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private Button btnIniciarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesionTask task = new IniciarSesionTask(MainActivity.this, et_username.getText().toString(),et_password.getText().toString());
                task.execute();
            }
        });
    }

}