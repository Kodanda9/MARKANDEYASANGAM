package com.cpetsolu.administrator.markandeyasangam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cpetsolu.administrator.markandeyasangam.R;

public class EMPLOYMENTDETAILS extends AppCompatActivity implements AdapterView.OnItemSelectedListener   {
    private Spinner WOrkdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employmentdetails);

        String[] Work = {"Select Work Details", "Chenetha Works", "Central Government Employee", "State  Government Employee", "Private Employee", "Large Business", "Small Business", "Retired From Employee", "Retired From Business", "Home Maker/ House Wife", "Student", "Job Search", "Others"};

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        WOrkdetails = (Spinner) findViewById(R.id.employeementspinner);
        WOrkdetails.setOnItemSelectedListener(this);
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Work);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WOrkdetails.setAdapter(aa1);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
