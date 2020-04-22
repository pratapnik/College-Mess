package com.first.myapplication.collegemess;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class admin_feedbacks extends AppCompatActivity {

    ListView listViewFeedbacks;
    DatabaseReference databaseFeedback;
    List<feed> feedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedbacks);

        listViewFeedbacks = findViewById(R.id.listViewFeedbacks);
        databaseFeedback = FirebaseDatabase.getInstance().getReference("feedbacks");
        feedList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()) {
                    feed feed = feedbackSnapshot.getValue(feed.class);
                    feedList.add(0, feed);

                }
                FeedbacksList adapter = new FeedbacksList(admin_feedbacks.this, feedList);
                listViewFeedbacks.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
