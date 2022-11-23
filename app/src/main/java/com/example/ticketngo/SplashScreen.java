package com.example.ticketngo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
//By:Diego Cobos
public class SplashScreen extends AppCompatActivity {
    LinearLayout splashScreenLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        Runnable last = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, SplashScreen1.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(100, 0);
                finish();
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(last,800);
    }
}