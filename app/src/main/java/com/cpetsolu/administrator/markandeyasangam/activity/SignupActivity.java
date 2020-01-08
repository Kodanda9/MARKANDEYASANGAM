package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    private TextView sup, act, fbook,otp;
    private Button signup, signin;
    private Button sendotp;
    private ImageView displaydate;
    private EditText show;
    private View localView;
    private String selectedGender;
    private String selectedProfession;
    private Spinner sCountry, sState, sDist;
    public static final String TAG = "Singup";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText phonenumber;
    String MobilePattern = "[0-9]{10}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//date of birth end
        signup = (Button) findViewById(R.id.signup);
        signin = (Button) findViewById(R.id.signin);
        otp=(TextView)findViewById(R.id.otp);
        act = (TextView) findViewById(R.id.act);
        phonenumber = (EditText) findViewById(R.id.phonenumber);

//account create start
        sendotp = (Button) findViewById(R.id.sendotp);
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phonenumber.getText().toString().trim().isEmpty()){
                    phonenumber.setError("Phone Number");
                }else {
                    validation();
            }
}
});

//account create end
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });
    }
public   boolean validation(){
    if (phonenumber.getText().toString().matches(MobilePattern)) {

        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
        asyncTaskRunner.execute();

    } else if (!phonenumber.getText().toString().matches(MobilePattern)) {
        Toast.makeText(getApplicationContext(), "Enter valid 10 digit Phone Number", Toast.LENGTH_SHORT).show();
        phonenumber.setText("");
        phonenumber.requestFocus();
    }

    return false;
}
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        String mobile;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(SignupActivity.this, "Please wait", "loading...");

            mobile = phonenumber.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj = null;
            String resp = null;
            try {
                obj = new JSONObject();

                obj.accumulate("mobile", mobile);
                resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/sendOtp", "POST", obj);

            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
            return resp;
        }
        @Override
        protected void onPostExecute(String resp) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                JSONObject jsonObject = new JSONObject(resp);

                if (jsonObject.getString("msg").equalsIgnoreCase("Please Enter OTP for Further Process"))
                {
                    Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
                    intent.putExtra("phonenumber", phonenumber.getText().toString());
                    startActivity(intent);
                    Toast.makeText(SignupActivity.this, "otp sent successfully", Toast.LENGTH_LONG).show();
                    phonenumber.setText("");

                }else if (jsonObject.getString("msg").equalsIgnoreCase("Mobile Number Already Exists")){
                   // Toast.makeText(SignupActivity.this, "Number is Already Exists", Toast.LENGTH_LONG).show();

                    otp.setVisibility(View.VISIBLE);
                   // Intent i= new Intent(SignupActivity.this,LoginActivity.class);
                   // startActivity(i);
                    phonenumber.setText("");
                }else{
                    Toast.makeText(SignupActivity.this, "invalid", Toast.LENGTH_LONG).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }
}


