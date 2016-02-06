package com.guc.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CActivity extends AppCompatActivity {
    public final static String KEY_MESSAGE = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
    }

    public void goBackTOMyActivity(View view) {
        Intent intent = new Intent();
        intent.putExtra(KEY_MESSAGE, "Hello from C");
        setResult(RESULT_OK, intent);
        finish();
    }
}
