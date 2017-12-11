package com.example.user.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Hammad on 15-11-2017.
 */

public class LoginClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //switching between activities using intents
        Button logbtn = (Button) findViewById(R.id.loginBtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlog = new Intent(LoginClass.this, MainActivity.class);
                startActivity(intentlog);
            }
        });

        TextView fgtbtn = (TextView) findViewById(R.id.forgot_password);
        fgtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfgt = new Intent(LoginClass.this, ForgotPasswordClass.class);
                startActivity(intentfgt);
            }
        });

        TextView regbtn = (TextView) findViewById(R.id.createAccount);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentreg = new Intent(LoginClass.this, SignupClass.class);
                startActivity(intentreg);
            }
        });

    }

}
