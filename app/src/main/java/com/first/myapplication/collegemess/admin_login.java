package com.first.myapplication.collegemess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class admin_login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email,password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.admin_email);
        password = findViewById(R.id.admin_password);
        progressBar = findViewById(R.id.progressbar);
    }

    public void onStart(){

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


    }

    public void loginAdmin(View v){
        String getEmail = email.getText().toString().trim();
        String getPassword = password.getText().toString().trim();

        if(getEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
            email.setError("Please enter a valid email.");
            email.requestFocus();
            return;
        }

        if(getPassword.length()<6){
            password.setError("Minimum Length of password should be 6");
            password.requestFocus();
            return;

        }

        if(getPassword.isEmpty()){
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent i = new Intent(admin_login.this, admin_home.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateUI(FirebaseUser user){

        if(user != null){

            Intent i = new Intent(admin_login.this, admin_home.class );
            startActivity(i);

        }
        else
        {
            Log.i("action","admin activity");
        }
    }
}
