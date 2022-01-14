package com.conect.conectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.conect.conectapp.Prevalent.PreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    //Variaveis
    private static int SPLASH_SCREEN = 3000;

    Animation topAnim, botAnim;
    ImageView image, loading;
    TextView slogan;
    AnimationDrawable animationLoading;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Animações
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim= AnimationUtils.loadAnimation(this, R.anim.bot_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        slogan = findViewById(R.id.textView);
        loading = findViewById(R.id.loadingView);

        //SetAnimation
        animationLoading = (AnimationDrawable)loading.getDrawable();
        image.setAnimation(topAnim);
        slogan.setAnimation(botAnim);
        animationLoading.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                animationLoading.stop();
                finish();
            }
        },SPLASH_SCREEN);




    }




    public void startLoading(View v){
        animationLoading.start();
    }
    public void stopLoading(View v){
        animationLoading.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}