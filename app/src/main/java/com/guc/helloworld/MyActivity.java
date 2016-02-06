package com.guc.helloworld;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_FOR_C = 10;
    private BroadcastReceiver intentABroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("success")) {
                Toast.makeText(context, "Message Success Received from Intent A", Toast.LENGTH_LONG).show();
            } else if (intent.hasExtra("failure")) {
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CODE_FOR_C) {

            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(intentABroadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(intentABroadcastReceiver, new IntentFilter(
                AIntentService.A_INTENT_BROADCAST_TAG)

        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, CActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FOR_C);
            }
        });
    }

    public void openActivityB(View view) {
        Intent intent = new Intent(this, BActivity.class);
        startActivity(intent);
    }

    public void openActivityD(View view) {
        Intent intent = new Intent(this, DActivity.class);
        startActivity(intent);
    }

    public void openAIntentService(View view) {
        Intent intent = new Intent(this, AIntentService.class);
        startService(intent);
    }

    public void openStartStopService(View view) {
        Intent intent = new Intent(this, StartStopServiceActivity.class);
        startActivity(intent);
    }

    public void openLink(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com.eg/")));

    }

    public void openaActionSend(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "message");
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }


    public void showNotification(View view) {
        NotificationManager mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this);
        mBuilder.setContentTitle("Sample Notification");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setAutoCancel(true);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        Intent intent = new Intent(this, BActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, intent, 0);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(23, mBuilder.build());
    }


    public void openAsyncTask(View view) {
        Intent intent = new Intent(this, ReadWebpageAsyncTask.class);
        startActivity(intent);
    }

    public void openThreads(View view) {
        Intent intent = new Intent(this, ThreadsActivity.class);
        startActivity(intent);
    }
}
