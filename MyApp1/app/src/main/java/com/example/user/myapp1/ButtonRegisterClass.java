package com.example.user.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 12/12/2017.
 */

public class ButtonRegisterClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_register);

        Button logbtn = (Button) findViewById(R.id.VolBtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlog = new Intent(ButtonRegisterClass.this, SignupClass.class);
                startActivity(intentlog);
            }
        });

        Button logbtn1 = (Button) findViewById(R.id.PubBtn);
        logbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlog = new Intent(ButtonRegisterClass.this, SignUpPublicClass.class);
                startActivity(intentlog);
            }
        });
    }
}
