package com.example.pocketcloset;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.pocketcloset.adapters.ClothingAdapter;
import com.example.pocketcloset.adapters.RandomizedAdapter;
import com.example.pocketcloset.models.Clothing;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizedView extends AppCompatActivity {
    protected List<Clothing> allClothing;
    private ImageView ivShoes;
    private ImageView ivTop;
    private ImageView ivBottom;
    private ImageView ivBracelet;
    private ImageView ivHeadwear;
    private ImageView ivNecklace;
    private ImageView ivHandheld;
    private ImageView ivEarrings;
    private ImageView ivOverwear;
    private ClothingAdapter adapter;
    private RandomizedAdapter randomizedAdapter;
    private RecyclerView rvRandomTops;


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
        rvRandomTops = findViewById(R.id.rvRandomTops);
        randomizedAdapter = new RandomizedAdapter(this, allClothing);


        adapter = new ClothingAdapter(this, allClothing);
        rvRandomTops.setAdapter(randomizedAdapter);
        rvRandomTops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        queryClothes(null);
        queryClothesRandom(ClothingType.TOP);





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

    protected void queryRandom(){
        adapter.clear();
        allClothing.addAll(filterClothing(allClothing, ClothingType.TOP));
        adapter.notifyDataSetChanged();

    }

    protected  void queryClothesRandom(@Nullable String clothingType){
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
                    randomizedAdapter.clear();
                    allClothing.addAll(clothes);
                    randomizedAdapter.notifyDataSetChanged();


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
             //   checkClothing(allClothing, ClothingType.TOP, ivTop);
                checkClothing(allClothing, ClothingType.NECKWEAR, ivNecklace);
                checkClothing(allClothing, ClothingType.SHOES, ivShoes);
                checkClothing(allClothing, ClothingType.HANDHELD, ivHandheld);
                checkClothing(allClothing, ClothingType.EARRINGS, ivEarrings);
                checkClothing(allClothing, ClothingType.OVERWEAR, ivOverwear);


            }
        });






    }

}