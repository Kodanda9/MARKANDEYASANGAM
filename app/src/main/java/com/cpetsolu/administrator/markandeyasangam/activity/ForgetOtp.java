package com.cpetsolu.administrator.markandeyasangam.activity;

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
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetOtp extends AppCompatActivity {

   public TextView readonly,resend11,error1;
   public TextView resend;
   public Button sendotpforget11;
    public EditText phonenumberforget11;
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_otp);

        sendotpforget11 = findViewById(R.id.sendotpforget11);
        phonenumberforget11 = findViewById(R.id.phonenumberforget11);
        error1 = findViewById(R.id.error1);

        sendotpforget11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phonenumberforget11.getText().toString().trim().isEmpty()){
                    phonenumberforget11.setError("Enter OTP");
                }else {

                    ForgetAsyncTaskRunner Runner = new ForgetAsyncTaskRunner();
                    Runner.execute();
                }
            }
        });
        resend11=findViewById(R.id.phone111111);
        Intent i1 = getIntent();
        resend11.setText(i1.getStringExtra("changenumbernew"));
        resend11.setEnabled(false);

        readonly = findViewById(R.id.phone1111);
        Intent i = getIntent();
        readonly.setText(i.getStringExtra("changenumer1"));
        readonly.setEnabled(false);

        resend = findViewById(R.id.hiiiii);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
                asyncTaskRunner.execute();
            }
        });
    }
    private class ForgetAsyncTaskRunner extends AsyncTask<String,String,String>{

           String mobile;
           String otp;

        @Override
        protected void onPreExecute() {
            mobile=readonly.getText().toString().trim();
            otp=phonenumberforget11.getText().toString().trim();

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
                content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/checkOtpChangeMobileNo/"+SessinSave.getsessin("id",ForgetOtp.this), "POST", obj1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
        }
        @Override
        protected void onPostExecute(String content) {

            try {
                JSONObject mainObj=new JSONObject(content);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){

                    Toast.makeText(ForgetOtp.this ,"Your Number Changed Successfully ", Toast.LENGTH_LONG).show();
                    Toast.makeText(ForgetOtp.this ,"Login Again With New Number ", Toast.LENGTH_LONG).show();

                    SessinSave.saveSession("id","",ForgetOtp.this);
                    SessinSave.saveSession("role","",ForgetOtp.this);
                    SessinSave.saveSession("surName","",ForgetOtp.this);
                    SessinSave.saveSession("name","",ForgetOtp.this);
                    SessinSave.saveSession("gender","",ForgetOtp.this);
                    SessinSave.saveSession("bloodGroup","",ForgetOtp.this);
                    SessinSave.saveSession("dob","",ForgetOtp.this);
                    SessinSave.saveSession("memberStatus","",ForgetOtp.this);
                    SessinSave.saveSession("locality","",ForgetOtp.this);
                    SessinSave.saveSession("assembly","",ForgetOtp.this);
                    SessinSave.saveSession("parliament","",ForgetOtp.this);
                    SessinSave.saveSession("mobile","",ForgetOtp.this);

                    SessinSave.saveSession("emailView","",ForgetOtp.this);
                    SessinSave.saveSession("aboutYou","",ForgetOtp.this);
                    SessinSave.saveSession("aboutYouView","",ForgetOtp.this);
                    SessinSave.saveSession("adminComments","",ForgetOtp.this);
                    SessinSave.saveSession("mobileView","",ForgetOtp.this);
                    SessinSave.saveSession("email","",ForgetOtp.this);
                    SessinSave.saveSession("status","",ForgetOtp.this);
                    SessinSave.saveSession("country","",ForgetOtp.this);
                    SessinSave.saveSession("state","",ForgetOtp.this);
                    SessinSave.saveSession("district","",ForgetOtp.this);
                    SessinSave.saveSession("subDistrict","",ForgetOtp.this);
                    SessinSave.saveSession("addressLine1","",ForgetOtp.this);

                    finish();
                    Intent i= new Intent(ForgetOtp.this,LoginActivity.class);
                    startActivity(i);
                }else{
                    error1.setVisibility(View.VISIBLE);
                    Toast.makeText(ForgetOtp.this ,"Invalid OTP", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(content);
        }
    }
    private class AsyncTaskRunner extends AsyncTask<String,String,String>{

        String mobile;
        String mobileresend;

        @Override
        protected void onPreExecute() {
            mobile=readonly.getText().toString();
            mobileresend=resend11.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            String resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/resendOtp/" +mobileresend, "GET", null);

            return resp;
        }
        @Override
        protected void onPostExecute(String s) {

            if (s.equalsIgnoreCase("true")) {
                error1.setVisibility(View.VISIBLE);
                //Toast.makeText(ForgetOtp.this, "Ootp is not sent Please try again later", Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(ForgetOtp.this, "OTP sent Successfully", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }
}
