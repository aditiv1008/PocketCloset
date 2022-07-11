package com.example.pocketcloset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketcloset.Fragments.CameraFragment;
import com.example.pocketcloset.models.Clothing;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class SaveClothingView extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private MaterialButtonToggleGroup toggleButton;
    private ImageButton ibAdd;
    private ImageView ivSavedPhoto;
    private Button btnHeadwear;
    private Button btnTops;
    private Button btnOverwear;
    private Button btnBottoms;
    private Button btnAccessories;
    private Button btnShoes;
    private MaterialButtonToggleGroup toggleButtonBottoms;
    private MaterialButtonToggleGroup toggleAccessories;
    protected List<Clothing> allClothing;
    CameraFragment cameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_clothing_view);

        ibAdd = findViewById(R.id.ibAdd);
        ivSavedPhoto = findViewById(R.id.ivSavedPhoto);
        toggleAccessories = findViewById(R.id.toggleAccessories);
        toggleButton = findViewById(R.id.toggleButton);
        btnAccessories = findViewById(R.id.btnAccessories);
        btnTops = findViewById(R.id.btnTop);
        btnHeadwear = findViewById(R.id.btnHeadwear);
        btnOverwear = findViewById(R.id.btnOverwear);
        btnBottoms = findViewById(R.id.btnBottoms);
        btnShoes = findViewById(R.id.btnShoes);
        toggleButtonBottoms = findViewById(R.id.toggleButtonBottoms);
        toggleButtonBottoms.setVisibility(View.GONE);
        toggleAccessories.setVisibility(View.GONE);

        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (checkedId == R.id.btnBottoms) {
                    toggleButtonBottoms.setVisibility(View.VISIBLE);
                    toggleButton.setVisibility(View.GONE);
                }
                if (checkedId == R.id.btnAccessories) {
                    toggleAccessories.setVisibility(View.VISIBLE);
                    toggleButton.setVisibility(View.GONE);
                }
                ibAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("SaveClothingView", "Add Button Clicked");

                        switch (checkedId) {
                            case R.id.btnTop:
                                addClothing(ClothingType.TOP);
                                break;

                            case R.id.btnBottoms:
                                addClothing(ClothingType.PANTS);
                                break;

                            case R.id.btnShoes:
                                addClothing(ClothingType.SHOES);
                                break;

                            case R.id.btnHeadwear:
                                addClothing(ClothingType.HEADWEAR);
                                break;

                            case R.id.btnOverwear:
                                addClothing(ClothingType.OVERWEAR);
                                break;

                            case R.id.btnAccessories:
                                addClothing(ClothingType.EARRINGS);

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
                                addClothing(ClothingType.SKIRT);
                                break;

                            case R.id.btnPants:
                                addClothing(ClothingType.PANTS);
                                break;

                            case R.id.btnDress:
                                addClothing(ClothingType.DRESS);
                                break;


                        }
                    }
                });
            }
        });

        toggleAccessories.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (checkedId == R.id.btnAccessories) {
                    toggleAccessories.setVisibility(View.VISIBLE);
                    toggleButton.setVisibility(View.GONE);
                }
                ibAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (checkedId) {
                            case R.id.btnBracelets:
                                addClothing(ClothingType.BRACELET);
                                break;

                            case R.id.btnEarrings:
                                addClothing(ClothingType.EARRINGS);
                                break;

                            case R.id.btnNeckwear:
                                addClothing(ClothingType.NECKWEAR);
                                break;

                            case R.id.btnHandhelds:
                                addClothing(ClothingType.HANDHELD);
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