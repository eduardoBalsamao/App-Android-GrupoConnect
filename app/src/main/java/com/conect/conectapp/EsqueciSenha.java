package com.conect.conectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EsqueciSenha extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetButton;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        emailEditText = findViewById(R.id.esqueciEmail);
        resetButton = findViewById(R.id.esqueciButton);
        auth= FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {

        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()){
            emailEditText.setError("Campo vazio");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email Invalido");
            emailEditText.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EsqueciSenha.this, "Confira seu email pra resetar sua senha", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EsqueciSenha.this, "Erro inesperado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}