package com.example.pocketcloset.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pocketcloset.R;
import com.example.pocketcloset.adapters.ClothingAdapter;
import com.example.pocketcloset.models.Clothing;
import com.example.pocketcloset.models.User;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class WardrobeFragment extends Fragment {

    protected ClothingAdapter adapter;
    protected List<Clothing> allClothing;

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvWardrobe;
    public ParseUser user =  User.getCurrentUser();
    public MaterialButtonToggleGroup toggleButton;

    public WardrobeFragment(ParseUser userToFilterBy) {
        // Required empty public constructor
        this.user =  userToFilterBy;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wardrobe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvWardrobe = view.findViewById(R.id.rvClothes);
        toggleButton = view.findViewById(R.id.toggleButton);

        allClothing = new ArrayList<>();
        adapter = new ClothingAdapter(getContext(), allClothing);

        rvWardrobe.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvWardrobe.setLayoutManager(new LinearLayoutManager(getContext()));

        int numberOfColumns =2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);

        rvWardrobe.setLayoutManager(gridLayoutManager);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
         //Setup refresh listener which triggers new data loading
       swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryClothes();
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
       swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
               android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



       toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
           @Override
           public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
               if (checkedId == R.id.btnTop) {
                   adapter.clear();
                    queryClothes("Top");
                   adapter.notifyDataSetChanged();
               }
               if (checkedId == R.id.btnBottoms) {
                   adapter.clear();
                   queryClothes("Bottom");
                   adapter.notifyDataSetChanged();

               }

               if (checkedId == R.id.btnAccessories) {
                   adapter.clear();
                   queryClothes("Earrings");
                   adapter.notifyDataSetChanged();

               }
               if (checkedId == R.id.btnHeadwear) {
                   adapter.clear();
                   queryClothes("Headwear");
                   adapter.notifyDataSetChanged();

               }
               if (checkedId == R.id.btnShoes) {
                   adapter.clear();
                   queryClothes("Shoes");
                   adapter.notifyDataSetChanged();

               }
               if (checkedId == R.id.btnOverwear) {
                   adapter.clear();
                   queryClothes("Overwear");
                   adapter.notifyDataSetChanged();

               }
           }
       });



        queryClothes();







    }

    protected void queryClothes() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
        //query.include(Clothing.CLOTHING_TYPE);
        query.include(Clothing.KEY_CLOTHING_IMAGE);
        query.whereEqualTo(Clothing.KEY_USER, user);
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
                    Log.e("WardrobeFragment", "Issue with getting clothing", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Clothing clothing : clothes) {
                //    Log.i("WardrobeFragment", "Clothing: " + clothing.getClothingType() );
                }

                // save received posts to list and notify adapter of new data
               // adapter.clear();
                allClothing.addAll(clothes);
          //      Log.i("WardrobeFragment", "Clothing: " + allClothing.size());
                adapter.notifyDataSetChanged();
            }
        });


    }

    protected void queryClothes(String clothingType) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
        //query.include(Clothing.CLOTHING_TYPE);
        query.include(Clothing.KEY_CLOTHING_IMAGE);
        query.whereEqualTo(Clothing.KEY_USER, user);
        query.whereEqualTo(Clothing.KEY_CLOTHING_TYPE, clothingType);
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
                    Log.e("WardrobeFragment", "Issue with getting clothing", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Clothing clothing : clothes) {
                    //    Log.i("WardrobeFragment", "Clothing: " + clothing.getClothingType() );
                }

                // save received posts to list and notify adapter of new data
                // adapter.clear();
                allClothing.addAll(clothes);
                //      Log.i("WardrobeFragment", "Clothing: " + allClothing.size());
                adapter.notifyDataSetChanged();
            }
        });


    }
}