package com.example.androidregisterandlogin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.EasyEditSpan;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(StartActivity.this)
                .withFullScreen()
                .withTargetActivity(RecyclerActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundColor(Color.parseColor("#FFFFFF"))
                .withFooterText("SERVICES")
                .withLogo(R.mipmap.lgo);

        config.getFooterTextView().setTextSize(30);
        config.getFooterTextView().setTextColor(Color.BLUE);
        config.getFooterTextView().setHeight(200);

        getSupportActionBar().hide();



        View starter = config.create();
        setContentView(starter);
    }
}
