package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

public class SplashActivity extends Activity{
    Fragment fragment = null;
    String title = null;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        checkConnectivity();
    }

   private boolean checkConnectivity() {
        boolean enabled = true;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {
            Toast.makeText(getApplicationContext(), "Please Check Internet Connection...", Toast.LENGTH_SHORT).show();

            Intent i=new Intent(SplashActivity.this,Nointernet.class);
                startActivity(i);
            return false;
        } else {

            if (SessinSave.getsessin("id", SplashActivity.this).equalsIgnoreCase("")) {
                Intent in = new Intent(SplashActivity.this, NavigationActivity1.class);
                startActivity(in);
            } else {
                Intent in = new Intent(SplashActivity.this, NavigationActivity.class);
                startActivity(in);
            }
            return true;
        }
    }
}