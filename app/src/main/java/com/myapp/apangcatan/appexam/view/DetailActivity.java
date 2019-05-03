package com.myapp.apangcatan.appexam.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.myapp.apangcatan.appexam.R;
import com.myapp.apangcatan.appexam.util.Constant;
import com.myapp.apangcatan.appexam.util.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageArtwork;
    private TextView textDescription;
    private SharedPreferences lastScreenSharedPreference;
    private SharedPreferences historySharedPreference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_item_detail);
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        imageArtwork = findViewById(R.id.img_artwork_detail);
        textDescription = findViewById(R.id.text_description_detail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        lastScreenSharedPreference = getSharedPreferences(Constant.LAST_SCREEN_PREF, MODE_PRIVATE);
        historySharedPreference = getSharedPreferences(Constant.DATE_VISIT_PREF, MODE_PRIVATE);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String trackName = b.getString(Constant.TRACK_NAME_EXTRA);
            String trackArtwork = b.getString(Constant.ARTWORK_URL_EXTRA);
            String trackDescription = b.getString(Constant.TRACK_DESCRIPTION_EXTRA);

            GlideApp
                    .with(this)
                    .load(trackArtwork)
                    .centerCrop()
                    .transition(withCrossFade())
                    .placeholder(R.drawable.glide_placeholder)
                    .error(R.drawable.glide_error)
                    .into(imageArtwork);

            SharedPreferences.Editor editor = lastScreenSharedPreference.edit();
            editor.putString(Constant.TRACK_NAME_EXTRA, trackName);
            editor.putString(Constant.ARTWORK_URL_EXTRA, trackArtwork);
            editor.putString(Constant.TRACK_DESCRIPTION_EXTRA, trackDescription);

            editor.commit();

            textDescription.setText(trackDescription);
            collapsingToolbarLayout.setTitle(trackName);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = lastScreenSharedPreference.edit();
        editor.clear().commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = historySharedPreference.edit();
        editor.putString(Constant.PREVIOUS_DATE_TIME, Constant.getCurrentDateTime());
        editor.commit();
    }
}
