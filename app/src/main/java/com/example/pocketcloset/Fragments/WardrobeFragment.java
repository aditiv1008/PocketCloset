package com.example.pocketcloset.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private ParseUser user;
    private MaterialButtonToggleGroup toggleButton;
    private Button btnHeadwear;
    private Button btnTops;
    private Button btnOverwear;
    private Button btnBottoms;
    private Button btnAccessories;
    private Button btnShoes;
    private Button btnPants;
    private Button btnDress;
    private Button btnBack;
    private Button btnSkirt;

    public MaterialButtonToggleGroup toggleButtonBottoms;

    public WardrobeFragment() {
        // Require d empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ClothingAdapter(getContext(), new ArrayList<Clothing>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         user = getArguments().getParcelable("userToFilterBy");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wardrobe, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvWardrobe = view.findViewById(R.id.rvClothes);
        toggleButton = view.findViewById(R.id.toggleButton);
        toggleButtonBottoms = view.findViewById(R.id.toggleButtonBottoms);
        toggleButtonBottoms.setVisibility(View.INVISIBLE);
        btnAccessories = view.findViewById(R.id.btnAccessories);
        btnTops = view.findViewById(R.id.btnTop);
        btnHeadwear = view.findViewById(R.id.btnHeadwear);
        btnOverwear = view.findViewById(R.id.btnOverwear);
        btnBottoms = view.findViewById(R.id.btnBottoms);
        btnPants = view.findViewById(R.id.btnPants);
        btnDress = view.findViewById(R.id.btnDress);
        btnShoes = view.findViewById(R.id.btnShoes);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setVisibility(View.GONE);
        btnSkirt = view.findViewById(R.id.btnSkirt);
        allClothing = new ArrayList<>();
        adapter = new ClothingAdapter(getContext(), allClothing);

        rvWardrobe.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvWardrobe.setLayoutManager(new LinearLayoutManager(getContext()));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                2 /* numberOfColumns */);

        rvWardrobe.setLayoutManager(gridLayoutManager);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
         //Setup refresh listener which triggers new data loading
       swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryClothes(null);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
       swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
               android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


       btnTops.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               adapter.clear();
               queryClothes("Top");
               adapter.notifyDataSetChanged();
           }
       });

        btnOverwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                queryClothes("Overwear");
                adapter.notifyDataSetChanged();
            }
        });
        btnHeadwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                queryClothes("Headwear");
                adapter.notifyDataSetChanged();
            }
        });
        btnAccessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                queryClothes("Earrings");
                adapter.notifyDataSetChanged();
            }
        });
        btnShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                queryClothes("Shoes");
                adapter.notifyDataSetChanged();
            }
        });
        btnBottoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                btnBack.setVisibility(View.VISIBLE);
                toggleButtonBottoms.setVisibility(View.VISIBLE);
                adapter.clear();
               queryClothes("Dress");
               queryClothes("Pants");
                adapter.notifyDataSetChanged();
            }
        });

        btnDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.INVISIBLE);
                toggleButtonBottoms.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes("Dress");
                adapter.notifyDataSetChanged();
            }
        });
        btnPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonBottoms.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes("Pants");
                adapter.notifyDataSetChanged();
            }
        });
        btnSkirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonBottoms.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes("Skirt");
                adapter.notifyDataSetChanged();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.VISIBLE);
                toggleButtonBottoms.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                adapter.clear();
                queryClothes(null);
                adapter.notifyDataSetChanged();
            }
        });

       queryClothes(null);
    }

  /*  protected void queryClothes() {
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
                // save received posts to list and notify adapter of new data
                adapter.clear();
                allClothing.addAll(clothes);
                adapter.notifyDataSetChanged();
            }
        });


    }*/


    protected void queryClothes( @Nullable String clothingType) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
        Log.i("WardrobeFragment", "queryClothes(with params) ran");
        query.include(Clothing.KEY_CLOTHING_IMAGE);
        query.whereEqualTo(Clothing.KEY_USER, user);
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
                    Log.e("WardrobeFragment", "Issue with getting clothing", e);
                    return;
                }

                // save received posts to list and notify adapter of new data
                 //adapter.clear();
                allClothing.addAll(clothes);
                adapter.notifyDataSetChanged();
            }
        });


    }
}