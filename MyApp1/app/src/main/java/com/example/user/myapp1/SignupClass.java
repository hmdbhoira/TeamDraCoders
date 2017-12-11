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

public class SignupClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        Button logbtn = (Button) findViewById(R.id.signUpBtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlog = new Intent(SignupClass.this, MainActivity.class);
                startActivity(intentlog);
            }
        });

        TextView fgtbtn = (TextView) findViewById(R.id.already_user);
        fgtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfgt = new Intent(SignupClass.this, LoginClass.class);
                startActivity(intentfgt);
            }
        });
    }
}
