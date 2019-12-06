package com.demo.postbookdb.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

/*generating splash screen using drawable resource instead of setContantView to faster application time*/
public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //not used setcontantview method as we load screen from drawable
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // close splash activity
                finish();
            }
        }, 2000);
    }
}
