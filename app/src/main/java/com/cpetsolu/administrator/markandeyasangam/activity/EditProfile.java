package com.cpetsolu.administrator.markandeyasangam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

public class EditProfile extends AppCompatActivity {

    private Spinner blood,work;
    private RadioGroup Gender;
    private TextView Id,Name,Surname,Mobile,Email,Bloodgroup,password;

    String[] Blood = {"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    String[] Work = {"Select Work Details", "Chenetha Works", "Central Government Employee", "State  Government Employee", "Private Employee", "Large Business", "Small Business", "Retired From Employee", "Retired From Business", "Home Maker/ House Wife", "Student", "Job Search", "Others"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Gender=(RadioGroup)findViewById(R.id.genderedit);
      blood=(Spinner) findViewById(R.id.bloodedit);
      work=(Spinner) findViewById(R.id.workedit);
      Id=(TextView) findViewById(R.id.id);
      Name=(TextView)findViewById(R.id.nameedit);
        Surname=(TextView) findViewById(R.id.surName);
     //   password=(TextView) findViewById(R.id.passwordedit);
        Mobile=(TextView) findViewById(R.id.mobileedit);

    //    password.setText(SessinSave.getsessin("password",EditProfile.this));
        Surname.setText(SessinSave.getsessin("surName",EditProfile.this));
        Mobile.setText(SessinSave.getsessin("mobile",EditProfile.this));

        Id.setText(SessinSave.getsessin("id",EditProfile.this ));
Name.setText(SessinSave.getsessin("name",EditProfile.this));
//Gender.setOnCheckedChangeListener(SessinSave.getsessin("gender",EditProfile.this));


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
}
