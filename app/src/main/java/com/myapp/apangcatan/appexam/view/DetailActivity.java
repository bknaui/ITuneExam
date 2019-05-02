package com.myapp.apangcatan.appexam.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.myapp.apangcatan.appexam.R;
import com.myapp.apangcatan.appexam.util.GlideApp;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageDetail;
    private TextView descriptionDetail;
    private TextView txtviewTrackName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_detail_layout);

        sharedPreferences = getSharedPreferences("last_screen", MODE_PRIVATE);

        imageDetail = findViewById(R.id.track_detail_img);
        descriptionDetail = findViewById(R.id.track_detail_description);
        txtviewTrackName = findViewById(R.id.track_detail_name);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String trackName = b.getString("track_name");
            String imgUrl = b.getString("img_url");
            String longDescription = b.getString("long_description");

            txtviewTrackName.setText(trackName);
            descriptionDetail.setText(longDescription);

            GlideApp
                    .with(this)
                    .load(imgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.glide_placeholder)
                    .error(R.drawable.glide_error)
                    .into(imageDetail);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("track_name", trackName);
            editor.putString("long_description", longDescription);
            editor.putString("img_url", imgUrl);
            editor.commit();



        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
        super.onBackPressed();
    }
}
