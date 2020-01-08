package com.cpetsolu.administrator.markandeyasangam.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.TabAdapter;
import com.cpetsolu.administrator.markandeyasangam.fragment.AboutUSFrag;
import com.cpetsolu.administrator.markandeyasangam.fragment.ChangeMobiFrag;
import com.cpetsolu.administrator.markandeyasangam.fragment.ChangePasswordFrag;
import com.cpetsolu.administrator.markandeyasangam.fragment.ContactsusFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.FirstFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.ServiceFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.TabFragment;
import com.cpetsolu.administrator.markandeyasangam.util.SessinSave;

import java.util.List;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public  BottomNavigationView bottomNavigationView;
    private ExpandableListView mDrawerList;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mactionBarDrawerToggle;
    private String mactivityTitle;
    private String[] item;
    private TabItem home111,member111;
    private ListView details;
    private NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> listtitle;
    private Map<String,List<String>> listchild;
    private TextView id,surname,name,bloodgroup,mobile,email,gender;
    private TextView Id,Name,Surname,Gender,Mobile,Email,Bloodgroup;
    private TextView dashboard,profile,memberdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ViewPager viewPager=(ViewPager) findViewById(R.id.viewpager);

        TabAdapter tabAdapter1=new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter1);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview=navigationView.getHeaderView(0);
        TextView text = (TextView) headerview.findViewById(R.id.headername);

        text.setText(SessinSave.getsessin("name",NavigationActivity.this)+" "+SessinSave.getsessin("surName",NavigationActivity.this));

        String id = SessinSave.getsessin("id",NavigationActivity.this);
        String surName = SessinSave.getsessin("surName",NavigationActivity.this);
        String name = SessinSave.getsessin("name",NavigationActivity.this);
        String gender = SessinSave.getsessin("gender",NavigationActivity.this);
        String bloodGroup = SessinSave.getsessin("bloodGroup",NavigationActivity.this);
        String mobile = SessinSave.getsessin("mobile",NavigationActivity.this);
        String email1 = SessinSave.getsessin("email",NavigationActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new TabFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_member);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);
            builder.setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            SessinSave.saveSession("id","",NavigationActivity.this);
                            SessinSave.saveSession("role","",NavigationActivity.this);
                            SessinSave.saveSession("surName","",NavigationActivity.this);
                            SessinSave.saveSession("name","",NavigationActivity.this);
                            SessinSave.saveSession("gender","",NavigationActivity.this);
                            SessinSave.saveSession("bloodGroup","",NavigationActivity.this);
                            SessinSave.saveSession("dob","",NavigationActivity.this);
                            SessinSave.saveSession("memberStatus","",NavigationActivity.this);
                            SessinSave.saveSession("locality","",NavigationActivity.this);
                            SessinSave.saveSession("assembly","",NavigationActivity.this);
                            SessinSave.saveSession("parliament","",NavigationActivity.this);
                            SessinSave.saveSession("mobile","",NavigationActivity.this);

                            SessinSave.saveSession("emailView","",NavigationActivity.this);
                            SessinSave.saveSession("aboutYou","",NavigationActivity.this);
                            SessinSave.saveSession("aboutYouView","",NavigationActivity.this);
                            SessinSave.saveSession("adminComments","",NavigationActivity.this);
                            SessinSave.saveSession("mobileView","",NavigationActivity.this);
                            SessinSave.saveSession("email","",NavigationActivity.this);
                            SessinSave.saveSession("status","",NavigationActivity.this);
                            SessinSave.saveSession("country","",NavigationActivity.this);
                            SessinSave.saveSession("state","",NavigationActivity.this);
                            SessinSave.saveSession("district","",NavigationActivity.this);
                            SessinSave.saveSession("subDistrict","",NavigationActivity.this);
                            SessinSave.saveSession("addressLine1","",NavigationActivity.this);

                            Toast.makeText(NavigationActivity.this,"Logging Out...", Toast.LENGTH_LONG).show();

                            finish();
                            Intent in = new Intent(NavigationActivity.this,NavigationActivity1.class);
                            startActivity(in);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        //(id == nav_member)

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new FirstFragment()).commit();

           // Intent i=new Intent(NavigationActivity.this,MainActivity.class);
           // startActivity(i);

        } else if (id == R.id.nav_member) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_content_Frame,new TabFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_member);

               //getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new TabFragment()).commit();

            // Intent i=new Intent(NavigationActivity.this,TabFragment.class);
           // startActivity(i);

        } else if (id == R.id.nav_changenumber) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame,new ChangeMobiFrag()).commit();
            //Intent i=new Intent(NavigationActivity.this,ChangeNumberActivity.class);
            //startActivity(i);
        } else if (id == R.id.nav_changepassword) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame,new ChangePasswordFrag()).commit();
            //Intent i=new Intent(NavigationActivity.this,ChangePasswordActivity.class);
            //startActivity(i);
        } else if (id == R.id.navabout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new AboutUSFrag()).commit();
           // Intent i=new Intent(NavigationActivity.this,ABoutActivity.class);
            //startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_contactus) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new ContactsusFragment()).commit();
            //Intent i=new Intent(NavigationActivity.this,ContactUsActivity.class);
            //startActivity(i);

        } else if (id == R.id.nav_service) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new ServiceFragment()).commit();
            //Intent i = new Intent(NavigationActivity.this, ServiceActivity.class);
            //startActivity(i);

        }else if (id == R.id.nav_upload) {
           // getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new UploadImageinserver()).commit();
                Intent i=new Intent(NavigationActivity.this,UploadInServer.class);
                startActivity(i);

        } else if (id == R.id.nav_logout) {
          //  getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new Logout()).commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);
            builder.setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            SessinSave.saveSession("id","",NavigationActivity.this);
                            SessinSave.saveSession("role","",NavigationActivity.this);
                            SessinSave.saveSession("surName","",NavigationActivity.this);
                            SessinSave.saveSession("name","",NavigationActivity.this);
                            SessinSave.saveSession("gender","",NavigationActivity.this);
                            SessinSave.saveSession("bloodGroup","",NavigationActivity.this);
                            SessinSave.saveSession("dob","",NavigationActivity.this);
                            SessinSave.saveSession("memberStatus","",NavigationActivity.this);
                            SessinSave.saveSession("locality","",NavigationActivity.this);
                            SessinSave.saveSession("assembly","",NavigationActivity.this);
                            SessinSave.saveSession("parliament","",NavigationActivity.this);
                            SessinSave.saveSession("mobile","",NavigationActivity.this);

                            SessinSave.saveSession("emailView","",NavigationActivity.this);
                            SessinSave.saveSession("aboutYou","",NavigationActivity.this);
                            SessinSave.saveSession("aboutYouView","",NavigationActivity.this);
                            SessinSave.saveSession("adminComments","",NavigationActivity.this);
                            SessinSave.saveSession("mobileView","",NavigationActivity.this);
                            SessinSave.saveSession("email","",NavigationActivity.this);
                            SessinSave.saveSession("status","",NavigationActivity.this);
                            SessinSave.saveSession("country","",NavigationActivity.this);
                            SessinSave.saveSession("state","",NavigationActivity.this);
                            SessinSave.saveSession("district","",NavigationActivity.this);
                            SessinSave.saveSession("subDistrict","",NavigationActivity.this);
                            SessinSave.saveSession("addressLine1","",NavigationActivity.this);
                           // SessinSave.saveSession("inTime","",NavigationActivity.this);
                           // SessinSave.saveSession("view","",NavigationActivity.this);

                            Toast.makeText(NavigationActivity.this,"Logging Out...", Toast.LENGTH_LONG).show();

                            finish();
                            Intent in = new Intent(NavigationActivity.this,NavigationActivity1.class);
                            startActivity(in);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                NavigationActivity.this.finish();
                }
        }, 2000);
    }
}
