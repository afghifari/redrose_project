package com.ghifari.redrose2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Notification extends AppCompatActivity {

    private static int notifCount;

    private static TextView textview2;

    private BroadcastReceiver activityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String textTemp = (String) textview2.getText();
            Bundle bundle = intent.getBundleExtra("msg");

            if (notifCount == 0) {
                textview2.setText((notifCount+1) + ". " + bundle.getString("msgBody"));
                notifCount++;
            } else if (notifCount > 0 && notifCount < 10) {
                textview2.setText(textTemp + "\n" + (notifCount+1) + ". "
                        + bundle.getString("msgBody"));
                notifCount++;
            } else {
                textview2.setText((notifCount+1) + ". " + bundle.getString("msgBody"));
                notifCount = 0;
            }
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                Log.v("onConfigurationChanged", "Change to MainActivity");
                Intent intent_Goal = new Intent(Notification.this, MainActivity.class);
                Notification.this.startActivity(intent_Goal);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
         Log.v("activ", "AKUUUUUUUU");
        Log.v("activ", "AKUUUUUUUU");
        Log.v("activ", "AKUUUUUUUU");
        Log.v("activ", "AKUUUUUUUU");
        Log.v("activ", "AKUUUUUUUU");
        super.onSaveInstanceState(outState);
        Log.v("activ", "AKUUUUUUUU2");
        Log.v("activ", "AKUUUUUUUU2");
        Log.v("activ", "AKUUUUUUUU2");
        Log.v("activ", "AKUUUUUUUU2");
        Log.v("activ", "AKUUUUUUUU2");
        outState.putString("msgs",(String)textview2.getText());
        Log.v("activ", (String)textview2.getText());
        Log.v("activ", "AKUUUUUUUU3");
        Log.v("activ", "AKUUUUUUUU3");
        Log.v("activ", "AKUUUUUUUU3");
        Log.v("activ", "AKUUUUUUUU3");
        Log.v("activ", "AKUUUUUUUU3");
        Log.v("activ", "AKUUUUUUUU3");
        Log.v("activ", "AKUUUUUUUU3");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v("activ", "AKUUUUUUUU4");
        Log.v("activ", "AKUUUUUUUU4");
        Log.v("activ", "AKUUUUUUUU4");
        Log.v("activ", "AKUUUUUUUU4");
        Log.v("activ", "AKUUUUUUUU4");
        Log.v("activ", "AKUUUUUUUU4");
        Log.v("activ", "AKUUUUUUUU4");
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", savedInstanceState.getString("msgs"));
        textview2.setText(savedInstanceState.getString("msgs"));
        Log.v("activ", "AKUUUUUUUU6");
        Log.v("activ", "AKUUUUUUUU6");
        Log.v("activ", "AKUUUUUUUU6");
        Log.v("activ", "AKUUUUUUUU6");
        Log.v("activ", "AKUUUUUUUU6");
        Log.v("activ", "AKUUUUUUUU6");
        Log.v("activ", "AKUUUUUUUU6");
        Log.v("activ", "AKUUUUUUUU6");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        textview2 = (TextView) findViewById(R.id.textNotification2);
        textview2.setText(getIntent().getStringExtra("MESSAGE_NOTIFICATION"));

        IntentFilter intentFilter = new IntentFilter("ACTION_STRING_ACTIVITY");
        registerReceiver(activityReceiver, intentFilter);
    }
}
