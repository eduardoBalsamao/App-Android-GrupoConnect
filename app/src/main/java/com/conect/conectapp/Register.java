package com.conect.conectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.conect.conectapp.Prevalent.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private Button registerButton;
    private EditText nameEdit, emailEdit, passwordEdit, confimationEdit;
    private FirebaseAuth auth;
    ImageView loading;
    AnimationDrawable animationLoading;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        startComponents();
        eventoClicks();

    }

    private void eventoClicks(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString().trim();
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String confirmation = confimationEdit.getText().toString().trim();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmation.isEmpty()){
                    alert("existem campos vazios");
                }else{
                    if (confirmation.equals(password)){
                        criarUser(name,email,password);
                    }else {
                        alert("Senhas diferentes");
                    }
                }

            }
        });

    }

    private void criarUser(String name, String email, String password) {
        animationLoading.start();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    User user = new User(name, email, password);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                auth.signInWithEmailAndPassword(email,password)
                                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    user.sendEmailVerification();
                                                    alert("Confira seu email para confirmar a conta");
                                                }
                                            }
                                        });
                                alert("Usuario Cadastrado com sucesso");
                                animationLoading.stop();
                                Intent i = new Intent(Register.this, Login.class);
                                startActivity(i);
                                finish();
                            }
                            else{ alert("Erro de cadastro"); }
                        }
                    });
                } else {
                    alert("Erro de cadastro");
                }
            }
        });
    }



    private void alert(String msg){
        Toast.makeText(Register.this,msg,Toast.LENGTH_SHORT).show();
    }


    private void startComponents(){
        loading = findViewById(R.id.loadingView);
        animationLoading = (AnimationDrawable)loading.getDrawable();
        registerButton = findViewById(R.id.registerButton);
        nameEdit = findViewById(R.id.registerName);
        emailEdit = findViewById(R.id.registerEmail);
        passwordEdit = findViewById(R.id.registerPassword);
        confimationEdit = findViewById(R.id.registerPasswordconfimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}