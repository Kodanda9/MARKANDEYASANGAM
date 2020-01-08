package com.cpetsolu.administrator.markandeyasangam.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactUsActivity extends AppCompatActivity {

    private EditText edName;
    private EditText edphonenumber;
    private EditText emailValidate ;
    private EditText  edmsg;
    private Button contact_submit;
    String MobilePattern = "[0-9]{10}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        contact_submit= findViewById(R.id.contact_submit);

        contact_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  String eTmail = emailValidate .getText().toString().trim();
                edName= findViewById(R.id.edName1);
                edphonenumber= findViewById(R.id.edphonenumber1);
                emailValidate = findViewById(R.id.edEmail1);
                edmsg= findViewById(R.id.edmsg1);

                if (edName.getText().toString() != null && !edName.getText().toString().isEmpty()) {
                    if (edphonenumber.getText().toString() != null && !edphonenumber.getText().toString().isEmpty()) {
                        if (emailValidate .getText().toString() != null && !emailValidate .getText().toString().isEmpty()) {
                            if (edmsg.getText().toString() != null && !edmsg.getText().toString().isEmpty()) {
                                if (edphonenumber.getText().toString().matches(MobilePattern)) {
                                    if (emailValidate.getText().toString().matches(emailPattern)){

                                        ContactAsyncTask contactAsyncTask = new ContactAsyncTask();
                                        contactAsyncTask.execute();

                                    }else {
                                        Toast.makeText(ContactUsActivity.this,"Invalid Email",Toast.LENGTH_LONG).show();
                                    }
                                } else if (!edphonenumber.getText().toString().matches(MobilePattern)) {
                                    Toast.makeText(getApplicationContext(), "Please enter valid 10 Digit Number", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(ContactUsActivity.this, "Enter Your message or Question ", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(ContactUsActivity.this, "Enter Your Email Address", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(ContactUsActivity.this, "Enter Mobile Number", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(ContactUsActivity.this, "Please Enter Name", Toast.LENGTH_LONG).show();
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

    private class ContactAsyncTask  extends AsyncTask<String, String, String>{

        private String mobile, email,name,userQuery;

        @Override
        protected void onPreExecute() {
            mobile = edphonenumber.getText().toString();
            email = emailValidate.getText().toString();
            name = edName.getText().toString();
            userQuery = edmsg.getText().toString();

            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj1 = null;
            String content=null;
            try {
                obj1 = new JSONObject();
                obj1.accumulate("mobile", mobile);
                obj1.accumulate("email", email);
                obj1.accumulate("name", name);
                obj1.accumulate("userQuery", userQuery);

                Log.i("Obj-->", obj1.toString());
                content = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/saveUserQuery", "POST", obj1);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return content;
            }
        @Override
        protected void onPostExecute(String resp) {

            try {
                JSONObject jsonObject = new JSONObject(resp);
                if (jsonObject.getString("status").equalsIgnoreCase("Success")){
                    Toast.makeText(ContactUsActivity.this,"Your Message sent Successfully..",Toast.LENGTH_LONG).show();
                    edName.setText("");
                    edphonenumber.setText("");
                    emailValidate.setText("");
                    edmsg.setText("");
                    edName.requestFocus();

                }else {
                    Toast.makeText(ContactUsActivity.this,"Enter the Message clearly",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(resp);
        }
    }
}
