package com.arvoron.scherpel.arvoron;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

public class Splash extends AppCompatActivity {
    private ImageView fonte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(this);

        fonte = (ImageView) findViewById(R.id.fonte);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.fadein);
        fonte.startAnimation(myanim);

        final Intent i = new Intent(this, SetupActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}

