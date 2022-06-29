package com.example.pocketcloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pocketcloset.Fragments.CameraFragment;
import com.example.pocketcloset.Fragments.WardrobeFragment;
import com.example.pocketcloset.models.Clothing;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class SaveClothingView extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private MaterialButtonToggleGroup toggleButton;
    private ImageButton ibAdd;
    private ImageView ivSavedPhoto;
    public Button btnHeadwear;
    public Button btnTops;
    public Button btnOverwear;
    public Button btnBottoms;
    public Button btnAccessories;
    public Button btnShoes;
    public MaterialButtonToggleGroup toggleButtonBottoms;
    protected List<Clothing> allClothing;
    CameraFragment cameraFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_clothing_view);

        ibAdd = findViewById(R.id.ibAdd);
        ivSavedPhoto = findViewById(R.id.ivSavedPhoto);
        toggleButton = findViewById(R.id.toggleButton);
        btnAccessories = findViewById(R.id.btnAccessories);
        btnTops = findViewById(R.id.btnTop);
        btnHeadwear = findViewById(R.id.btnHeadwear);
        btnOverwear = findViewById(R.id.btnOverwear);
        btnBottoms = findViewById(R.id.btnBottoms);
        btnShoes = findViewById(R.id.btnShoes);
        toggleButtonBottoms = findViewById(R.id.toggleButtonBottoms);
        toggleButtonBottoms.setVisibility(View.GONE);

        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (checkedId == R.id.btnBottoms) {
                    toggleButtonBottoms.setVisibility(View.VISIBLE);
                    toggleButton.setVisibility(View.GONE);
                }
                ibAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("SaveClothingView", "Add Button Clicked");

                        switch (checkedId) {
                            case R.id.btnTop:
                                addClothing("Top");

                                break;

                            case R.id.btnBottoms:
                                addClothing("Pants");
                                break;

                            case R.id.btnShoes:
                                addClothing("Shoes");

                                break;

                            case R.id.btnHeadwear:

                                addClothing("Headwear");
                                break;

                            case R.id.btnOverwear:

                                addClothing("Overwear");

                                break;
                            case R.id.btnAccessories:
                                addClothing("Earrings");

                        }

                    }
                });

            }
        });

        toggleButtonBottoms.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (checkedId == R.id.btnBottoms) {
                    toggleButtonBottoms.setVisibility(View.VISIBLE);
                    toggleButton.setVisibility(View.GONE);
                }
                ibAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (checkedId) {
                            case R.id.btnSkirt:
                                addClothing("Skirt");
                                break;

                            case R.id.btnPants:
                                addClothing("Pants");
                                break;

                            case R.id.btnDress:
                                addClothing("Dress");
                                break;


                        }
                    }
                });
                }
        });
    }



    public void addClothing(String clothing) {
        Clothing article = new Clothing();
        article.setClothingType(clothing);
        article.setUser(ParseUser.getCurrentUser());
        article.setClothingImage(getIntent().getParcelableExtra(EXTRA_CONTACT));
        article.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("MainActivity", "Error while saving", e);
                    Toast.makeText(SaveClothingView.this, "Error while saving", Toast.LENGTH_SHORT);
                }
                Log.i("MainActivity", "Post save was sucessful");
                goMainActivity();

            }
        });
        Log.i("SaveClothingView", "Clothing:" + article.getClothingType().toString());

        Log.i("SaveClothingView", "Top Button Clicked");


    }
    private void goMainActivity() {
        Intent i = new Intent(SaveClothingView.this, MainActivity.class);

        startActivity(i);
        finish();
    }

}