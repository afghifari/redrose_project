package com.ghifari.redrose2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NatureBackgroundService extends Service {
    boolean goon = true;

    public NatureBackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int StartId)
    {
        new Thread(new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                Pohon myPohon = ((MyApplication) getApplication()).getMyPohon();
                while(goon)
                {
                    try {
                        Thread.sleep(10000);
                    }
                    catch(Exception e)
                    {

                    }
                    myPohon.GrowPohon();
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        goon = false;
        Log.v("service", "stop");
    }
}