package com.ghifari.redrose2;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by CXXXV on 23/02/2017.
 */

public class MyApplication extends Application {
    private int count = 0;

    public Pohon getMyPohon() {
        return myPohon;
    }

    public void setMyPohon(Pohon myPohon) {
        this.myPohon = myPohon;
    }

    private Pohon myPohon;

    public int getCount()
    {
        return count;
    }

    public void setCount(int x)
    {
        count = x;
    }

    public void inrCount()
    {
        count++;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    private FirebaseAuth mAuth;
}
