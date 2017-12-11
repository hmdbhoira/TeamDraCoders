package com.example.user.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Hammad on 15-11-2017.
 */

public class ForgotPasswordClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword_layout);

        TextView fgtbtn = (TextView) findViewById(R.id.backToLoginBtn);
        fgtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfgt = new Intent(ForgotPasswordClass.this, LoginClass.class);
                startActivity(intentfgt);
            }
        });

        TextView submit = (TextView) findViewById(R.id.forgot_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForgotPasswordClass.this, "Check Your E-mail", Toast.LENGTH_LONG).show();
            }
        });
    }
}