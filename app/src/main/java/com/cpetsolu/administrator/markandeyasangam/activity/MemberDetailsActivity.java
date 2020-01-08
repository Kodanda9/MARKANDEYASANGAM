package com.cpetsolu.administrator.markandeyasangam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpetsolu.administrator.markandeyasangam.R;


public class MemberDetailsActivity extends AppCompatActivity {

    private ImageView image;
    private TextView name,work,address,mobilenumber;
    private TextView surname1,email1,gender1,memberstatus1,bloodgroup1,
    district1,state1,country1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        surname1=findViewById(R.id.surname1);
        email1=findViewById(R.id.email1);
        gender1=findViewById(R.id.gender1);
        memberstatus1=findViewById(R.id.memberstatus1);
        bloodgroup1=findViewById(R.id.bloodgroup1);
        district1=findViewById(R.id.district1);
        state1=findViewById(R.id.state1);
        country1=findViewById(R.id.country1);
       image=(ImageView) findViewById(R.id.memberphoto);
        name=(TextView) findViewById(R.id.name);
        work=(TextView) findViewById(R.id.work);
        address=(TextView) findViewById(R.id.address);
        mobilenumber=(TextView) findViewById(R.id.mobilenumber);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
