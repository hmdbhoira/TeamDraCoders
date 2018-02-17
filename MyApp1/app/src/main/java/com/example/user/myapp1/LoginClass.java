package com.example.user.myapp1;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by Hammad on 15-11-2017.
 */

public class LoginClass extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences settings = getSharedPreferences(LoginClass.PREFS_NAME, 0);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if(hasLoggedIn)
        {
            //Go directly to main activity.
            Intent intent = new Intent(LoginClass.this, MainActivity.class);
            startActivity(intent);
            LoginClass.this.finish();

        }
        setContentView(R.layout.login_layout);


//        class LoginActivity extends AppCompatActivity {


//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);

        //Get Firebase auth instance

        // set the view now



        //switching between activities using intents

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
                Intent intentreg = new Intent(LoginClass.this, ButtonRegisterClass.class);
                startActivity(intentreg);
            }
        });

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.login_emailid);
        inputPassword = (EditText) findViewById(R.id.login_password);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.loginBtn);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                progressBar.setVisibility(View.VISIBLE);

                //authenticate user

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginClass.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                //  progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 8) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginClass.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    SharedPreferences settings = getSharedPreferences(LoginClass.PREFS_NAME, 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = settings.edit();
                                    //Set "hasLoggedIn" to true
                                    editor.putBoolean("hasLoggedIn", true);

                                    // Commit the edits!
                                    editor.commit();
                                    Intent intent = new Intent(LoginClass.this, MainActivity.class);
                                    startActivity(intent);
                                    LoginClass.this.finish();
                                }
                            }
                        });
            }
        });

        // auth = FirebaseAuth.getInstance();



//        if (auth.getCurrentUser() != null) {
//            Toast.makeText(this,"Why No LoginClass? :(",Toast.LENGTH_SHORT).show();
//            //  startActivity(new Intent(LoginClass.this, VolunteerActivity.class));
//            finish();
    }


}





