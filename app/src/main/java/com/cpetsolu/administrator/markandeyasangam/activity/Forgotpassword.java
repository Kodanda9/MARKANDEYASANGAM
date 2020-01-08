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

import org.json.JSONException;
import org.json.JSONObject;

public class Forgotpassword extends AppCompatActivity {

    private Button sendotpforget;
    private TextView error5;
    private EditText phonenumberforget;
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    phonenumberforget=findViewById(R.id.phonenumberforget);
        error5=findViewById(R.id.error5);
    sendotpforget=findViewById(R.id.sendotpforget);

    sendotpforget.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (phonenumberforget.getText().toString().trim().isEmpty()){
            phonenumberforget.setError("Mobile Number");
        }else {

            if (phonenumberforget.getText().toString().matches(MobilePattern)) {

                ForgetpasswordAsyncTask forgetpasswordAsyncTask = new ForgetpasswordAsyncTask();
                forgetpasswordAsyncTask.execute();

            } else if (!phonenumberforget.getText().toString().matches(MobilePattern)) {
                Toast.makeText(getApplicationContext(), "Enter valid  10 Digit Mobile Number", Toast.LENGTH_SHORT).show();
                phonenumberforget.setText("");
                phonenumberforget.requestFocus();
            }
        }
    }
    });
}
    private class ForgetpasswordAsyncTask extends AsyncTask<String,String,String>{

        String mobileNo;
        @Override
        protected void onPreExecute() {

            mobileNo = phonenumberforget.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj = null;
            String resp=null ;
            try {
                obj = new JSONObject();

                obj.accumulate("mobileNo", mobileNo);
                resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/forgotPasword","POST", obj);
            } catch (JSONException e) {
                e.printStackTrace();
                e.getMessage();
            }
            return resp;
        }
        @Override
        protected void onPostExecute(String resp) {
            Log.i("Response:->",resp);
            try {
                JSONObject jsonObject = new JSONObject(resp);

                if (jsonObject.getString("status").equalsIgnoreCase("SUCCESS"))
                {
                    Toast.makeText(Forgotpassword.this, "OTP sent", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Forgotpassword.this, CheckOtpForgetpassword.class);
                    intent.putExtra("phonenumberforget",phonenumberforget .getText().toString());
                    startActivity(intent);

                }else {
                    error5.setVisibility(View.VISIBLE);
                    Toast.makeText(Forgotpassword.this, "OTP is not sent Please try Again", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
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
