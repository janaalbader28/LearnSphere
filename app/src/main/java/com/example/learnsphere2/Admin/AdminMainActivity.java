package com.example.learnsphere2.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.learnsphere2.Admin.Fragment.AdminHomeFragment;
import com.example.learnsphere2.Admin.Fragment.AdminSettingsFragment;
import com.example.learnsphere2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMainActivity extends AppCompatActivity {

    private final Fragment homeFragment = new AdminHomeFragment();
    private final Fragment settingsFragment = new AdminSettingsFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager.beginTransaction().add(R.id.fragment_layout, settingsFragment, "Settings").hide(settingsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, homeFragment, "Home").commit();
        activeFragment = homeFragment;

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.AdminHomeFragment) {
                switchFragment(homeFragment);
                return true;
            } else if (item.getItemId() == R.id.AdminSettingsFragment) {
                switchFragment(settingsFragment);
                return true;
            }
            return false;
        });}
        private void switchFragment(Fragment fragment) {
        if (activeFragment != fragment) {
            fragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit();
            activeFragment = fragment;
        }}}
