package com.example.pocketcloset.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pocketcloset.ClothingType;
import com.example.pocketcloset.LoginActivity;
import com.example.pocketcloset.MainActivity;
import com.example.pocketcloset.R;
import com.example.pocketcloset.RandomizedView;
import com.example.pocketcloset.adapters.ClothingAdapter;
import com.example.pocketcloset.models.Clothing;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private MaterialButtonToggleGroup toggleButtonAccessories;
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
    private Button btnNeckwear;
    private Button btnEarrings;
    private Button btnHandhelds;
    private Button btnBracelet;
    private FloatingActionButton btnRandomize;

    public MaterialButtonToggleGroup toggleButtonBottoms;

    public WardrobeFragment() {
        // Require d empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ClothingAdapter(getContext(), new ArrayList<Clothing>());

        Bundle bundl = new Bundle();
        bundl.putParcelableArrayList("allClothing", (ArrayList<? extends Parcelable>) allClothing);



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
        toggleButtonAccessories = view.findViewById(R.id.toggleAccessories);
        toggleButtonAccessories.setVisibility(View.INVISIBLE);
        toggleButtonBottoms.setVisibility(View.INVISIBLE);
        btnAccessories = view.findViewById(R.id.btnAccessories);
        btnRandomize = view.findViewById(R.id.btnRandomize);
        btnTops = view.findViewById(R.id.btnTop);
        btnHeadwear = view.findViewById(R.id.btnHeadwear);
        btnOverwear = view.findViewById(R.id.btnOverwear);
        btnBottoms = view.findViewById(R.id.btnBottoms);
        btnPants = view.findViewById(R.id.btnPants);
        btnDress = view.findViewById(R.id.btnDress);
        btnBracelet = view.findViewById(R.id.btnBracelets);
        btnNeckwear = view.findViewById(R.id.btnNeckwear);
        btnEarrings = view.findViewById(R.id.btnEarrings);
        btnHandhelds = view.findViewById(R.id.btnHandhelds);
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

       btnRandomize.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getContext(), RandomizedView.class);
               startActivity(i);
           }
       });

       btnTops.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               adapter.clear();
               queryClothes(ClothingType.TOP);
               Log.i("Shirt", "" + allClothing.size());
               adapter.notifyDataSetChanged();
           }
       });

        btnOverwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                queryClothes(ClothingType.OVERWEAR);
                adapter.notifyDataSetChanged();
            }
        });
        btnHeadwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                queryClothes(ClothingType.HEADWEAR);
                adapter.notifyDataSetChanged();
            }
        });
        btnAccessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                btnBack.setVisibility(View.VISIBLE);
                toggleButtonAccessories.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.EARRINGS);
                queryClothes(ClothingType.HANDHELD);
                queryClothes(ClothingType.NECKWEAR);
                queryClothes(ClothingType.BRACELET);
                adapter.notifyDataSetChanged();
            }
        });
        btnShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                queryClothes(ClothingType.SHOES);
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
               queryClothes(ClothingType.DRESS);
               queryClothes(ClothingType.PANTS);
                queryClothes(ClothingType.SKIRT);
                adapter.notifyDataSetChanged();
            }
        });

        btnDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.INVISIBLE);
                toggleButtonBottoms.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.DRESS);
                adapter.notifyDataSetChanged();
            }
        });
        btnPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonBottoms.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.PANTS);
                adapter.notifyDataSetChanged();
            }
        });
        btnBracelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonAccessories.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.BRACELET);
                adapter.notifyDataSetChanged();
            }
        });
        btnEarrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonAccessories.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.EARRINGS);
                adapter.notifyDataSetChanged();
            }
        });
        btnNeckwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonAccessories.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.NECKWEAR);
                adapter.notifyDataSetChanged();
            }
        });
        btnHandhelds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonAccessories.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.HANDHELD);
                adapter.notifyDataSetChanged();
            }
        });
        btnSkirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.GONE);
                toggleButtonBottoms.setVisibility(View.VISIBLE);
                adapter.clear();
                queryClothes(ClothingType.SKIRT);
                adapter.notifyDataSetChanged();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setVisibility(View.VISIBLE);
                toggleButtonBottoms.setVisibility(View.GONE);
                toggleButtonAccessories.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                adapter.clear();
                queryClothes(null);
                adapter.notifyDataSetChanged();
            }
        });

       queryClothes(null);
    }


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
                allClothing.addAll(clothes);
                adapter.notifyDataSetChanged();
                Log.i("WARDROBE FRAGMENT CLOTHES", "" + allClothing.size());
            }
        });


    }
}