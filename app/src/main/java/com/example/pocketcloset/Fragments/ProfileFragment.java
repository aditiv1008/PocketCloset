package com.example.pocketcloset.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pocketcloset.ClothingType;
import com.example.pocketcloset.R;
import com.example.pocketcloset.adapters.ClothingAdapter;
import com.example.pocketcloset.models.Clothing;
import com.example.pocketcloset.models.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment {
    public User user = (User) User.getCurrentUser();
    protected ClothingAdapter mostWornAdapter;
    protected ClothingAdapter leastWornAdapter;
    protected List<Clothing> allClothing;
    ImageButton arrow;
    ImageButton arrow2;
    ImageButton arrow3;
    TextView tvLeastWornShifted;
    TextView tvLeastWorn;
    TextView tvMostWorn;
    View dividerHide1;
    View dividerHide2;
    View dividerHide;
    View dividerShifted;
    View dividerShifted2;
    View divider2;
    View dividerShifted3;
    List<Clothing> mostWorn;
    List<Clothing> leastWorn;
    View divider;
    RecyclerView rvMostWorn;
    RecyclerView rvLeastWorn;
    RecyclerView rvLeastWornShifted;
    ConstraintLayout hiddenLayout;
    ConstraintLayout hiddenLayout2;
    private ImageView ivProfilePic;
    private TextView tvUsername;
    private TextView tvClothes;
    private TextView tvClothesCount;
    private Integer size;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // size = getArguments().getInt("wardrobeFragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvUsername = view.findViewById(R.id.tvUsername3);
        dividerHide1 = view.findViewById(R.id.dividerHide1);
        rvMostWorn = view.findViewById(R.id.rvMostWorn);
        tvLeastWorn = view.findViewById(R.id.tvLeastWorn);
        tvMostWorn = view.findViewById(R.id.tvMostWorn);
        dividerHide = view.findViewById(R.id.dividerHide);
        dividerShifted = view.findViewById(R.id.dividerShifted);
        dividerShifted2 = view.findViewById(R.id.dividerShifted2);
        dividerShifted3 = view.findViewById(R.id.dividerShifted3);
        arrow3 = view.findViewById(R.id.arrow3);
        tvLeastWornShifted = view.findViewById(R.id.tvLeastWornShifted);
        rvLeastWornShifted = view.findViewById(R.id.rvLeastWornShifted);
        divider2 = view.findViewById(R.id.divider2);
        tvClothes = view.findViewById(R.id.tvClothes);
        tvClothesCount = view.findViewById(R.id.tvClothesCount);
        hiddenLayout = view.findViewById(R.id.hiddenLayout);
        hiddenLayout2 = view.findViewById(R.id.hiddenLayout2);
        hiddenLayout2.setVisibility(View.GONE);
        dividerHide2 = view.findViewById(R.id.dividerHide2);
        divider = view.findViewById(R.id.divider);
        rvLeastWorn = view.findViewById(R.id.rvLeastWorn);
        rvMostWorn.setVisibility(View.GONE);
        rvLeastWorn.setVisibility(View.GONE);
        dividerHide1.setVisibility(View.GONE);
        dividerHide.setVisibility(View.GONE);
        hiddenLayout.setVisibility(View.GONE);

        allClothing = new ArrayList<>();
        queryMostWornClothes();
        queryLeastWornClothes();
        mostWorn = new ArrayList<>();
        leastWorn = new ArrayList<>();
        tvClothesCount.setText("" + size);


        mostWornAdapter = new ClothingAdapter(getContext(), mostWorn);
        leastWornAdapter = new ClothingAdapter(getContext(), leastWorn);
        arrow = view.findViewById(R.id.arrow);
        arrow2 = view.findViewById(R.id.arrow2);

        rvMostWorn.setAdapter(mostWornAdapter);
        rvLeastWorn.setAdapter(leastWornAdapter);
        rvLeastWornShifted.setAdapter(leastWornAdapter);


        rvMostWorn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvLeastWorn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvLeastWornShifted.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dividerHide.getVisibility() == View.VISIBLE) {
                    dividerHide.setVisibility(View.GONE);
                    divider2.setVisibility(View.VISIBLE);
                    rvLeastWorn.setVisibility(View.GONE);
                    arrow2.setImageResource(R.drawable.ic_expand_more);
                } else {
                    dividerHide.setVisibility(View.VISIBLE);
                    divider2.setVisibility(View.GONE);
                    rvLeastWorn.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.drawable.ic_expand_less);
                }
            }
        });

        arrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dividerShifted2.getVisibility() == View.GONE) {
                    dividerShifted2.setVisibility(View.VISIBLE);
                    rvLeastWornShifted.setVisibility(View.GONE);
                    arrow3.setImageResource(R.drawable.ic_expand_more);

                } else {
                    dividerShifted2.setVisibility(View.GONE);
                    rvLeastWornShifted.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.drawable.ic_expand_less);
                }
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (dividerHide1.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.

                    dividerHide1.setVisibility(View.GONE);
                    dividerHide2.setVisibility(View.VISIBLE);
                    rvMostWorn.setVisibility(View.GONE);
                    hiddenLayout.setVisibility(View.GONE);
                    divider2.setVisibility(View.GONE);
                    tvLeastWorn.setVisibility(View.GONE);
                    dividerHide.setVisibility(View.GONE);
                    arrow2.setVisibility(View.GONE);
                    divider2.setVisibility(View.VISIBLE);
                    tvLeastWorn.setVisibility(View.VISIBLE);
                    arrow2.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_expand_more);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {
                    dividerHide1.setVisibility(View.VISIBLE);
                    dividerHide2.setVisibility(View.GONE);
                    rvLeastWorn.setVisibility(View.GONE);
                    dividerHide.setVisibility(View.GONE);
                    rvMostWorn.setVisibility(View.VISIBLE);
                    hiddenLayout.setVisibility(View.VISIBLE);
                    rvLeastWornShifted.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_expand_less);
                    arrow2.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });

        user.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                user = (User) object;
                displayUserInfo();
            }
        });

    }

    public void displayUserInfo() {
        ParseFile profilePhoto = user.getProfilePhoto();
        if (profilePhoto != null) {
            Glide.with(getContext()).load(user.getProfilePhoto().getUrl()).circleCrop().into(ivProfilePic);
        } else {
            Toast.makeText(getContext(), "Profile photo does not exist for" + user.getUsername(), Toast.LENGTH_SHORT).show();
        }
        tvUsername.setText(user.getUsername());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                Glide.with(getContext()).load(takenImage).circleCrop().into(ivProfilePic);
                user.setProfilePhoto(new ParseFile(photoFile));
                user.saveInBackground();
                // ivprofilePic.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
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

    public Clothing MostWorn(String clothingType) {
        List<Clothing> clothing = filterClothing(allClothing, clothingType);
        if (clothing != null) {
            Collections.sort(clothing, (d1, d2) -> {
                return d2.getWornCount() - d1.getWornCount();
            });
        }

        if (clothing != null && clothing.size() > 0) {
            return clothing.get(0);
        }
        return null;
    }

    public Clothing LeastWorn(String clothingType) {
        List<Clothing> clothing = filterClothing(allClothing, clothingType);
        if (clothing != null) {
            Collections.sort(clothing, (d1, d2) -> {
                return d2.getWornCount() - d1.getWornCount();
            });
            Collections.reverse(clothing);
        }

        if (clothing != null && clothing.size() > 0) {
            return clothing.get(0);
        }
        return null;
    }

    protected void queryClothes(ClothingType clothingType, String worn, ClothingAdapter adapter) {
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
        query.include(Clothing.KEY_CLOTHING_IMAGE);
        query.whereEqualTo(Clothing.KEY_USER, user);

        // limit query to latest 20 items
        query.setLimit(1);
        // order posts by creation date (newest first)
        if (worn == "most worn") {
            query.addDescendingOrder("wornCount");
        } else {
            query.addAscendingOrder("wornCount");

            query.findInBackground(new FindCallback<Clothing>() {
                @Override
                public void done(List<Clothing> clothes, ParseException e) {
                    // check for errors
                    if (e != null) {
                        return;
                    }
                    // save received posts to list and notify adapter of new data'
                    adapter.clear();
                    allClothing.addAll(clothes);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }


    protected void queryMostWornClothes() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
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
                    return;
                }
                // save received posts to list and notify adapter of new data'
                mostWornAdapter.clear();
                allClothing.addAll(clothes);
                mostWornAdapter.notifyDataSetChanged();

                if ((MostWorn(ClothingType.TOP) != null)) {
                    mostWorn.add(MostWorn(ClothingType.TOP));
                }
                if ((MostWorn(ClothingType.HEADWEAR) != null)) {
                    mostWorn.add(MostWorn(ClothingType.HEADWEAR));
                }
                if ((MostWorn(ClothingType.PANTS) != null)) {
                    mostWorn.add(MostWorn(ClothingType.PANTS));
                }
                if ((MostWorn(ClothingType.DRESS) != null)) {
                    mostWorn.add(MostWorn(ClothingType.DRESS));
                }
                if ((MostWorn(ClothingType.SHOES) != null)) {
                    mostWorn.add(MostWorn(ClothingType.SHOES));
                }
                if ((MostWorn(ClothingType.OVERWEAR) != null)) {
                    mostWorn.add(MostWorn(ClothingType.OVERWEAR));
                }
                if ((MostWorn(ClothingType.HANDHELD) != null)) {
                    mostWorn.add(MostWorn(ClothingType.HANDHELD));
                }
                if ((MostWorn(ClothingType.BRACELET) != null)) {
                    mostWorn.add(MostWorn(ClothingType.BRACELET));
                }
                if ((MostWorn(ClothingType.EARRINGS) != null)) {
                    mostWorn.add(MostWorn(ClothingType.EARRINGS));
                }


            }
        });


    }

    protected void queryLeastWornClothes() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Clothing> query = ParseQuery.getQuery(Clothing.class);
        // include data referred by user key
        Log.i("WardrobeFragment", "queryClothes(with params) ran");
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
                    return;
                }
                // save received posts to list and notify adapter of new data'
                leastWornAdapter.clear();
                allClothing.addAll(clothes);
                leastWornAdapter.notifyDataSetChanged();

                if ((LeastWorn(ClothingType.TOP) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.TOP));
                }
                if ((LeastWorn(ClothingType.HEADWEAR) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.HEADWEAR));
                }
                if ((LeastWorn(ClothingType.PANTS) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.PANTS));
                }
                if ((LeastWorn(ClothingType.DRESS) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.DRESS));
                }
                if ((LeastWorn(ClothingType.SHOES) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.SHOES));
                }
                if ((LeastWorn(ClothingType.OVERWEAR) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.OVERWEAR));
                }
                if ((LeastWorn(ClothingType.HANDHELD) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.HANDHELD));
                }
                if ((LeastWorn(ClothingType.BRACELET) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.BRACELET));
                }
                if ((LeastWorn(ClothingType.EARRINGS) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.EARRINGS));
                }
                if ((LeastWorn(ClothingType.NECKWEAR) != null)) {
                    leastWorn.add(LeastWorn(ClothingType.NECKWEAR));
                }


            }
        });


    }
}