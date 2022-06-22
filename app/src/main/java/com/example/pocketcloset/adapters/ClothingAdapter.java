package com.example.pocketcloset.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pocketcloset.R;
import com.example.pocketcloset.models.Clothing;
import com.parse.ParseFile;

import java.util.List;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ViewHolder> {
    private Context context;
    private List<Clothing> clothes;

    public ClothingAdapter(Context context, List<Clothing> clothes) {
        this.context = context;
        this.clothes = clothes;
    }

    @NonNull
    @Override
    public ClothingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wardrobe_clothing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingAdapter.ViewHolder holder, int position) {
        Clothing clothing = clothes.get(position);
        holder.bind(clothing);
    }


    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public void clear() {
        clothes.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Clothing> list) {
        clothes.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivWardrobePhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWardrobePhoto = itemView.findViewById(R.id.ivWardrobePhoto);

        }

        public void bind(Clothing clothing) {
            // Bind the post data to the view elements
            Log.i("ADAPTER", "IM IN BIND");

            ParseFile image = clothing.getClothingImage();
            Log.i("ADAPTER", image.getUrl());
            if (image != null) {
                Log.i("ADAPTER", "I am hereeeee");
                Glide.with(context).load(image.getUrl()).into(ivWardrobePhoto);
              //  Log.i("ADAPTER", image.getUrl());
            }
        }


    }
}
