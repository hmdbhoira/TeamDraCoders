package com.example.user.myapp1;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;*/
/**
 * Created by Hammad on 15-11-2017.
 */

public class SignUpPublicClass extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static final String TAG = SignupClass.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;

    private EditText inputEmail, inputPassword, fullname, mobileNumber;
    private TextView txtName, txtEmail;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        Button logbtn = (Button) findViewById(R.id.signUpBtn);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlog = new Intent(SignUpPublicClass.this, MainActivity.class);
                startActivity(intentlog);
            }
        });
        auth = FirebaseAuth.getInstance();
        btnSignUp = (Button) findViewById(R.id.signUpBtn);
        inputEmail = (EditText) findViewById(R.id.userEmailId);
        inputPassword = (EditText) findViewById(R.id.password);
        fullname = (EditText) findViewById(R.id.fullName);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpPublicClass.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(SignupClass.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpPublicClass.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intentlog = new Intent(SignUpPublicClass.this, UserActivity.class);
                                    startActivity(intentlog);
                                    myRef = database.getReference("Users").push();
                                    myRef.child("Name").setValue(fullname.getText().toString());
                                    myRef.child("Email").setValue(inputEmail.getText().toString());
                                    myRef.child("Password").setValue(inputPassword.getText().toString());
                                    myRef.child("MobileNo").setValue(mobileNumber.getText().toString());

                                    //myRef.child("Password").setValue(inputPassword.getText().toString());
                                    Toast.makeText(SignUpPublicClass.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpPublicClass.this, UserActivity.class));
                                    finish();
                                    //String child=inputEmail.getText().toString();

                                   /* myRef=database.getReference("users").child(child);
                                    myRef.child("name").setValue(editTextname.getText().toString());
                                    myRef.child("email").setValue(inputEmail.getText().toString());
                                    myRef.child("mobile no").setValue(inputPassword.getText().toString());*/
                                }
                            }
                        });

            }
        });
    }
}


/*
package com.example.user.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

*/
/**
 * Created by Hammad on 15-11-2017.
 *//*


public class SignUpPublicClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        Button logbtn = (Button) findViewById(R.id.signUpBtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlog = new Intent(SignUpPublicClass.this, UserActivity.class);
                startActivity(intentlog);
            }
        });

        TextView fgtbtn = (TextView) findViewById(R.id.already_user);
        fgtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfgt = new Intent(SignUpPublicClass.this, LoginClass.class);
                startActivity(intentfgt);
            }
        });
    }
}
*/
