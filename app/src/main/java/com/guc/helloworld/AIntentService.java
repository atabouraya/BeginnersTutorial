package com.guc.helloworld;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Ahmed on 2/6/2016.
 */
public class AIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String A_INTENT_BROADCAST_TAG = "a_intent_broadcast_tag";

    public AIntentService() {
        super("AIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            publishSuccess();
        }
    }

    public void publishSuccess() {
        Intent intent = new Intent(A_INTENT_BROADCAST_TAG);
        intent.putExtra("success", true);
        sendBroadcast(intent);

    }
}
