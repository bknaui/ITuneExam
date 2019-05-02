package com.myapp.apangcatan.appexam.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.myapp.apangcatan.appexam.R;
import com.myapp.apangcatan.appexam.model.Track;
import com.myapp.apangcatan.appexam.util.GlideApp;
import com.myapp.apangcatan.appexam.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {
    private List<Track> trackList = new ArrayList<>();

    public TrackAdapter() {
    }

    public void setList(List<Track> list) {
        trackList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item_layout, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, final int position) {
        holder.txtviewTrackName.setText(trackList.get(position).getTrackName());
        holder.txtviewTrackPrice.setText(trackList.get(position).getPrice() + "");
        holder.txtviewTrackGenre.setText(trackList.get(position).getGenre());

        GlideApp
                .with(holder.itemView.getContext())
                .load(trackList.get(position).getArtWork())
                .centerCrop()
                .placeholder(R.drawable.glide_placeholder)
                .error(R.drawable.glide_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgArtWork);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("track_name", trackList.get(position).getTrackName());
                intent.putExtra("img_url", trackList.get(position).getArtWork());
                intent.putExtra("long_description", trackList.get(position).getLongDescription());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {
        ImageView imgArtWork;
        TextView txtviewTrackName;
        TextView txtviewTrackPrice;
        TextView txtviewTrackGenre;


        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            imgArtWork = itemView.findViewById(R.id.track_artwork);
            txtviewTrackName = itemView.findViewById(R.id.track_name);
            txtviewTrackPrice = itemView.findViewById(R.id.track_price);
            txtviewTrackGenre = itemView.findViewById(R.id.track_genre);
        }
    }
}
