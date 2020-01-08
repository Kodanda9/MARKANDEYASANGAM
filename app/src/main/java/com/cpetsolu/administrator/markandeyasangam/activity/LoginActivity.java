package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.Dialog;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private TextView fbook, acc, sin, forgetpassword,invalid;
    private View localView;
    private Button signin;
    private Button signup;
    public ListView listview;
    private Dialog mDialog;
    private EditText phoneno, pswd;
    private TextView forgot;
    String MobilePattern = "[0-9]{10}";
    String startpartten="[6,7,8,9]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        invalid=(TextView) findViewById(R.id.invalid);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        forgetpassword = findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Forgotpassword.class);
                startActivity(intent);
            }
        });
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(it);
            }
        });
        acc = (TextView) findViewById(R.id.act);
        phoneno = (EditText) findViewById(R.id.phonenumber1);
        pswd = (EditText) findViewById(R.id.pswd1);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneno.getText().toString() != null && !phoneno.getText().toString().isEmpty()) {

                    if (phoneno.getText().toString().matches(MobilePattern)) {

                        if (pswd.getText().toString() != null && !pswd.getText().toString().isEmpty()) {

                            LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
                            loginAsyncTask.execute();

                            phoneno.setText("");
                            pswd.setText("");
                            phoneno.requestFocus();
                        } else if (pswd.getText().toString().isEmpty()) {
                            Toast.makeText(LoginActivity.this, "Please enter pasasword", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!phoneno.getText().toString().matches(MobilePattern)) {
                        Toast.makeText(LoginActivity.this, "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
                        phoneno.setText("");
                        phoneno.requestFocus();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private class LoginAsyncTask extends AsyncTask<String, String, String> {

        private String mobno, password;
        ProgressDialog progressDialog;
        String ProfileRole;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "loading...");
            mobno = phoneno.getText().toString();
            password = pswd.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj = null;
            String content=null;
            try {
                obj = new JSONObject();
                obj.accumulate("mobile", mobno);
                obj.accumulate("password", password);


                content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/checkLogin", "POST", obj);

                Log.i("checkObj-->", content.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
        }
        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            try {
                JSONArray mainArray = new JSONArray(s);

                JSONObject stausObj = mainArray.getJSONObject(0);

                JSONObject detailsObj = mainArray.getJSONObject(1);

                if (stausObj.getString("Status").equalsIgnoreCase("Success")) {
                    Toast.makeText(LoginActivity.this, "Welcome "+ SessinSave.getsessin("name",LoginActivity.this)+SessinSave.getsessin("surName",LoginActivity.this), Toast.LENGTH_LONG).show();

                    SessinSave.saveSession("LogInObj", detailsObj.toString(), LoginActivity.this);

                    SessinSave.saveSession("id", detailsObj.getString("id"), LoginActivity.this);
                    SessinSave.saveSession("surName", detailsObj.getString("surName"), LoginActivity.this);
                    SessinSave.saveSession("name", detailsObj.getString("name"), LoginActivity.this);
                    SessinSave.saveSession("gender", detailsObj.getString("gender"), LoginActivity.this);
                    SessinSave.saveSession("bloodGroup", detailsObj.getString("bloodGroup"), LoginActivity.this);
                    SessinSave.saveSession("locality", detailsObj.getString("locality"), LoginActivity.this);
                    SessinSave.saveSession("assembly", detailsObj.getString("assembly"), LoginActivity.this);
                    SessinSave.saveSession("parliament", detailsObj.getString("parliament"), LoginActivity.this);
                    SessinSave.saveSession("mobile", detailsObj.getString("mobile"), LoginActivity.this);
                    SessinSave.saveSession("password", detailsObj.getString("password"), LoginActivity.this);
                    SessinSave.saveSession("addressLine1", detailsObj.getString("addressLine1"), LoginActivity.this);
                    SessinSave.saveSession("country", detailsObj.getString("country"), LoginActivity.this);
                    SessinSave.saveSession("district", detailsObj.getString("district"), LoginActivity.this);
                    SessinSave.saveSession("email", detailsObj.getString("email"), LoginActivity.this);
                    SessinSave.saveSession("state", detailsObj.getString("state"), LoginActivity.this);
                    SessinSave.saveSession("aboutYou", detailsObj.getString("aboutYou"), LoginActivity.this);

                    SessinSave.saveSession("emailView", detailsObj.getString("emailView"), LoginActivity.this);
                    SessinSave.saveSession("aboutYouView", detailsObj.getString("aboutYouView"), LoginActivity.this);
                    SessinSave.saveSession("adminComments", detailsObj.getString("adminComments"), LoginActivity.this);
                    SessinSave.saveSession("mobileView", detailsObj.getString("mobileView"), LoginActivity.this);
                    SessinSave.saveSession("status", detailsObj.getString("status"), LoginActivity.this);
                    SessinSave.saveSession("subDistrict", detailsObj.getString("subDistrict"), LoginActivity.this);
                    SessinSave.saveSession("role", detailsObj.getString("role"), LoginActivity.this);
                    SessinSave.saveSession("dob", detailsObj.getString("dob"), LoginActivity.this);
                    SessinSave.saveSession("memberStatus", detailsObj.getString("memberStatus"), LoginActivity.this);

                    Intent in = new Intent(LoginActivity.this, NavigationActivity.class);
                    startActivity(in);

                    LoginActivity.this.finish();

                }else if(stausObj.getString("STATUS").equalsIgnoreCase("ERROR")&&
                        (stausObj.getString("Report").equalsIgnoreCase("InValid Credentials"))){

                    invalid.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Invalid Credential ", Toast.LENGTH_LONG).show();
                }
                else {
                    invalid.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Invalid Credential ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }
}






