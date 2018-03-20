package com.example.user.myapp1;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Youtube extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("First Aid Tutorials");

        tv1 = (TextView) findViewById(R.id.browser1);
        tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv1.setText("Road Accident");

        tv2 = (TextView) findViewById(R.id.browser2);
        tv2.setPaintFlags(tv2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv2.setText("Heart Attack");

        tv3 = (TextView) findViewById(R.id.browser3);
        tv3.setPaintFlags(tv3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv3.setText("Snake Bite");

        tv4 = (TextView) findViewById(R.id.browser4);
        tv4.setPaintFlags(tv4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv4.setText("Fracture");

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=EKCyHuNfAQI"));
                startActivity(intent1);
            }


        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=JT6YJXEkhPU"));
                startActivity(intent2);
            }


        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=IVIXd5_Kcsc"));
                startActivity(intent3);
            }


        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=2v8vlXgGXwE"));
                startActivity(intent4);
            }


        });


    }

}
