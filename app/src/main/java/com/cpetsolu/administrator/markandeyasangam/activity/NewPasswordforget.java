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
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPasswordforget extends AppCompatActivity {

   private EditText setforgetconfirm;
    private EditText setforget;
    private Button setpassworforget;
    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_passwordforget);

        setpassworforget= findViewById(R.id.setpassworforget);
       btn= findViewById(R.id.phone3);

        btn=findViewById(R.id.phone3);
        Intent i = getIntent();
        btn.setText(i.getStringExtra("readonly"));
        btn.setEnabled(false);
       // btn.setText(SessinSave.getsessin("mobile",NewPasswordforget.this));
        setpassworforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setforgetconfirm= findViewById(R.id.setforget);
                setforget= findViewById(R.id.setforgetconfirm);

                    if (setforget.getText().toString().trim().isEmpty()){
                        setforget.setError("New Password");
                    }else {
                        if (setforgetconfirm.getText().toString().trim().isEmpty()){
                            setforgetconfirm.setError("Confirm Password");
                        }else {

                            String a=setforget.getText().toString();
                            String b=setforgetconfirm.getText().toString();

                            if(a.equals(b))
                            {
                                saveAsyncTask finalAsyncTask = new saveAsyncTask();
                                finalAsyncTask.execute();

                            } else {
                                Toast.makeText(NewPasswordforget.this, "password not match", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        });
    }
    private class saveAsyncTask extends AsyncTask<String,String,String>{

        ProgressDialog progressDialog;
        String password;
        String confirmpassword;
        String phonenum;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(NewPasswordforget.this, "Please wait", "loading...");
            password=setforget.getText().toString();
            confirmpassword=setforgetconfirm.getText().toString();
            phonenum=btn.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String resp = null;
            progressDialog.dismiss();
            JSONObject obj= new JSONObject();

            try {

                obj.accumulate("mobileNo", phonenum);
                obj.accumulate("newPassword", password);
                obj.accumulate("confirmPw", confirmpassword);

                resp = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/storeForgotPassword", "POST", obj);
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
                JSONObject mainObj = new JSONObject(resp);
                if (mainObj.getString("status").equalsIgnoreCase("SUCCESS")) {

                    Toast.makeText(NewPasswordforget.this, "Your Password Changed Successfully", Toast.LENGTH_LONG).show();

                    SessinSave.saveSession("id","",NewPasswordforget.this);
                    SessinSave.saveSession("role","",NewPasswordforget.this);
                    SessinSave.saveSession("surName","",NewPasswordforget.this);
                    SessinSave.saveSession("name","",NewPasswordforget.this);
                    SessinSave.saveSession("gender","",NewPasswordforget.this);
                    SessinSave.saveSession("bloodGroup","",NewPasswordforget.this);
                    SessinSave.saveSession("dob","",NewPasswordforget.this);
                    SessinSave.saveSession("memberStatus","",NewPasswordforget.this);
                    SessinSave.saveSession("locality","",NewPasswordforget.this);
                    SessinSave.saveSession("assembly","",NewPasswordforget.this);
                    SessinSave.saveSession("parliament","",NewPasswordforget.this);
                    SessinSave.saveSession("mobile","",NewPasswordforget.this);

                    SessinSave.saveSession("emailView","",NewPasswordforget.this);
                    SessinSave.saveSession("aboutYou","",NewPasswordforget.this);
                    SessinSave.saveSession("aboutYouView","",NewPasswordforget.this);
                    SessinSave.saveSession("adminComments","",NewPasswordforget.this);
                    SessinSave.saveSession("mobileView","",NewPasswordforget.this);
                    SessinSave.saveSession("email","",NewPasswordforget.this);
                    SessinSave.saveSession("status","",NewPasswordforget.this);
                    SessinSave.saveSession("country","",NewPasswordforget.this);
                    SessinSave.saveSession("state","",NewPasswordforget.this);
                    SessinSave.saveSession("district","",NewPasswordforget.this);
                    SessinSave.saveSession("subDistrict","",NewPasswordforget.this);
                    SessinSave.saveSession("addressLine1","",NewPasswordforget.this);

                    finish();

                    Intent i= new Intent(NewPasswordforget.this,LoginActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(NewPasswordforget.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();

                super.onPostExecute(resp);
            }

            super.onPostExecute(resp);
        }
    }
}
