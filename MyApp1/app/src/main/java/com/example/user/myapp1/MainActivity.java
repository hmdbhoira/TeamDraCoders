package com.example.user.myapp1;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Golden Hour Response - 9");


//        ActionBar actionBar;
//        actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        // TODO: Remove the redundant calls to getSupportActionBar()
//        //       and use variable actionBar instead
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);





        Button user_btn;

            user_btn = (Button) findViewById(R.id.btn_temp1);
            user_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(MainActivity.this,UserActivity.class);
                    startActivity(intent1);
                }
            });

        Button vol_btn = (Button) findViewById(R.id.btn_temp2);
        vol_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,VolunteerActivity.class);
                startActivity(intent2);
            }
        });




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

//        Button btn1 = (Button) findViewById(R.id.btn1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentlog = new Intent(MainActivity.this, LoginClass.class);
//                startActivity(intentlog);
//            }
//        });


        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginClass.class);
                startActivity(intent);
            }
        });

        // Write a message to the database


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    @Override
    public boolean onKeyLongPress(int keyCode,KeyEvent event){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
        if(keyCode== KeyEvent.KEYCODE_VOLUME_UP){
            mp.start();
//            Toast.makeText(this,"Volume Up triggered on Long Press", Toast.LENGTH_SHORT).show();

            return true;
        }
        return onKeyLongPress(keyCode,event);
    }

}
