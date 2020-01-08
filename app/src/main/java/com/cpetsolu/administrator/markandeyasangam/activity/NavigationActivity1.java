package com.cpetsolu.administrator.markandeyasangam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cpetsolu.administrator.markandeyasangam.R;
import com.cpetsolu.administrator.markandeyasangam.fragment.AboutUSFrag;
import com.cpetsolu.administrator.markandeyasangam.fragment.ContactsusFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.FirstFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.LoginFragment;
import com.cpetsolu.administrator.markandeyasangam.fragment.ServiceFragment;

public class NavigationActivity1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame1, new FirstFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home1);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_activity1, menu);

        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame1, new FirstFragment()).commit();

        } else if (id == R.id.nav_changenumber) {

            Intent i=new Intent(NavigationActivity1.this,ChangeNumberActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_changepassword) {

            Intent i=new Intent(NavigationActivity1.this,ChangePasswordActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_service1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame1, new ServiceFragment()).commit();

            //Intent i=new Intent(NavigationActivity1.this,ServiceActivity.class);
                //startActivity(i);

        } else if (id == R.id.nav_about1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame1, new AboutUSFrag()).commit();

            // Intent i=new Intent(NavigationActivity1.this,ABoutActivity.class);
           // startActivity(i);
        } else if (id == R.id.nav_contact1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame1, new ContactsusFragment()).commit();

           // Intent i=new Intent(NavigationActivity1.this,ContactUsActivity.class);
           // startActivity(i);
        } else if (id == R.id.nav_login1) {

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_content_Frame1, new LoginFragment()).commit();

       // Intent i=new Intent(NavigationActivity1.this,LoginActivity.class);
       // startActivity(i);
    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
