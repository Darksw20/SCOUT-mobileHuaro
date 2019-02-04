package com.example.ricardo.huaroapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Splash extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View decorView=getWindow().getDecorView();
        int uiOptions=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        /*
            Despues de 2 segundos se entra a la aplicacion
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent=new Intent(Splash.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
