package com.conect.conectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.conect.conectapp.Prevalent.PreferenceUtils;
import com.conect.conectapp.Prevalent.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    private Button registerButton, loginButton, esqueciButton;
    private EditText emailEdit, passwordEdit;
    private CheckBox remember;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startComponents();
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");



        esqueciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent esqueci = new Intent(Login.this, EsqueciSenha.class);
                startActivity(esqueci);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login.this, Register.class);
                startActivity(register);

            }
        });
    }

    private void LoginUser() {
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email vazio..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Digite sua senha...", Toast.LENGTH_SHORT).show();
        } else {
            login(email, password);
        }
    }

    private void login(String email, String senha) {

        if (remember.isChecked()) {
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(email, senha);


        }
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USERSESSION);
                                sessionManager.createLoginSession(email, senha);
                                Intent i = new Intent(Login.this, Dashboard.class);
                                startActivity(i);
                            } else {
                                alert("Confira seu e-mail para verificar sua conta");
                            }
                        } else {
                            alert("E-mail ou senha incorretos");
                        }
                    }
                });
    }


    private void alert(String s) {
        Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    private void startComponents() {
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.goButton);
        esqueciButton = findViewById(R.id.forgetButton);
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);
        remember = findViewById(R.id.remember);

        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()) {
            HashMap<String, String> remembermeDatails = sessionManager.getRememberMeDetailFromSession();
            emailEdit.setText(remembermeDatails.get(SessionManager.KEY_SESSIONEMAIL));
            passwordEdit.setText(remembermeDatails.get(SessionManager.KEY_SESSIOPASSWORD));
        }

    }
}