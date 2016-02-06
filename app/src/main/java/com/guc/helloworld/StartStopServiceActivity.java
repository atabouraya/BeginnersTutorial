package com.guc.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class StartStopServiceActivity extends AppCompatActivity {

    public final String LOG_TAG = getClass().getName();
    private BroadcastReceiver myserviceBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("message")) {
                Toast.makeText(context, "Message Success Received from MyService", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myserviceBroadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myserviceBroadcastReceiver, new IntentFilter(
                MyService.MY_SERVICE_INTENT_BROADCAST_TAG)

        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_stop_service);
    }

    public void stopService(View view) {
        stopService(new Intent(this, MyService.class));
    }

    public void startService(View view) {
        startService(new Intent(this, MyService.class));
    }
}
