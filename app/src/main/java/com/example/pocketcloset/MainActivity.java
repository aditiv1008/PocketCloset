package com.example.pocketcloset;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pocketcloset.Fragments.CameraFragment;
import com.example.pocketcloset.Fragments.OutfitsFragment;
import com.example.pocketcloset.Fragments.ProfileFragment;
import com.example.pocketcloset.Fragments.WardrobeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigation;
    CameraFragment cameraFragment = new CameraFragment();
    OutfitsFragment outfitsFragment = new OutfitsFragment();
    WardrobeFragment wardrobeFragment = new WardrobeFragment();
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
                        //put bundle information
                        Bundle bundl = new Bundle();
                        bundl.putParcelable("userToFilterBy", ParseUser.getCurrentUser());
                        fragment = wardrobeFragment;
                        fragment.setArguments(bundl);
                        break;
                    case R.id.action_outfits:
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("userToFilterBy", ParseUser.getCurrentUser());
                        fragment = outfitsFragment;
                        fragment.setArguments(bundle);
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