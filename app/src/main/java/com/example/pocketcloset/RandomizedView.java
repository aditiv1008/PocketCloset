package com.example.pocketcloset;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pocketcloset.adapters.ClothingAdapter;
import com.example.pocketcloset.models.Clothing;
import com.parse.FindCallback;
import com.parse.ParseException;
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
    private ImageView ivNeckwear;
    private ImageView ivHandheld;
    private ClothingAdapter adapter;
    private Button btnRandomize2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomized_view);


        ivShoes = findViewById(R.id.ivShoes);
        ivTop = findViewById(R.id.ivTop);
        ivBottom = findViewById(R.id.ivBottom);
        btnRandomize2 = findViewById(R.id.btnRandomize2);
        ivHeadwear = findViewById(R.id.ivHeadwear);
        allClothing = new ArrayList<>();
        ivNeckwear = findViewById(R.id.ivNecklace);
        adapter = new ClothingAdapter(this, allClothing);
        queryClothes(null);
        Log.i("FRAGMENT CLOTHES", "b" + allClothing.size());



        btnRandomize2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clothing randomShoe = randomizeClothing(ClothingType.TOP);
                Log.i("RANDOM SHOE", randomShoe.getClothingImage().toString());
             Glide.with(RandomizedView.this).load(randomShoe.getClothingImage().getUrl()).into(ivTop);
            }
        });



    }

    public Clothing randomizeClothing(String clothing) {
       // queryClothes(clothing);
      Clothing random = allClothing.get(new Random().nextInt(allClothing.size()));


       return random;
    }


   /* public void filterClothing(List<Clothing> allClothing, String clothingType) {
        queryClothes(null);
        for (int i = 0; i < allClothing.size(); i++) {
            if (allClothing.get(i).getClothingType() != clothingType) {
                allClothing.remove(i);
            }
        }
    }*/

    protected void queryClothes( @Nullable String clothingType) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
        Log.i("WardrobeFragment", "queryClothes(with params) ran");
        //query.include(Clothing.KEY_CLOTHING_IMAGE);
        query.whereEqualTo(Clothing.KEY_USER, ParseUser.getCurrentUser());
        if(clothingType != null) {
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
                // save received posts to list and notify adapter of new data
            //    adapter.clear();
                allClothing.addAll(clothes);
                adapter.notifyDataSetChanged();
                Log.i("randomized FRAGMENT CLOTHES", "" + allClothing.size());



            }
        });


    }

}