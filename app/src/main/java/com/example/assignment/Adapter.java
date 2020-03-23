package com.example.assignment;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {
    private List<GetArtworksQuery.Artwork> artworks;
    private List<GetArtworksQuery.Artwork> artworksFull;



    public void setArtworks(List<GetArtworksQuery.Artwork> artworks) {
        this.artworks = new ArrayList<>(artworks);
        artworksFull=new ArrayList<>(artworks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems, parent, false);
        return new ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetArtworksQuery.Artwork artwork = artworks.get(position);
        holder.artistname.setText(artwork.artist_names());
        holder.artwork_name.setText(artwork.title());
        holder.price.setText(artwork.price);
        Glide.with(holder.itemView.getContext()).load(artwork.imageUrl).placeholder(R.drawable.ic_image_black_24dp).into(holder.imageView);
        if (Objects.equals(artwork.price, "")){
            holder.price.setText("Price not Available");
        }
    }

    @Override
    public int getItemCount() {
        if (artworks != null)
            return artworks.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GetArtworksQuery.Artwork> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                if (artworks != null)
                    filteredList.addAll(artworksFull);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                if (artworks != null)
                    for (GetArtworksQuery.Artwork item : artworksFull) {
                        if (item.title.toLowerCase().contains(filteredPattern)) {
                            filteredList.add(item);
                        }
                    }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (artworks != null) {
                artworks.clear();
                artworks.addAll((Collection<? extends GetArtworksQuery.Artwork>) results.values);
                notifyDataSetChanged();
            }
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView artwork_name, artistname, price;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            artistname = itemView.findViewById(R.id.artist_name);
            artwork_name = itemView.findViewById(R.id.artwork_name);
            price = itemView.findViewById(R.id.artwork_price);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
