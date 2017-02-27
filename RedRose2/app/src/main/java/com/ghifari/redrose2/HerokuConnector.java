package com.ghifari.redrose2;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by CXXXV on 25/02/2017.
 */

public class HerokuConnector implements Runnable {
    private ArrayList<String> list;

    public ArrayList<String> getseeds()
    {
        return list;
    }

    @Override
    public void run() {
        try {
            URL url = new URL("https://boiling-cove-31833.herokuapp.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;

            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            Log.v("httpurl", "test");
            Log.v("httpurl", sb.toString());

            JSONArray arr = new JSONArray(sb.toString());
            list = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++){
                list.add(arr.getString(i));
            }
            Log.v("json", list.toString());
        }
        catch (Exception e)
        {
            Log.e("httpurl", "exception", e);
        }
    }
}
