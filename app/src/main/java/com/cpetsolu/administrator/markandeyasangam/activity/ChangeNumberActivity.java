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

public class ChangeNumberActivity extends AppCompatActivity {

    private Button changenumber1button;
    private TextView error;
    private EditText changenumer1,changenumbernew;
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        changenumber1button =findViewById(R.id.changenumber1button);
        changenumer1=findViewById(R.id.changenumer1);
        changenumbernew=findViewById(R.id.changenumernew);
        error=findViewById(R.id.error);

        changenumber1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (changenumer1.getText().toString().trim().isEmpty()){
                    changenumer1.setError("Phone Number");
                }else {
                    if (changenumer1.getText().toString().matches(MobilePattern)) {

                        ChangenumberAsyncTaskRunner changenumberAsyncTaskRunner = new ChangenumberAsyncTaskRunner();
                        changenumberAsyncTaskRunner.execute();

                    } else if (!changenumer1.getText().toString().matches(MobilePattern)) {
                        Toast.makeText(getApplicationContext(), "Enter Valid 10 digit Phone Number", Toast.LENGTH_SHORT).show();
                        changenumer1.setText("");
                        changenumer1.requestFocus();
                    }
                }
            }
        });
    }
    private class ChangenumberAsyncTaskRunner extends AsyncTask<String, String, String> {

        String mobile;
        String mobilenew;

        @Override
        protected void onPreExecute() {
            mobile = changenumer1.getText().toString();
            mobilenew = changenumbernew.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj = null;
            String resp = null;
            try {
                obj = new JSONObject();

                obj.accumulate("mobileNo", mobilenew);
                obj.accumulate("oldMobileNo", mobile);
                resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/changeMobileNo", "POST", obj);
            } catch (JSONException e) {
                e.printStackTrace();
                e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {
            Log.i("Response:->", resp);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(resp);

                if (jsonObject.getString("status").equalsIgnoreCase("SUCCESS")) {
                    Toast.makeText(ChangeNumberActivity.this, "OTP sent Successfully", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(ChangeNumberActivity.this, ForgetOtp.class);
                    i.putExtra("changenumer1", changenumer1.getText().toString());
                    i.putExtra("changenumbernew", changenumbernew.getText().toString());

                    startActivity(i);
                } else {
                    error.setVisibility(View.VISIBLE);
                    //Toast.makeText(ChangeNumberActivity.this, "OTP is not sent Please try again later.", Toast.LENGTH_LONG).show();
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
