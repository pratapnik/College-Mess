package com.first.myapplication.collegemess;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Notification.DEFAULT_SOUND;
import static com.first.myapplication.collegemess.App.CHANNEL_1_ID;

public class AlarmReceiver extends BroadcastReceiver {


    DatabaseReference databaseMess;

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (intent.getAction().equals("nikhil.action.DISPLAY_NOTIFICATION")) {


            Intent notificationIntent = new Intent(context, loginActivity.class);

            databaseMess = FirebaseDatabase.getInstance().getReference("MessMenu");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(loginActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                databaseMess.child("Thursday").child("Lunch").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_1_ID);

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
                        mBuilder.setContentIntent(pendingIntent);


                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, mBuilder.build());


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }


        }
    }
}
