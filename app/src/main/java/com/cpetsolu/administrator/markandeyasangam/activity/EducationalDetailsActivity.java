package com.cpetsolu.administrator.markandeyasangam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;

public class EducationalDetailsActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener  {

    Spinner education;
    EditText degree,postgradution,other,extraother;
    Button submit;
    String[] Education = {"Select Your Qualification", "Upto Intermediate(SSC to Intermediate)", "Diploma(Diploma in AnyStream)", "Graduation(B.A,B.Com,B.Sc)", "Engineering(B.Tech,BE)", "PostGraduation in Engg(M.Tech,ME,MS)", "PostGraduation(B.Tech+MBA)", "PostGraduation(M.A,MBA,M.Sc,MCA)", "Abroad(MS,MBA)","Medical(MBBS,MD,MS,DM)","Medical(BDS,MDS)","Medical(B.Pharma,M.Pharma)","Medical(BPT,MPT,Others)","Ph.D(doctorate)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_details);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        education=(Spinner)findViewById(R.id.education);
        education.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Education);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(aa);

       degree= (EditText)findViewById(R.id.degree);
        postgradution= (EditText)findViewById(R.id.postgraduation);
        other= (EditText)findViewById(R.id.other1);
        extraother= (EditText)findViewById(R.id.other2);

        submit=(Button)findViewById(R.id.educationalbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! education.getSelectedItem().toString().isEmpty()){
                    Intent i= new Intent(EducationalDetailsActivity.this,HomeActivity.class);
                    startActivity(i);

                }else {
                    Toast.makeText(EducationalDetailsActivity.this,"Select your Qualification",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
