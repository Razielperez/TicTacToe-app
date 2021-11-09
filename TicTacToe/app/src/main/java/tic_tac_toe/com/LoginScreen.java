package tic_tac_toe.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ProgressBar;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        final ProgressBar loading=(ProgressBar)findViewById(R.id.loading_progressBar);
        loading.setMax(100);
        loading.setProgress(0);
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    for(int i=0;i<=100;i++){
                        loading.setProgress(i);
                        Thread.sleep(30);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(LoginScreen.this, HomeScreen.class);
                    startActivity(intent);

                }
                }
            };
        thread.start();


    }
}