package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckOtpForgetpassword extends AppCompatActivity {

    private EditText otpforget;
    private Button checkotpforget;
    private TextView readonly,error4;
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp_forgetpassword);

        readonly=findViewById(R.id.phone2);
        Intent i1 = getIntent();
        readonly.setText(i1.getStringExtra("phonenumberforget"));
        readonly.setEnabled(false);

        otpforget=findViewById(R.id.otpforget);
        error4=findViewById(R.id.error4);
       checkotpforget= findViewById(R.id.checkotpforget);
       TextView resend=findViewById(R.id.resendotp11);
       resend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

              ResendTaskRunnerf asyncTaskRunnerf = new ResendTaskRunnerf();
               asyncTaskRunnerf.execute();
           }
       });
       checkotpforget.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (otpforget.getText().toString().trim().isEmpty()){
                   otpforget.setError("Enter OTP");
               }else {
                   CheckpasswordAsyncTask passwordAsyncTask = new CheckpasswordAsyncTask();
                   passwordAsyncTask.execute();
               }
           }
       });
    }
    private class CheckpasswordAsyncTask extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;
        String otp;
        String phonenum;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(CheckOtpForgetpassword.this, "Please wait", "loading...");
            otp=otpforget.getText().toString();
            phonenum=readonly.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            String resp = null;
            progressDialog.dismiss();
            JSONObject obj= new JSONObject();

            try {

                obj.accumulate("mobile", phonenum);
                obj.accumulate("otp", otp);

                resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/checkforgotOtp", "POST", obj);
                progressDialog.dismiss();

                Log.i("api100 OnPost:-->", resp);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resp ;
        }
        @Override
        protected void onPostExecute(String resp) {
            try {
                JSONObject jsonObject = new JSONObject(resp);

                if (jsonObject.getString("status").equalsIgnoreCase("SUCCESS"))
                {
                    Toast.makeText(CheckOtpForgetpassword.this, "You can Create your New password", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(CheckOtpForgetpassword.this, NewPasswordforget.class);
                  intent.putExtra("readonly",readonly .getText().toString());
                    startActivity(intent);

                }else {
                    error4.setVisibility(View.VISIBLE);
                    Toast.makeText(CheckOtpForgetpassword.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(resp);
        }
    }
    private class ResendTaskRunnerf  extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;
        String mobile;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(CheckOtpForgetpassword.this, "Please wait", "loading...");

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
                    error4.setVisibility(View.VISIBLE);
                    //Toast.makeText(CheckOtpForgetpassword.this, "otp is not sent Please try again later", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(CheckOtpForgetpassword.this, "OTP sent", Toast.LENGTH_LONG).show();
                }
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            super.onPostExecute(resp);
        }
    }
}
