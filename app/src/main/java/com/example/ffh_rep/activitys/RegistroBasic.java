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

    private EditText etRUser, etRPass, etRcPass;
    private Button btnBack, btnHunter, btnCommerce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_basic);

        initializeViews();
        setupButtonClickListeners();
    }

    private void initializeViews() {
        etRUser = findViewById(R.id.et_r_user);
        etRPass = findViewById(R.id.et_r_password);
        etRcPass = findViewById(R.id.et_rc_password);

        btnBack = findViewById(R.id.btn_rc_back);
        btnCommerce = findViewById(R.id.btn_rc_commerce);
        btnHunter = findViewById(R.id.btn_rc_Hunter);
    }

    private void setupButtonClickListeners() {
        btnHunter.setOnClickListener(v -> onTypeSelected(RegistroHunter.class));
        btnCommerce.setOnClickListener(v -> onTypeSelected(RegistroComercio.class));
        btnBack.setOnClickListener(v -> goBackToMainActivity());
    }

    private void onTypeSelected(Class<?> registrationType) {
        if (isAllComplete(etRUser.getText().toString(), etRPass.getText().toString(), etRcPass.getText().toString())) {
            if (samePasswords(etRPass.getText().toString(), etRcPass.getText().toString())) {
                Intent intent = new Intent(RegistroBasic.this, registrationType);
                intent.putExtra("username", etRUser.getText().toString());
                intent.putExtra("password", etRPass.getText().toString());
                startActivity(intent);
            } else {
                showToast("Las contraseñas deben ser iguales");
            }
        } else {
            showToast("Por favor, antes de continuar complete todos los datos");
        }
    }

    private void goBackToMainActivity() {
        Intent intent = new Intent(RegistroBasic.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean isAllComplete(String field1, String field2, String field3) {
        return !field1.isEmpty() && !field2.isEmpty() && !field3.isEmpty();
    }

    private boolean samePasswords(String pass, String cPass) {
        return pass.equals(cPass);
    }

    private void showToast(String message) {
        Toast.makeText(RegistroBasic.this, message, Toast.LENGTH_SHORT).show();
    }
}