package com.example.learnsphere2.User;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learnsphere2.User.Fragment.ChallengesFragment;
import com.example.learnsphere2.User.Fragment.ExpertFragment;
import com.example.learnsphere2.User.Fragment.HomeFragment;
import com.example.learnsphere2.User.Fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.learnsphere2.R;

import java.util.HashMap;
import java.util.Map;

public class UserMainActivity extends AppCompatActivity {
        private final Map<Integer, Fragment> fragmentMap = new HashMap<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_main);

            fragmentMap.put(R.id.HomeFragment, new HomeFragment());
            fragmentMap.put(R.id.ChallengesFragment, new ChallengesFragment());
            fragmentMap.put(R.id.ExpertFragment, new ExpertFragment());
            fragmentMap.put(R.id.SettingsFragment, new SettingsFragment());

            replaceFragment(fragmentMap.get(R.id.HomeFragment));

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                Fragment selectedFragment = fragmentMap.get(item.getItemId());
                if (selectedFragment != null) {
                    replaceFragment(selectedFragment);
                }
                return true;});}

        private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_layout, fragment);
            fragmentTransaction.commit();}}
