package com.first.myapplication.collegemess;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.app.Notification.DEFAULT_SOUND;
import static com.first.myapplication.collegemess.App.CHANNEL_1_ID;

public class admin_home extends AppCompatActivity {

    ProgressBar progressBar;
    Spinner mySpinner, timeSpinner;
    BottomNavigationView bottomNavigationView;
    EditText menuItem;
    Spinner day;
    Spinner meal;
    Button update, notify;
    DatabaseReference databaseMess;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        progressBar = findViewById(R.id.progressbar);
        timeSpinner = findViewById(R.id.meal);
        mySpinner = findViewById(R.id.Day);
        databaseMess = FirebaseDatabase.getInstance().getReference("MessMenu");
        menuItem = findViewById(R.id.menuItem);
        day =findViewById(R.id.Day);
        meal = findViewById(R.id.meal);
        update = findViewById(R.id.updateItem);
        notify = findViewById(R.id.notify);


//        createNotificationChannel();





        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(admin_home.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(admin_home.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);


        bottomNavigationView = findViewById(R.id.navigation);




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Logout:
                        FirebaseAuth.getInstance().signOut();

                        Intent i = new Intent(admin_home.this,admin_login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        return true;

                    case R.id.Feedbacks:
                        Intent intent = new Intent(admin_home.this,admin_feedbacks.class);
                        startActivity(intent);
                        return true;
//                    case R.id.nav_notifications:
//                        return true;
                }
                return false;
            }

        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                menuItem.setText(null);
            }
        });

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     notification();
            }
        });
    }


    private void update(){
        String weekDay = day.getSelectedItem().toString();
        String mealType = meal.getSelectedItem().toString();
        String item = menuItem.getText().toString().trim();

        if(!TextUtils.isEmpty(item)){

            messmenu menu = new messmenu(item);
            databaseMess.child(weekDay).child(mealType).setValue(menu);
            Toast.makeText(this, "Mess Menu updated", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Please enter Menu Items!", Toast.LENGTH_LONG).show();
        }
    }

    private void notification(){

        String weekDay = day.getSelectedItem().toString();
        String mealType = meal.getSelectedItem().toString();

        databaseMess.child(weekDay).child(mealType).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(admin_home.this, CHANNEL_1_ID );

                messmenu changedPost = dataSnapshot.getValue(messmenu.class);


                String item = changedPost.getMenuItem();
                mBuilder.setSmallIcon(R.drawable.cmicon);
                mBuilder.setContentTitle("Upcoming Meal");
                mBuilder.setContentText(item);
                mBuilder.setTicker("You have new notification");
                mBuilder.setWhen(System.currentTimeMillis());
                mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(item));
                mBuilder.setAutoCancel(true);
                mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                mBuilder.setDefaults(DEFAULT_SOUND);



                Intent notificationIntent = new Intent(admin_home.this, admin_home.class);
                PendingIntent contentIntent = PendingIntent.getActivity(admin_home.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(contentIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, mBuilder.build());

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "College Mess";
//            String description = "College mess notification channel";
//            String CHANNEL_ID = "5";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


}
