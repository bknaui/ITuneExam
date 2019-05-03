package com.myapp.apangcatan.appexam.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.myapp.apangcatan.appexam.R;
import com.myapp.apangcatan.appexam.adapter.TrackAdapter;
import com.myapp.apangcatan.appexam.model.Track;
import com.myapp.apangcatan.appexam.util.Constant;
import com.myapp.apangcatan.appexam.viewmodel.TrackViewModel;

import java.util.List;

public class TrackActivity extends AppCompatActivity {
    private TextView textDateHistory;
    private RecyclerView recyclerView;

    private TrackViewModel trackViewModel;
    private TrackAdapter trackAdapter;

    private SharedPreferences historySharedPreference;
    private SharedPreferences lastScreenSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastScreenSharedPreference = getSharedPreferences(Constant.LAST_SCREEN_PREF, MODE_PRIVATE);
        historySharedPreference = getSharedPreferences(Constant.DATE_VISIT_PREF, MODE_PRIVATE);

        recyclerView = findViewById(R.id.recycler_view);
        textDateHistory = findViewById(R.id.text_date_history);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        trackAdapter = new TrackAdapter();
        recyclerView.setAdapter(trackAdapter);

        trackViewModel = ViewModelProviders.of(this).get(TrackViewModel.class);

        //Proceed to last screen visited
        if (lastScreenSharedPreference.getString(Constant.ARTWORK_URL_EXTRA, null) != null) {

            String track_name = lastScreenSharedPreference.getString(Constant.TRACK_NAME_EXTRA, null);
            String long_description = lastScreenSharedPreference.getString(Constant.TRACK_DESCRIPTION_EXTRA, null);
            String img_url = lastScreenSharedPreference.getString(Constant.ARTWORK_URL_EXTRA, null);

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Constant.TRACK_NAME_EXTRA, track_name);
            intent.putExtra(Constant.TRACK_DESCRIPTION_EXTRA, long_description);
            intent.putExtra(Constant.ARTWORK_URL_EXTRA, img_url);

            startActivity(intent);
        }

        //Update track list whenever new track(s) are added
        trackViewModel.getMutableLiveData().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                trackAdapter.setList(tracks);
            }
        });

        //Prompts a snackbar whenever there is a problem loading tracks
        trackViewModel.getMutableLiveDataMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                displaySnackBar(message);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constant.isNetworkConnected(this)) {
            trackViewModel.loadTracksFromRemote();
        } else {
            trackViewModel.getMutableLiveDataMessage().setValue("No network connection");
        }

        //Display last visit by date time
        if (historySharedPreference.getString(Constant.PREVIOUS_DATE_TIME, null) != null) {
            textDateHistory.setText("Last visited: " + historySharedPreference.getString(Constant.PREVIOUS_DATE_TIME, null));
            textDateHistory.setVisibility(View.VISIBLE);
        }
    }

    public void displaySnackBar(String message) {
        Snackbar.make(findViewById(R.id.main_content), message, Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.isNetworkConnected(v.getContext())) {
                    trackViewModel.loadTracksFromRemote();
                } else {
                    trackViewModel.getMutableLiveDataMessage().setValue("No network connection");
                }
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Store current datetime when leaving the application
        SharedPreferences.Editor editor = historySharedPreference.edit();
        editor.putString(Constant.PREVIOUS_DATE_TIME, Constant.getCurrentDateTime());
        editor.commit();
    }
}
