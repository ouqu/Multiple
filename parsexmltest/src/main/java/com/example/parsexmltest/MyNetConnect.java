package com.example.parsexmltest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hahaha on 9/1/16.
 */
public class MyNetConnect {
    private static boolean isWIFI;
    private static boolean isMobile;

    // judge if is connected
    //should run firstly
    public static boolean isConnected(){
        ConnectivityManager connectivityManager= (ConnectivityManager) MyApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null&&networkInfo.isConnected()){
            switch (networkInfo.getType()){
                case ConnectivityManager.TYPE_MOBILE:
                    isWIFI=false;
                    isMobile=true;
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    isWIFI=true;
                    isMobile=false;
                    break;
            }
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isMobile() {
        return isMobile;
    }

    public static boolean isWIFI() {
        return isWIFI;
    }

    public InputStream getStreamFromURL(String urlString) throws IOException {
        URL url=new URL(urlString);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        connection.connect();
        return connection.getInputStream();
    }
}
