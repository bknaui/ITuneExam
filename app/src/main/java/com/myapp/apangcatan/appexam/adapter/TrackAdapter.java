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
import com.myapp.apangcatan.appexam.util.Constant;
import com.myapp.apangcatan.appexam.util.GlideApp;
import com.myapp.apangcatan.appexam.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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
        holder.textTrackName.setText(trackList.get(position).getTrackName());
        holder.textTrackPrice.setText(trackList.get(position).getPrice() + "");
        holder.textTrackGenre.setText(trackList.get(position).getGenre());

        GlideApp
                .with(holder.itemView.getContext())
                .load(trackList.get(position).getArtWork())
                .centerCrop()
                .transition(withCrossFade())
                .placeholder(R.drawable.glide_placeholder)
                .error(R.drawable.glide_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgArtwork);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(Constant.TRACK_NAME_EXTRA, trackList.get(position).getTrackName());
                intent.putExtra(Constant.TRACK_DESCRIPTION_EXTRA, trackList.get(position).getLongDescription());
                intent.putExtra(Constant.ARTWORK_URL_EXTRA, trackList.get(position).getArtWork());

                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {
        ImageView imgArtwork;
        TextView textTrackName;
        TextView textTrackPrice;
        TextView textTrackGenre;


        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            imgArtwork = itemView.findViewById(R.id.img_item_artwork);
            textTrackName = itemView.findViewById(R.id.text_item_name);
            textTrackPrice = itemView.findViewById(R.id.text_item_price);
            textTrackGenre = itemView.findViewById(R.id.text_item_genre);
        }
    }
}
