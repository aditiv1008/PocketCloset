package com.example.pocketcloset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.pocketcloset.Fragments.CameraFragment;
import com.example.pocketcloset.Fragments.OutfitsFragment;
import com.example.pocketcloset.Fragments.ProfileFragment;
import com.example.pocketcloset.Fragments.WardrobeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigation;
    CameraFragment cameraFragment = new CameraFragment();
    OutfitsFragment outfitsFragment = new OutfitsFragment();
    WardrobeFragment wardrobeFragment = new WardrobeFragment(ParseUser.getCurrentUser());
    ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        fragment = cameraFragment;
                        break;
                    case R.id.action_wardrobe:
                        fragment = wardrobeFragment;
                        break;
                    case R.id.action_outfits:
                        fragment = outfitsFragment;
                        break;
                    case R.id.action_profile:
                    default:
                      //  profileFragment.user = (User) ParseUser.getCurrentUser();
                        fragment = profileFragment;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.rlContainer, fragment).commit();
                return true;

            }
        });
        bottomNavigation.setSelectedItemId(R.id.action_camera);
    }


    public void onLogoutButton(View view) {
        // forget who's logged in
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);


    }


}