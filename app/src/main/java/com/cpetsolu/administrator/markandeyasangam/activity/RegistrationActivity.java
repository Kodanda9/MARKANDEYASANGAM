package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {
    private TextView sup;
    private TextView act;
    private TextView fbook;
    private Button signup;
    private Button signin;
    private Button create;
    private ImageView displaydate;
    private EditText show;
    ProgressDialog progressDialog;
    private View localView;
    private String selectedGender;
    private String selectedProfession;
    private Spinner sCountry;
    private Spinner sState;
    private Spinner sDist;
    private Spinner BLloodgroup;
    private Spinner WOrkdetails;
    public static final String TAG = "Singup";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText usrusr;
    private EditText pswd;
    private EditText Mail;
    private EditText Confirmpassword;
    private EditText Assembly;
    private EditText Parmiament;
    private EditText Locality;
    private EditText Aboutyou;
    private EditText surname;
    private EditText Mobile;
    private EditText AddressLine;
    private EditText Aboutyouview;
    private RadioButton male;
    private RadioButton female;
    private RadioButton self;
    private RadioButton parent;
    private RadioButton friend;
    private RadioButton relative;
    private Spinner Gender;
    private Spinner blood1;
    private RadioGroup profile;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String MobilePattern = "[0-9]{10}";

    String[] gender1 = {"Select Gender","Male","Female"};
    String[] Blood = {"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    String[] Work = {"Select Work Details", "Chenetha Works", "Central Government Employee", "State  Government Employee", "Private Employee", "Large Business", "Small Business", "Retired From Employee", "Retired From Business", "Home Maker/ House Wife", "Student", "Job Search", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Mobile = findViewById(R.id.mobileno);
        Intent i = getIntent();
        Mobile.setText(i.getStringExtra("readonly"));
        Mobile.setEnabled(false);

        Locality = findViewById(R.id.locality);
        //    Mobile=findViewById(R.id.mobileno);
        Assembly = findViewById(R.id.assemblyconstituency);
        Parmiament = findViewById(R.id.parliamentconstituency);
        Confirmpassword = findViewById(R.id.confirmpassword);
        AddressLine = findViewById(R.id.address);
        Aboutyou = findViewById(R.id.aboutyou);

        //radio button
        // male = (RadioButton) findViewById(R.id.male);
        //  female = (RadioButton) findViewById(R.id.female);

        // self = (RadioButton) findViewById(R.id.self);
        //  parent = (RadioButton) findViewById(R.id.parent);
        // friend = (RadioButton) findViewById(R.id.friend);
        //  relative = (RadioButton) findViewById(R.id.relative);

//Radio group
        Gender = (Spinner) findViewById(R.id.genderregi111);
        Gender.setOnItemSelectedListener(this);
        ArrayAdapter aa11 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender1);
        aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(aa11);

// Spinner start
        //blood group
        BLloodgroup = (Spinner) findViewById(R.id.bloodgroup);
        BLloodgroup.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Blood);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BLloodgroup.setAdapter(aa);
//workdetails
        WOrkdetails = (Spinner) findViewById(R.id.workingdetails);
        WOrkdetails.setOnItemSelectedListener(this);
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Work);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WOrkdetails.setAdapter(aa1);
//dist
        sDist = (Spinner) findViewById(R.id.sDist);
        sDist.setOnItemSelectedListener(this);
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add(0,"Select Dist");
        spinnerArray.add(1,"Adilabad");
        spinnerArray.add(2,"Hyderabad");
        spinnerArray.add(3,"Jagtial");
        spinnerArray.add(4,"Jangaon");
        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArray);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDist.setAdapter(aa2);

        sCountry = (Spinner) findViewById(R.id.sCountry);
//country
        ArrayList<String> ar = new ArrayList<String>();
        ar.add(0, "India");
        sCountry.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ar));

//state list
        sState = (Spinner) findViewById(R.id.sState);
        sState.setOnItemSelectedListener(this);
        List<String> spinnerArray1 = new ArrayList<>();
        spinnerArray1.add(0,"Select State");
        spinnerArray1.add(1,"Telengana");
        spinnerArray1.add(2,"Andhra Pradesh");
        ArrayAdapter as = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArray1);
        as.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sState.setAdapter(as);

        signup = (Button) findViewById(R.id.signup);
        signin = (Button) findViewById(R.id.signin);
        surname = findViewById(R.id.sername);

        act = (TextView) findViewById(R.id.act);
        Mail = (EditText) findViewById(R.id.email);
        pswd = (EditText) findViewById(R.id.pswd);

       /* String pass = pswd.getText().toString();
        if(TextUtils.isEmpty(pass) || pass.length() < 6)
        {
            pswd.setError("You must have 6 characters in your password");
            return;
        }*/
        usrusr = (EditText) findViewById(R.id.name);

//account create start
        create = (Button) findViewById(R.id.createaccount);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (surname.getText().toString() != null && !surname.getText().toString().isEmpty())
                {
                    if (usrusr.getText().toString() != null && !usrusr.getText().toString().isEmpty())
                    {
                        if (pswd.getText().toString() != null && !pswd.getText().toString().isEmpty())
                        {
                            if (Gender.getSelectedItem().toString().trim().equals("Select Gender"))
                            {

                               Toast.makeText(RegistrationActivity.this,"Select Gender",Toast.LENGTH_LONG).show();
                            }else {
                                if (BLloodgroup.getSelectedItem().toString().trim().equals("Select Blood Group")){

                                    Toast.makeText(RegistrationActivity.this,"Select Blood Group",Toast.LENGTH_LONG).show();

                                }else {
                                    if (WOrkdetails.getSelectedItem().toString().trim().equals("Select Work Details")) {

                                        Toast.makeText(RegistrationActivity.this, "Select Work Details", Toast.LENGTH_LONG).show();
                                    }else {

                                        if (Mail.getText().toString().matches(emailPattern)) {

                                            AllUserAsync api = new AllUserAsync();
                                            api.execute();
                                        }else {
                                            Toast.makeText(RegistrationActivity.this,"Invalid Email",Toast.LENGTH_LONG).show();
                                        }
                            }}}
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please enter surname", Toast.LENGTH_SHORT).show();
                } }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private class AllUserAsync extends AsyncTask<String, String, String> {

        String dob, memberStatus, mobileView, emailView, aboutYouView, adminComments, status;

        @Override
        protected String doInBackground(String... strings) {
            String response = null;
            //   progressDialog.dismiss();

            JSONObject obj = new JSONObject();
            try {
                obj.accumulate("surName", surname.getText().toString());
                obj.accumulate("name", usrusr.getText().toString());
                obj.accumulate("password", pswd.getText().toString());
                obj.accumulate("gender", Gender.getSelectedItem().toString());
                obj.accumulate("bloodGroup", BLloodgroup.getSelectedItem().toString());
                obj.accumulate("dob", dob);
                obj.accumulate("memberStatus", memberStatus);
                obj.accumulate("locality", Locality.getText().toString());
                obj.accumulate("assembly", Assembly.getText().toString());
                obj.accumulate("parliament", Parmiament.getText().toString());
                obj.accumulate("mobile", Mobile.getText().toString());
                obj.accumulate("mobileView", mobileView);
                obj.accumulate("email", Mail.getText().toString());
                obj.accumulate("aboutYou", Aboutyou.getText().toString());
                obj.accumulate("emailView", emailView);
                obj.accumulate("aboutYouView", aboutYouView);
                obj.accumulate("adminComments", adminComments);
                obj.accumulate("status", status);
                obj.accumulate("country",sCountry.getSelectedItem().toString());
                obj.accumulate("state", "1");
                obj.accumulate("district","2");
                obj.accumulate("addressLine1",AddressLine.getText().toString());

                response = AsyncTaskHelper.makeServiceCall("http://www.cpetsol.com/MS/mRest/saveMemberInfo","POST", obj);
                //      progressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String response) {

            try {
                JSONObject jsonObject=new JSONObject(response);

                if (jsonObject.getString("msg").equalsIgnoreCase("User Registerted Successfully... Please Login")) {

                    Intent i= new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(i);
                    Toast.makeText(RegistrationActivity.this, "User Registerted Successfully... Please Login", Toast.LENGTH_LONG).show();
                    RegistrationActivity.this.finish();

                    surname.setText("");
                    usrusr.setText("");
                    pswd.setText("");
                    Mail.setText("");

                } else {
                    Toast.makeText(RegistrationActivity.this, "Please ...", Toast.LENGTH_LONG).show();
                }
                surname.setText("");
                usrusr.setText("");
                pswd.setText("");
                Mail.setText("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(response);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }
}






