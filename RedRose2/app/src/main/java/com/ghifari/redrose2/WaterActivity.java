package com.ghifari.redrose2;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.view.OrientationEventListener;


/**
 * Created by Lenovo PC on 02/23/2017.
 */

public class WaterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        TextView t_water = (TextView) findViewById(R.id.water);

        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                t_water.setText("siram");
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                t_water.setText("gasiram");
                break;

        }
    }



}