package com.guc.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "ServiceExample";
    Handler handler;
    Runnable runnable;
    public static final String MY_SERVICE_INTENT_BROADCAST_TAG = "my_service_intent_broadcast_tag";

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Service running");
                publishMessage();
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(runnable);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        Log.i(TAG, "Service onDestroy");
    }

    public void publishMessage() {
        Intent intent = new Intent(MY_SERVICE_INTENT_BROADCAST_TAG);
        intent.putExtra("message", true);
        sendBroadcast(intent);

    }
}