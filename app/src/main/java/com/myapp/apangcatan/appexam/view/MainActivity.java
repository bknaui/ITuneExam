package com.myapp.apangcatan.appexam.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.apangcatan.appexam.R;
import com.myapp.apangcatan.appexam.adapter.TrackAdapter;
import com.myapp.apangcatan.appexam.model.Track;
import com.myapp.apangcatan.appexam.util.CommonUtil;
import com.myapp.apangcatan.appexam.viewmodel.TrackViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TrackViewModel trackViewModel;
    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private SharedPreferences historySharedPreference;
    private SharedPreferences lastScreenSharedPreference;
    private TextView txtViewDateHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        txtViewDateHistory = findViewById(R.id.history_date_visit);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        trackAdapter = new TrackAdapter();
        recyclerView.setAdapter(trackAdapter);

        trackViewModel = ViewModelProviders.of(this).get(TrackViewModel.class);

        lastScreenSharedPreference = getSharedPreferences("last_screen", MODE_PRIVATE);

        //Proceed to last screen visited
        if (lastScreenSharedPreference.getString("img_url", null) != null) {

            String track_name = lastScreenSharedPreference.getString("track_name", null);
            String long_description = lastScreenSharedPreference.getString("long_description", null);
            String img_url = lastScreenSharedPreference.getString("img_url", null);

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("img_url", img_url);
            intent.putExtra("long_description", long_description);
            intent.putExtra("track_name", track_name);
            startActivity(intent);
        }

        trackViewModel.getMutableLiveData().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                trackAdapter.setList(tracks);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (CommonUtil.isNetworkConnected(this)) {
            trackViewModel.loadTracksFromRemote();
        }

        historySharedPreference = getSharedPreferences("history_visit", MODE_PRIVATE);
        if (historySharedPreference.getString("previous_date", null) != null) {
            txtViewDateHistory.setText("Last visited: " + historySharedPreference.getString("previous_date", null));
            txtViewDateHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = historySharedPreference.edit();
        editor.putString("previous_date", CommonUtil.CURRENT_DATE());
        editor.commit();
    }

}
