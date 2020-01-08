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

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentpass;
    private EditText newpass;
    private TextView error2;
    private EditText confirmnewpass;

    private String   curpass,npass,conpass;

    private Button changepassword1button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        String pass=SessinSave.getsessin("password",ChangePasswordActivity.this);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    changepassword1button= findViewById(R.id.change1button11);
        error2= findViewById(R.id.error2);

        changepassword1button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentpass= findViewById(R.id.changepassword1);
                newpass= findViewById(R.id.changepassword2);
                confirmnewpass= findViewById(R.id.changepassword3);

                if (currentpass.getText().toString() != null && !currentpass.getText().toString().isEmpty()) {

                    if (newpass.getText().toString() != null && !newpass.getText().toString().isEmpty()) {

                        if (confirmnewpass.getText().toString()!=null && !confirmnewpass.getText().toString().isEmpty()) {

                            String c=currentpass.getText().toString();
                            String a=newpass.getText().toString();
                            String b=confirmnewpass.getText().toString();

                              if(a.equals(b))
                              {
                                  ChangepasswordAsyncTaskRunner changenumberAsyncTaskRunner = new ChangepasswordAsyncTaskRunner();
                                   changenumberAsyncTaskRunner.execute();
                              }
                              else
                              {
                                  Toast.makeText(ChangePasswordActivity.this, "password not match", Toast.LENGTH_LONG).show();
                              }

                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Please enter Confirm Password", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Please enter new Password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Enter Current password", Toast.LENGTH_LONG).show();
                }
            }
        });

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

    private class ChangepasswordAsyncTaskRunner extends AsyncTask<String,String,String>{

        private String currentPw, newPassword,confirmPw;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            currentPw = currentpass.getText().toString();
            newPassword = newpass.getText().toString();
            confirmPw = confirmnewpass.getText().toString();
        }
        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj1 = null;
            String content=null;
            try {
                obj1 = new JSONObject();
                obj1.accumulate("currentPw", currentPw);
                obj1.accumulate("newPassword", newPassword);
                obj1.accumulate("confirmPw", confirmPw);

                Log.i("Obj-->", obj1.toString());
                content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/changePassword/"+SessinSave.getsessin("id",ChangePasswordActivity.this ), "POST", obj1);

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

                    Toast.makeText(ChangePasswordActivity.this ,"Otp Sent to your Number", Toast.LENGTH_LONG).show();
                    Toast.makeText(ChangePasswordActivity.this ,"Enter Otp for Change Password", Toast.LENGTH_LONG).show();

                    Intent i= new Intent(ChangePasswordActivity.this,ChangePasswordOtpCheck.class);
                    startActivity(i);
                }else{
                    error2.setVisibility(View.VISIBLE);
                    Toast.makeText(ChangePasswordActivity.this ,"Otp Not Sent", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(content);
        }

    }

}



