package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cpetsolu.administrator.markandeyasangam.R;

public class HomeActivity extends Activity {
private TextView Ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tv1=findViewById(R.id.about);
        TextView tv2=findViewById(R.id.contact);
        TextView tv3=findViewById(R.id.servicce);
        TextView tv4=findViewById(R.id.home11);
        TextView tv5=findViewById(R.id.loginn);

tv1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(HomeActivity.this,ABoutActivity.class);
        startActivity(i);
    }
});
tv2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(HomeActivity.this,ContactUsActivity.class);
        startActivity(i);

    }
});
tv3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(HomeActivity.this,ServiceActivity.class);
        startActivity(i);
    }
});
tv4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(HomeActivity.this,MainActivity.class);
        startActivity(i);

    }
});
tv5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent i=new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(i);

       /* if(SessinSave.getsessin("id",HomeActivity.this).equalsIgnoreCase("") ){
            Intent in  = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(in);
        }else{
            Intent in  = new Intent(HomeActivity.this,NavigationActivity.class);
            startActivity(in);
        }*/
    }
});
    }
}
