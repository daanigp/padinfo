package com.daanigp.padinfo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.daanigp.padinfo.Fragments.HomeFragment;
import com.daanigp.padinfo.Fragments.MainFragment;
import com.daanigp.padinfo.Interfaces.BackPressHandler;
import com.daanigp.padinfo.R;
import com.daanigp.padinfo.SharedPreferences.SharedPreferencesManager;

public class Activity_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //setDayNight();

        if (savedInstanceState != null) {
            loadFragment(new MainFragment());
        } else {
            handleIntent(getIntent());
        }


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.add(R.id.fcv_main_container, fragment);
        fragmentTransaction.commit();
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra("navigateToFragment")) {
            String fragmentToNavigate = intent.getStringExtra("navigateToFragment");
            if (fragmentToNavigate.equalsIgnoreCase("gamesListFragment")) {
                loadFragment(MainFragment.newInstance("gamesListFragment"));
            }
        } else {
            loadFragment(new MainFragment());
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof BackPressHandler) {
            if (!((BackPressHandler) fragment).onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void setDayNight() {
        int theme = SharedPreferencesManager.getInstance(this).getTheme();
        if (theme == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}