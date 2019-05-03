package com.myapp.apangcatan.appexam.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Constant {

    public static final String BASE_URL = "https://itunes.apple.com";

    public static final String TRACK_NAME_EXTRA = "track_name";
    public static final String ARTWORK_URL_EXTRA = "track_artwork";
    public static final String TRACK_DESCRIPTION_EXTRA = "track_description";

    public static final String PREVIOUS_DATE_TIME = "previous_datetime_visit";

    public static final String DATE_VISIT_PREF = "date_visit_pref";
    public static final String LAST_SCREEN_PREF = "last_screen_pref";

    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return df.format(calendar.getTime());
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
