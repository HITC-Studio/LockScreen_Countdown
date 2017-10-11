package com.software.headintheclouds.lockscreen_countdown;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{

    CountDownTimer cdt30;
    Notification.Builder notification;
    NotificationManager notificationManager;
    String timeRemaining = "30:00";
    Vibrator vibarate;
    Uri uriAlarm;
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vibrator
        vibarate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Sound
            uriAlarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            r = RingtoneManager.getRingtone(getApplicationContext(), uriAlarm);

        // Pre build the notification
        notification = new Notification.Builder(MainActivity.this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("30:00")
                .setContentText("TIME REMAINING")
                .setSmallIcon(R.drawable.warning)
                .setColor(Color.rgb(226,128,0))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.head1))
                .setVisibility(Notification.VISIBILITY_PUBLIC);

        // Setup the notification manager, show a default of 30mins
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());

        // 30 mins!
        cdt30 = new CountDownTimer( 1800000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                // Update the time remaining
                timeRemaining = String.format(Locale.ENGLISH, "%d:%s",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                // Update the notification
                notification.setContentTitle(timeRemaining);
                notificationManager.notify(1, notification.build());
            }

            @Override
            public void onFinish()
            {
                r.play();
                // Sound the alarm!!
                vibarate.vibrate(30000);
            }
        };


        Button button = (Button)findViewById(R.id.bt_start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                // Start the timer at 30 mins
                // First cancel any old ones
                cdt30.cancel();
                // Start!!
                cdt30.start();
            };
        });
    }

}
