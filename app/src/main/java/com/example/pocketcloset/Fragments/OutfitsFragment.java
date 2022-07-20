package com.example.pocketcloset.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pocketcloset.R;
import com.example.pocketcloset.adapters.ClothingAdapter;
import com.example.pocketcloset.adapters.OutfitsAdapter;
import com.example.pocketcloset.models.Clothing;
import com.example.pocketcloset.models.Outfit;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutfitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutfitsFragment extends Fragment {
    protected OutfitsAdapter adapter;
    protected List<Outfit> allOutfits;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvOutfits;
    private ParseUser user;

    public OutfitsFragment() {
        // Required empty public constructor
    }


    public static OutfitsFragment newInstance(String param1, String param2) {
        OutfitsFragment fragment = new OutfitsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user = getArguments().getParcelable("userToFilterBy");
        return inflater.inflate(R.layout.fragment_outfits, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allOutfits = new ArrayList<>();
        adapter = new OutfitsAdapter(getContext(), allOutfits);
        rvOutfits = view.findViewById(R.id.rvOutfits);

        rvOutfits.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvOutfits.setLayoutManager(new LinearLayoutManager(getContext()));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                2 /* numberOfColumns */);

        rvOutfits.setLayoutManager(gridLayoutManager);


        swipeContainer = view.findViewById(R.id.swipeContainer);
        //Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryOutfits();
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        queryOutfits();
    }

    protected void queryOutfits() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Outfit> query = ParseQuery.getQuery(Outfit.class);
        // include data referred by user key
        Log.i("WardrobeFragment", "queryClothes(with params) ran");
        query.include(Outfit.KEY_OUTFIT_NAME);
        query.include(Outfit.KEY_BOTTOMS);
        query.include(Outfit.KEY_TOP);
        query.include(Outfit.KEY_BRACELET);
        query.include(Outfit.KEY_NECKWEAR);
        query.include(Outfit.KEY_SHOES);
        query.include(Outfit.KEY_OVERWEAR);
        query.include(Outfit.KEY_HANDHELD);
        query.include(Outfit.KEY_EARRINGS);
        query.include(Outfit.KEY_HEADWEAR);
        query.whereEqualTo(Outfit.KEY_USER, user);

        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Outfit>() {
            @Override
            public void done(List<Outfit> outfits, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e("WardrobeFragment", "Issue with getting clothing", e);
                    return;
                }
                // save received posts to list and notify adapter of new data
                allOutfits.addAll(outfits);
                adapter.notifyDataSetChanged();
                Log.i("WARDROBE FRAGMENT CLOTHES", "" + allOutfits.size());
            }
        });


    }
}