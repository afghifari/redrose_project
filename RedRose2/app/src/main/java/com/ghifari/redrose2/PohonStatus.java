package com.ghifari.redrose2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Observable;
import java.util.Observer;

public class PohonStatus extends AppCompatActivity{
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohon_status);

        t_age = (TextView) findViewById(R.id.t_pohon_age);
        t_type = (TextView) findViewById(R.id.t_pohon_type);
        t_nutrition = (TextView) findViewById(R.id.t_pohon_nutrition);
        t_height = (TextView) findViewById(R.id.t_pohon_height);
        Button b_siram = (Button)findViewById(R.id.b_pohon_siram);
        Button b_tanam = (Button)findViewById(R.id.button_pohon_tanam);

        b_siram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPohon.Siram();
                t_nutrition = (TextView) findViewById(R.id.t_pohon_nutrition);
            }
        });

        b_tanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Goal = new Intent(PohonStatus.this, PlantActivity.class);
                PohonStatus.this.startActivity(intent_Goal);
            }
        });



        if(myPohon == null) {
            mPrefs = getSharedPreferences("geloman", Context.MODE_PRIVATE);
            LoadPohon();
            myPohon = ((MyApplication) getApplication()).getMyPohon();
        }

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //myPohon.GrowPohon();

                                t_height.setText(myPohon.getHeight()+"");
                                t_age.setText(myPohon.getAge()+"");
                                t_nutrition.setText(myPohon.getNutrition()+"");
                                t_type.setText(myPohon.getType()+"");

                                SavePohon();
                            }
                        });
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();


        SavePohon();
    }


    public void SavePohon()
    {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myPohon);
        prefsEditor.putString("myPohon", json);
        prefsEditor.commit();
    }

    public void LoadPohon()
    {
        Log.v("Pohon", "masuk jsonLoad");
        Gson gson = new Gson();

        if(mPrefs.contains("myPohon")) {
            String json = mPrefs.getString("myPohon", "NOTHING");
            Log.v("Pohon", "jsonLoad " + json);
            ((MyApplication) getApplication()).setMyPohon(gson.fromJson(json, Pohon.class));
        }
        else //setPreference kosong
        {
            Log.v("Pohon", "new Pohon");
            ((MyApplication) getApplication()).setMyPohon(new Pohon());
        }

        final Intent service = new Intent(getBaseContext(), NatureBackgroundService.class);
        startService(service);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.watering_can);

        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                Log.v("masuk", "configurationchanged");
                imageView.setImageResource(R.mipmap.watering_can);
                myPohon.Siram();
                t_nutrition.setText(myPohon.getNutrition()+"");
                SavePohon();
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                imageView.setImageResource(0);
                break;

        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        Log.v("activ", "AKUUUUUUUU");
        super.onSaveInstanceState(outState);
        Log.v("activ", "AKUUUUUUUU2");
        outState.putString("age",(String)t_age.getText());
        outState.putString("height",(String)t_height.getText());
        outState.putString("nutrition",(String)t_nutrition.getText());
        outState.putString("type",(String)t_type.getText());
        Log.v("activ", "AKUUUUUUUU3");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v("activ", "AKUUUUUUUU4");
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("activ", "AKUUUUUUUU5");
        Log.v("activ", savedInstanceState.getString("age"));
        t_age.setText(savedInstanceState.getString("age"));
        t_height.setText(savedInstanceState.getString("height"));
        t_nutrition.setText(savedInstanceState.getString("nutrition"));
        t_type.setText(savedInstanceState.getString("type"));
        Log.v("activ", "AKUUUUUUUU6");
    }


    private Pohon myPohon;
    TextView t_height;
    TextView t_age;
    TextView t_type;
    TextView t_nutrition;
}
