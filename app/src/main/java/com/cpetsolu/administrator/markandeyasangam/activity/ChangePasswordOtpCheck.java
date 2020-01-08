package com.cpetsolu.administrator.markandeyasangam.activity;

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
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordOtpCheck extends AppCompatActivity {
    private TextView checknumber1,resendotppassword,error3;
    private EditText checkotp1;
    private Button checkbutton1;
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_otp_check);


        checknumber1 = findViewById(R.id.checknumber1);
        error3 = findViewById(R.id.error3);

        resendotppassword=findViewById(R.id.resendotppassword);
        resendotppassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner1 Runner = new AsyncTaskRunner1();
                Runner.execute();

            }
        });
        checknumber1.setText(SessinSave.getsessin("mobile", ChangePasswordOtpCheck.this));
      /*  checknumber1=findViewById(R.id.phone2);
        Intent i1 = getIntent();
        checknumber1.setText(i1.getStringExtra("phonenumberforget"));
        checknumber1.setEnabled(false);*/

        checkbutton1 = findViewById(R.id.checkbutton1);
        checkbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkotp1 = findViewById(R.id.checkotp1);
                if (checkotp1.getText().toString() != null && !checkotp1.getText().toString().isEmpty()) {

                    //   if (checkotp1.getText().toString().matches(MobilePattern)) {

                    ChangepasswordOtp1AsyncTaskRunner Runner = new ChangepasswordOtp1AsyncTaskRunner();
                    Runner.execute();

                 /*else if (!checkotp1.getText().toString().matches(MobilePattern)) {
                        Toast.makeText(getApplicationContext(), "Please enter valid  6 Digit Otp", Toast.LENGTH_SHORT).show();
                        checkotp1.setText("");
                        checkotp1.requestFocus();
                    }*/

                }else {
                    Toast.makeText(ChangePasswordOtpCheck.this,"Enter the Otp ",Toast.LENGTH_LONG).show();
                }
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }
    private class AsyncTaskRunner1 extends AsyncTask<String,String,String>{

        String mobile;
        String mobileresend;

        @Override
        protected void onPreExecute() {
            mobile=checknumber1.getText().toString();
       //     mobileresend=resend11.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            String resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/resendOtp/" +mobile, "GET", null);

            return resp;
        }
        @Override
        protected void onPostExecute(String s) {

            if (s.equalsIgnoreCase("true")) {
                error3.setVisibility(View.VISIBLE);
                Toast.makeText(ChangePasswordOtpCheck.this, "otp is not sent Please try again later", Toast.LENGTH_LONG).show();

            }else {

                Toast.makeText(ChangePasswordOtpCheck.this, "Otp Sent", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }
    private class ChangepasswordOtp1AsyncTaskRunner extends AsyncTask<String, String, String> {

        String mobile, otp;

        @Override
        protected void onPreExecute() {

            mobile = checknumber1.getText().toString();
            otp = checkotp1.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj1 = null;
            String content = null;
            try {
                obj1 = new JSONObject();

                obj1.accumulate("mobile", mobile);
                obj1.accumulate("otp", otp);

                Log.i("Obj-->", obj1.toString());
                content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/checkChangeOtp", "POST", obj1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
        }
        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject mainObj = new JSONObject(content);
                if (mainObj.getString("status").equalsIgnoreCase("SUCCESS")) {

                    Toast.makeText(ChangePasswordOtpCheck.this, "Your Password has changed Successfully. ", Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(ChangePasswordOtpCheck.this,NavigationActivity.class);
                    startActivity(intent);
                } else {
                    error3.setVisibility(View.VISIBLE);
                    Toast.makeText(ChangePasswordOtpCheck.this, "Invalid OTP", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();

                super.onPostExecute(content);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

