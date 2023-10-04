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

public class RegistroBasic extends AppCompatActivity {

    private EditText et_r_user, et_r_pass, et_rc_pass;
    private Button btnBack, btnHunter, btnCommerce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_basic);

        et_r_user = findViewById(R.id.et_r_user);
        et_r_pass = findViewById(R.id.et_r_password);
        et_rc_pass= findViewById(R.id.et_rc_password);

        btnBack = findViewById(R.id.btn_rc_back);
        btnCommerce = findViewById(R.id.btn_rc_commerce);
        btnHunter = findViewById(R.id.btn_rc_Hunter);

        btnHunter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllComplete(et_r_user.getText().toString(), et_r_pass.getText().toString(), et_rc_pass.getText().toString())){
                    if(samePasswords(et_r_pass.getText().toString(), et_rc_pass.getText().toString())){
                        Intent intent = new Intent(RegistroBasic.this, RegistroHunter.class);

                        String username = et_r_user.getText().toString();
                        String password = et_r_pass.getText().toString();
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);

                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegistroBasic.this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegistroBasic.this, "Por favor, antes de continuar complete todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCommerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllComplete(et_r_user.getText().toString(), et_r_pass.getText().toString(), et_rc_pass.getText().toString())){
                    if(samePasswords(et_r_pass.getText().toString(), et_rc_pass.getText().toString())){
                        Intent intent = new Intent(RegistroBasic.this, RegistroComercio.class);
                        String username = et_r_user.getText().toString();
                        String password = et_r_pass.getText().toString();
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);

                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegistroBasic.this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegistroBasic.this, "Por favor, antes de continuar complete todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroBasic.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isAllComplete(String campito1, String campito2, String campito3){
        return !campito1.isEmpty() && !campito2.isEmpty() && !campito3.isEmpty();
    }

    private boolean samePasswords(String pass, String c_pass){
        return pass.equals(c_pass);
    }
}