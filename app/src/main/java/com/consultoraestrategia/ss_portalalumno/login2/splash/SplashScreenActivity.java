package com.consultoraestrategia.ss_portalalumno.login2.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.MainActivity2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.logo2)
    ImageView logo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getString(R.string.app_name).equals("Educar Student")){
            getTheme().applyStyle(R.style.SplashThemeEducar, true);
        }else{
            getTheme().applyStyle(R.style.SplashThemeEVA, true);

        }
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        if(getResources().getString(R.string.app_name).equals("Educar Student")){
            Glide.with(logo)
                    .load(R.drawable.educar_splash)
                    .into(logo);
            Glide.with(logo2)
                    .load(R.drawable.educar_icono_splash)
                    .into(logo2);
        }else{
            Glide.with(logo)
                    .load(R.drawable.icrm_splash_2)
                    .into(logo);
            logo2.setVisibility(View.GONE);
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, MainActivity2.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 4000);
    }
}
