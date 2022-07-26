package com.example.pocketcloset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pocketcloset.R;
import com.example.pocketcloset.models.Clothing;
import com.example.pocketcloset.models.Outfit;
import com.parse.ParseFile;

import java.util.List;

public class OutfitsAdapter extends RecyclerView.Adapter<OutfitsAdapter.ViewHolder> {
    private final Context context;
    private final List<Outfit> outfits;

    public OutfitsAdapter(Context context, List<Outfit> outfits) {
        this.context = context;
        this.outfits = outfits;
    }

    @NonNull
    @Override
    public OutfitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_outfit_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitsAdapter.ViewHolder holder, int position) {
        Outfit outfit = outfits.get(position);
        holder.bind(outfit);
    }


    @Override
    public int getItemCount() {
        return outfits.size();
    }

    public void clear() {
        outfits.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Outfit> list) {
        outfits.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivTop;
        private final ImageView ivBottom;
        private final ImageView ivHeadwear;
        private final ImageView ivHandheld;
        private final ImageView ivShoes;
        private final ImageView ivOverwear;
        private final ImageView ivBracelet;
        private final ImageView ivNeckwear;
        private final TextView tvName;
        private final ImageView ivEarrings;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTop = itemView.findViewById(R.id.ivTop);
            ivBottom = itemView.findViewById(R.id.ivBottom);
            ivHeadwear = itemView.findViewById(R.id.ivHeadwear);
            ivHandheld = itemView.findViewById(R.id.ivHandheld);
            ivShoes = itemView.findViewById(R.id.ivShoes);
            ivNeckwear = itemView.findViewById(R.id.ivNecklace);
            ivBracelet = itemView.findViewById(R.id.ivBracelet);
            ivEarrings = itemView.findViewById(R.id.ivEarrings);
            ivOverwear = itemView.findViewById(R.id.ivOverwear);
            tvName = itemView.findViewById(R.id.tvName);

        }

        public void bind(Outfit outfit) {
            // Bind the post data to the view elements
            tvName.setText(outfit.getOutfitName());
            checkClothing(outfit.getBottoms(), ivBottom);
            checkClothing(outfit.getTop(), ivTop);
            checkClothing(outfit.getEarrings(), ivEarrings);
            checkClothing(outfit.getHeadwear(), ivHeadwear);
            checkClothing(outfit.getShoes(), ivShoes);
            checkClothing(outfit.getOverwear(), ivOverwear);
            checkClothing(outfit.getNeckwear(), ivNeckwear);
            checkClothing(outfit.getHandheld(), ivHandheld);
            checkClothing(outfit.getBracelet(), ivBracelet);
        }

        public void checkClothing(Clothing clothing, ImageView image) {
            ParseFile photo = null;
            if (clothing != null) {
                photo = clothing.getClothingImage();
            }
            if (photo != null) {
                Glide.with(context).load(photo.getUrl()).into(image);
            }
        }
    }


}

