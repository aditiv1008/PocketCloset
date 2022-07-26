package com.example.pocketcloset.adapters;

import android.content.Context;
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

public class RandomizedAdapter extends RecyclerView.Adapter<RandomizedAdapter.ViewHolder> {
    private final Context context;
    private final List<Clothing> clothes;

    public RandomizedAdapter(Context context, List<Clothing> clothes) {
        this.context = context;
        this.clothes = clothes;
    }

    @NonNull
    @Override
    public RandomizedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_randomized_clothing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomizedAdapter.ViewHolder holder, int position) {
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

        private final ImageView ivWardrobePhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWardrobePhoto = itemView.findViewById(R.id.ivWardrobePhoto);

        }

        public void bind(Clothing clothing) {
            // Bind the post data to the view elements

            ParseFile image = clothing.getClothingImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).override(ivWardrobePhoto.getWidth(), ivWardrobePhoto.getHeight()).fitCenter().into(ivWardrobePhoto);
            }
        }


    }
}
