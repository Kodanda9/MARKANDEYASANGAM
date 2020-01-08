package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPActivity extends AppCompatActivity {
    private Button submitotp;
    private EditText otpfield;
    private EditText readonly;
    private TextView resend,error6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        readonly=findViewById(R.id.phone);
        Intent i = getIntent();
        readonly.setText(i.getStringExtra("phonenumber"));
        readonly.setEnabled(false);



        resend=findViewById(R.id.resendotp);
        error6=findViewById(R.id.error6);
        resend.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ResendTaskRunner asyncTaskRunner = new ResendTaskRunner();
                                          asyncTaskRunner.execute();
                                      }
                                  });
                submitotp= (Button) findViewById(R.id.submit);
                otpfield=(EditText)  findViewById(R.id.otp);

        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otpfield.getText().toString().trim().isEmpty()){
                    otpfield.setError("Enter OTP");
                }else {
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute();
                }
            }
        });
    }
    private class AsyncTaskRunner extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;
        String otp;
        String phonenum;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(OTPActivity.this, "Please wait", "loading...");
            otp=otpfield.getText().toString();
            phonenum=readonly.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            {
                String resp = null;
                progressDialog.dismiss();
               JSONObject obj= new JSONObject();
                try {

                   obj.accumulate("mobile", phonenum);
                    obj.accumulate("otp", otp);

                 resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/checkOtp", "POST", obj);
                    progressDialog.dismiss();

                    Log.i("api100 OnPost:-->", resp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return resp ;
            }
        }
        @Override
        protected void onPostExecute(String resp) {

            progressDialog.dismiss();

            try {

                JSONObject jsonObject1=new JSONObject(resp);

                if (jsonObject1.getString("msg").equalsIgnoreCase("Please Enter Details for Signup")) {

                    Intent i=new Intent(OTPActivity.this,RegistrationActivity.class);
                    i.putExtra("readonly", readonly.getText().toString());
                    startActivity(i);
                    Toast.makeText(OTPActivity.this, "Please Enter Details", Toast.LENGTH_LONG).show();
                    otpfield.setText("");

                    progressDialog.dismiss();

               }else if(jsonObject1.getString("msg").equalsIgnoreCase("Invalid OTP")) {
                    error6.setVisibility(View.VISIBLE);
                    //Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                    otpfield.setText("");
                    progressDialog.dismiss();

                }else {
                    error6.setVisibility(View.VISIBLE);
                    Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                    otpfield.setText("");
                    progressDialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            super.onPostExecute(resp);
        }
    }
    private class ResendTaskRunner extends AsyncTask<String,String,String>{
        ProgressDialog progressDialog;
        String mobile;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(OTPActivity.this, "Please wait", "loading...");

            mobile = readonly.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/resendOtp/" +mobile, "GET", null);

            return resp;
        }
        @Override
        protected void onPostExecute(String resp) {
            Log.i(" OnPost:-->", resp);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

                if (resp.equalsIgnoreCase("true")) {

                    Toast.makeText(OTPActivity.this, "otp is not sent Please try again later", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(OTPActivity.this, "otp sent", Toast.LENGTH_LONG).show();
                }
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            super.onPostExecute(resp);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }
    }

