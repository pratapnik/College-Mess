package com.first.myapplication.collegemess;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {

    //RatingBar rb;
    Spinner mySpinner, mySpinner2;
    BottomNavigationView bottomNavigationView;
    Button submit;
    DatabaseReference databaseFeedback;
    FirebaseAuth mAuth;
    EditText feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        databaseFeedback = FirebaseDatabase.getInstance().getReference("feedbacks");
        mAuth = FirebaseAuth.getInstance();

        submit = findViewById(R.id.submit);
        mySpinner = findViewById(R.id.feedback_type);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Feedback.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner2 = findViewById(R.id.mess);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(Feedback.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mess));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter2);


        bottomNavigationView = findViewById(R.id.navigation2);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_messmenu:
                        Intent intent = new Intent(Feedback.this, loginActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFeedback();
                feedback.setText(null);

            }
        });


    }

    private void addFeedback() {
        FirebaseUser user = mAuth.getCurrentUser();

        feedback = findViewById(R.id.enterfeedbackEditText);
        String response = feedback.getText().toString().trim();

        String emailID = user.getEmail();

        String mess = mySpinner2.getSelectedItem().toString();
        String genre = mySpinner.getSelectedItem().toString();


        if (!TextUtils.isEmpty(response)) {
            String id = databaseFeedback.push().getKey();

            feed feed = new feed(id, mess, genre, response, emailID);

            databaseFeedback.child(id).setValue(feed);

            Toast.makeText(getApplicationContext(), "Thanks for your feedback", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Please Give the feedback", Toast.LENGTH_SHORT).show();
        }

    }
}
