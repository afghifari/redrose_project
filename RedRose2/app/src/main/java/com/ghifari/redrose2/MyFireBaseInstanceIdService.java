package com.ghifari.redrose2;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by Ghifari on 21/02/2017.
 */

public class MyFireBaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recent_token);
        Log.d(TAG, "Refreshed token: " + recent_token);
        System.out.println("MY TOKEN IS : "+recent_token);
    }
}
