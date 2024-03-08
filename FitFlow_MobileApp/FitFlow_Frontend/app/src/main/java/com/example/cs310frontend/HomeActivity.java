package com.example.cs310frontend;

import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.cs310frontend.R;



public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_layout);

        String userEmail = getIntent().getStringExtra("userEmail");

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();
                if (itemId == bottomNavigationView.getMenu().findItem(R.id.navigation_workouts).getItemId()) {
                    selectedFragment = new WorkoutsFragment();
                } else if (itemId == bottomNavigationView.getMenu().findItem(R.id.navigation_my_profile).getItemId()) {
                    selectedFragment = new MyProfileFragment(userEmail);
                } else if (itemId == bottomNavigationView.getMenu().findItem(R.id.navigation_exercises).getItemId()) {
                    selectedFragment = new ExercisesFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }

                return true; // return true to display the item as the selected item
            }
        });

        // Set default fragment on activity start
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_my_profile); // Change to your default fragment
        }
    }
}

