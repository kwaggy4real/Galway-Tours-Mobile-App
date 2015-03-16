package com.example.galwaytour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.facebook.LoginActivity;
import com.facebook.Session;
import com.facebook.android.Facebook;
import com.facebook.widget.LoginButton;
import android.support.v4.app.Fragment;


import java.util.Date;


public class FB_Login  extends FragmentActivity {

    private FB_Login_MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_fb__login);

        Log.i("FB_Login",  Long.toString(new Date().getTime()));

        if(savedInstanceState == null)
        {
            //Add the fragment on initial activity setup
            mainFragment = new FB_Login_MainFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();

        }
        else
        {
            //OR set the fragment from the restored state info
            mainFragment = (FB_Login_MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fb__login, menu);
        return true;
    }

    @Override
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
    }
}
