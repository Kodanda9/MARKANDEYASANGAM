package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

public class Nointernet extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nointernet);

        Button retry=findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();
                Toast.makeText(getApplicationContext(), "Please Check Internet Connection...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean checkConnectivity() {
        boolean enabled = true;


        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {
            Toast.makeText(getApplicationContext(), "Please Check Internet Connection...", Toast.LENGTH_SHORT).show();
           // progressDialog = ProgressDialog.show(Nointernet.this, "Please wait", "loading...");
            return false;
        } else {

            if (SessinSave.getsessin("id", Nointernet.this).equalsIgnoreCase("")) {
                Intent in = new Intent(Nointernet.this, NavigationActivity1.class);
                startActivity(in);
                progressDialog = ProgressDialog.show(Nointernet.this, "Please wait", "loading...");

            } else {
                Intent in = new Intent(Nointernet.this, NavigationActivity.class);
                startActivity(in);
                progressDialog = ProgressDialog.show(Nointernet.this, "Please wait", "loading...");

            }
            return true;
        }

    }
}
