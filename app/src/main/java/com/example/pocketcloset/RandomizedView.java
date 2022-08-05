package com.example.pocketcloset;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.pocketcloset.Fragments.OutfitsFragment;
import com.example.pocketcloset.adapters.RandomizedAdapter;
import com.example.pocketcloset.models.Clothing;
import com.example.pocketcloset.models.Outfit;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomizedView extends AppCompatActivity {
    protected List<Clothing> allClothing;
    List<Clothing> randomTops;
    List<Clothing> randomBottoms;
    List<Clothing> randomHeadwear;
    List<Clothing> randomOverwear;
    List<Clothing> randomHandheld;
    List<Clothing> randomNeckwear;
    List<Clothing> randomEarrings;
    List<Clothing> randomShoes;
    List<Clothing> randomBracelets;
    FragmentManager fragmentManager;
    private ImageView ivShoes;
    private ImageView ivTop;
    private ImageView ivBottom;
    private ImageView ivBracelet;
    private ImageView ivHeadwear;
    private ImageView ivNecklace;
    private ImageView ivHandheld;
    private ImageView ivEarrings;
    private ImageView ivOverwear;
    RandomizedAdapter adapter;
    RandomizedAdapter topsAdapter;
    RandomizedAdapter bottomsAdapter;
    RandomizedAdapter headwearAdapter;
    RandomizedAdapter shoesAdapter;
    RandomizedAdapter earringsAdapter;
    RandomizedAdapter overwearAdapter;
    RandomizedAdapter neckwearAdapter;
    RandomizedAdapter braceletAdapter;
    RandomizedAdapter handheldAdapter;
    private RecyclerView rvRandomTops;
    private RecyclerView rvRandomShoes;
    private RecyclerView rvRandomHandheld;
    private RecyclerView rvRandomEarrings;
    private RecyclerView rvRandomNeckwear;
    private RecyclerView rvRandomOverwear;
    private RecyclerView rvRandomBottoms;
    private RecyclerView rvRandomHeadwear;
    private RecyclerView rvRandomBracelet;
    private SnapHelper topSnapHelper;
    private SnapHelper bottomsSnapHelper;
    private SnapHelper overwearSnapHelper;
    private SnapHelper shoesSnapHelper;
    private SnapHelper handheldSnapHelper;
    private ImageButton ibAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomized_view);

        ivShoes = findViewById(R.id.ivShoes);
        ivTop = findViewById(R.id.ivTop);
        ivBottom = findViewById(R.id.ivBottom);
        ivHeadwear = findViewById(R.id.ivHeadwear);
        ivBracelet = findViewById(R.id.ivBracelet);
        ivHandheld = findViewById(R.id.ivHandheld);
        allClothing = new ArrayList<>();
        ivNecklace = findViewById(R.id.ivNecklace);
        ivOverwear = findViewById(R.id.ivOverwear);
        ivEarrings = findViewById(R.id.ivEarrings);
        fragmentManager = getSupportFragmentManager();
        rvRandomTops = findViewById(R.id.rvRandomTops);
        rvRandomBottoms = findViewById(R.id.rvRandomBottoms);
        rvRandomEarrings = findViewById(R.id.rvRandomEarrings);
        rvRandomShoes = findViewById(R.id.rvRandomShoes);
        rvRandomNeckwear = findViewById(R.id.rvRandomNecklace);
        rvRandomHandheld = findViewById(R.id.rvRandomHandheld);
        rvRandomOverwear = findViewById(R.id.rvRandomOverwear);
        rvRandomEarrings = findViewById(R.id.rvRandomEarrings);
        rvRandomBracelet = findViewById(R.id.rvRandomBracelet);
        ibAdd = findViewById(R.id.ibAdd);
        randomTops = new ArrayList<>();
        rvRandomHeadwear = findViewById(R.id.rvRandomHeadwear);
        topsAdapter = new RandomizedAdapter(this, randomTops);
        randomBottoms = new ArrayList<>();
        randomHeadwear = new ArrayList<>();
        randomEarrings = new ArrayList<>();
        randomOverwear = new ArrayList<>();
        randomHandheld = new ArrayList<>();
        randomNeckwear = new ArrayList<>();
        randomShoes = new ArrayList<>();
        randomBracelets = new ArrayList<>();
        bottomsAdapter = new RandomizedAdapter(this, randomBottoms);
        headwearAdapter = new RandomizedAdapter(this, randomHeadwear);
        shoesAdapter = new RandomizedAdapter(this, randomShoes);
        overwearAdapter = new RandomizedAdapter(this, randomOverwear);
        neckwearAdapter = new RandomizedAdapter(this, randomNeckwear);
        earringsAdapter = new RandomizedAdapter(this, randomEarrings);
        handheldAdapter = new RandomizedAdapter(this, randomHandheld);
        braceletAdapter = new RandomizedAdapter(this, randomBracelets);
        earringsAdapter = new RandomizedAdapter(this, randomEarrings);
        rvRandomTops.setAdapter(topsAdapter);
        rvRandomTops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRandomHeadwear.setAdapter(headwearAdapter);
        rvRandomHeadwear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRandomShoes.setAdapter(shoesAdapter);
        rvRandomShoes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRandomOverwear.setAdapter(overwearAdapter);
        rvRandomOverwear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRandomNeckwear.setAdapter(neckwearAdapter);
        rvRandomNeckwear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRandomEarrings.setAdapter(earringsAdapter);
        rvRandomEarrings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRandomHandheld.setAdapter(handheldAdapter);
        rvRandomHandheld.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRandomBracelet.setAdapter(braceletAdapter);
        rvRandomBracelet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        queryClothesRandom(ClothingType.HEADWEAR, headwearAdapter, randomHeadwear);
        queryClothesRandom(ClothingType.BRACELET, braceletAdapter, randomBracelets);
        queryClothesRandom(ClothingType.EARRINGS, earringsAdapter, randomEarrings);
        queryClothesRandom(ClothingType.OVERWEAR, overwearAdapter, randomOverwear);
        queryClothesRandom(ClothingType.NECKWEAR, neckwearAdapter, randomNeckwear);
        queryClothesRandom(ClothingType.HANDHELD, handheldAdapter, randomHandheld);
        queryClothesRandom(ClothingType.SHOES, shoesAdapter, randomShoes);
        queryClothesRandom(ClothingType.TOP, topsAdapter, randomTops);
        rvRandomBottoms.setAdapter(bottomsAdapter);
        rvRandomBottoms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        queryClothesRandom(ClothingType.PANTS, bottomsAdapter, randomBottoms);
        queryClothesRandom(ClothingType.DRESS, bottomsAdapter, randomBottoms);
        topSnapHelper = new PagerSnapHelper();
        topSnapHelper.attachToRecyclerView(rvRandomTops);
        bottomsSnapHelper = new PagerSnapHelper();
        bottomsSnapHelper.attachToRecyclerView(rvRandomBottoms);
        shoesSnapHelper = new PagerSnapHelper();
        shoesSnapHelper.attachToRecyclerView(rvRandomShoes);


        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(RandomizedView.this);
            }
        });


    }



    private void showAddItemDialog(Context context) {
        final EditText taskEditText = new EditText(context);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Create Outfit")
                .setMessage("Create a name for your new outfit")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Outfit outfit = new Outfit();
                        Clothing top = getClothing(rvRandomTops, randomTops);
                        Clothing bottom = getClothing(rvRandomBottoms, randomBottoms);
                        Clothing handheld = getClothing(rvRandomHandheld, randomHandheld);
                        Clothing earrings = getClothing(rvRandomEarrings, randomEarrings);
                        Clothing bracelet = getClothing(rvRandomBracelet, randomBracelets);
                        Clothing headwear = getClothing(rvRandomHeadwear, randomHeadwear);
                        Clothing overwear = getClothing(rvRandomOverwear, randomOverwear);
                        Clothing shoes = getClothing(rvRandomShoes, randomShoes);
                        Clothing neckwear = getClothing(rvRandomNeckwear, randomNeckwear);
                        outfit.setOutfitName(String.valueOf(taskEditText.getText()));
                        outfit.setUser(ParseUser.getCurrentUser());

                        if (bottom != null) {
                            outfit.setBottoms(bottom);
                            bottom.setWornCount(bottom.getWornCount()+1);
                        }
                        if (top != null) {
                            outfit.setTop(top);
                            top.setWornCount(top.getWornCount()+1);
                        }
                        if (shoes != null) {
                            outfit.setShoes(shoes);
                            shoes.setWornCount(shoes.getWornCount()+1);
                        }
                        if (headwear != null) {
                            outfit.setHeadwear(headwear);
                            headwear.setWornCount(headwear.getWornCount()+1);
                        }
                        if (handheld != null) {
                            outfit.setHandheld(handheld);
                            handheld.setWornCount(handheld.getWornCount()+1);
                        }
                        if (earrings != null) {
                            outfit.setEarrings(earrings);
                            earrings.setWornCount(earrings.getWornCount()+1);
                        }
                        if (bracelet != null) {
                            outfit.setBracelet(bracelet);
                            bracelet.setWornCount(bracelet.getWornCount()+1);
                        }
                        if (overwear != null) {
                            outfit.setOverwear(overwear);
                            overwear.setWornCount(overwear.getWornCount()+1);
                        }
                        if (neckwear != null) {
                            outfit.setNeckwear(neckwear);
                            neckwear.setWornCount(neckwear.getWornCount()+1);
                        }
                        outfit.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.e("RANDOMIZED VIEW", "Error while saving", e);
                                    Toast.makeText(RandomizedView.this, "Error while saving", Toast.LENGTH_SHORT);
                                }

                            }
                        });


                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


    public Clothing getClothing(RecyclerView rvClothing, List<Clothing> randomClothing) {
        Integer position = 0;
        if (rvClothing != null) {
            position = ((LinearLayoutManager) rvClothing.getLayoutManager()).findFirstVisibleItemPosition();
        }
        if (randomClothing.size() != 0) {
            return randomClothing.get(position);
        }
        return null;
    }


    public Clothing randomizeClothing(List<Clothing> allClothing, String clothing) {
        List<Clothing> newClothing = filterClothing(allClothing, clothing);
        if (newClothing.size() != 0) {
            Clothing random = newClothing.get(new Random().nextInt(newClothing.size()));
            return random;
        } else {
            return null;
        }
    }


    public List<Clothing> filterClothing(List<Clothing> allClothing, String clothingType) {
        ArrayList<Clothing> newClothing = new ArrayList<>();
        for (int i = 0; i < allClothing.size(); i++) {
            if (allClothing.get(i).getClothingType().equals(clothingType)) {
                newClothing.add(allClothing.get(i));
            }
        }
        return newClothing;
    }

    public void checkClothing(List<Clothing> clothing, String clothingType, ImageView image) {
        Clothing randomItem = randomizeClothing(clothing, clothingType);
        ParseFile randomItemClothing = null;
        if (randomItem != null) {
            randomItemClothing = randomItem.getClothingImage();
        }
        if (randomItem != null && randomItemClothing != null) {
            Glide.with(RandomizedView.this).load(randomItem.getClothingImage().getUrl()).override(Target.SIZE_ORIGINAL).into(image);
        }
    }


    protected void queryClothesRandom(@Nullable String clothingType,RandomizedAdapter adapter, List<Clothing> clothingList) {
        {
            // specify what type of data we want to query - Post.class
            ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
            // include data referred by user key
            Log.i("WardrobeFragment", "queryClothes(with params) ran");
            //query.include(Clothing.KEY_CLOTHING_IMAGE);
            query.whereEqualTo(Clothing.KEY_USER, ParseUser.getCurrentUser());
            if (clothingType != null) {
                query.whereEqualTo(Clothing.KEY_CLOTHING_TYPE, clothingType);
            }
            // limit query to latest 20 items
            query.setLimit(20);
            // order posts by creation date (newest first)
            query.addDescendingOrder("createdAt");


            // start an asynchronous call for posts
            query.findInBackground(new FindCallback<Clothing>() {
                @Override
                public void done(List<Clothing> clothes, ParseException e) {
                    // check for errors
                    if (e != null) {
                        Log.i("RANDOMIZED VIEW", "Issue with getting clothing", e);
                        return;
                    }
                    Collections.shuffle(clothes);
                    clothingList.addAll(clothes);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    protected void queryClothes(@Nullable String clothingType) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
        Log.i("WardrobeFragment", "queryClothes(with params) ran");
        //query.include(Clothing.KEY_CLOTHING_IMAGE);
        query.whereEqualTo(Clothing.KEY_USER, ParseUser.getCurrentUser());
        if (clothingType != null) {
            query.whereEqualTo(Clothing.KEY_CLOTHING_TYPE, clothingType);
        }
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Clothing>() {
            @Override
            public void done(List<Clothing> clothes, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.i("RANDOMIZED VIEW", "Issue with getting clothing", e);
                    return;
                }
                adapter.clear();
                allClothing.addAll(clothes);
                adapter.notifyDataSetChanged();


                checkClothing(allClothing, ClothingType.HEADWEAR, ivHeadwear);
                checkClothing(allClothing, ClothingType.BRACELET, ivBracelet);
                checkClothing(allClothing, ClothingType.PANTS, ivBottom);
                checkClothing(allClothing, ClothingType.DRESS, ivBottom);
                checkClothing(allClothing, ClothingType.SKIRT, ivBottom);
                checkClothing(allClothing, ClothingType.TOP, ivTop);
                checkClothing(allClothing, ClothingType.NECKWEAR, ivNecklace);
                checkClothing(allClothing, ClothingType.SHOES, ivShoes);
                checkClothing(allClothing, ClothingType.HANDHELD, ivHandheld);
                checkClothing(allClothing, ClothingType.EARRINGS, ivEarrings);
                checkClothing(allClothing, ClothingType.OVERWEAR, ivOverwear);
            }
        });
    }
}