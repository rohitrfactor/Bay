package com.example.garorasu.bay.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.garorasu.bay.Model.Fare;

/**
 * Created by garorasu on 9/12/16.
 */

public class PreferencesHelper {
    private static final String FARE_PREFERENCES = "farePreferences";
    private static final String TWO_WHEELER_BASE_FARE = FARE_PREFERENCES + "twoWheelerBaseFare";
    private static final String TWO_WHEELER_BASE_HOUR = FARE_PREFERENCES + "twoWheelerBaseHour";
    private static final String TWO_WHEELER_RATE_PER_HOUR = FARE_PREFERENCES + "twoWheelerRatePerHour";
    private static final String FOUR_WHEELER_BASE_FARE = FARE_PREFERENCES + "fourWheelerBaseFare";
    private static final String FOUR_WHEELER_BASE_HOUR = FARE_PREFERENCES + "fourWheelerBaseHour";
    private static final String FOUR_WHEELER_RATE_PER_HOUR = FARE_PREFERENCES + "fourWheelerRatePerHour";

    private PreferencesHelper(){

    }
    public static void writeToPreferences(Context context,Fare fare){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(TWO_WHEELER_BASE_FARE,fare.getTwoWheelerBaseFare());
        editor.putInt(TWO_WHEELER_BASE_HOUR,fare.getTwoWheelerBaseHour());
        editor.putInt(TWO_WHEELER_RATE_PER_HOUR,fare.getTwoWheelerRatePerHour());
        editor.putInt(FOUR_WHEELER_BASE_FARE,fare.getFourWheelerBaseFare());
        editor.putInt(FOUR_WHEELER_BASE_HOUR,fare.getFourWheelerBaseHour());
        editor.putInt(FOUR_WHEELER_RATE_PER_HOUR,fare.getFourWheelerRatePerHour());
        editor.apply();
    }
    public static Fare getFare(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        final int twoWheelerBaseFare = preferences.getInt(TWO_WHEELER_BASE_FARE,0);
        final int twoWheelerBaseHour = preferences.getInt(TWO_WHEELER_BASE_HOUR,0);
        final int twoWheelerRatePerHour = preferences.getInt(TWO_WHEELER_RATE_PER_HOUR,0);
        final int fourWheelerBaseFare = preferences.getInt(FOUR_WHEELER_BASE_FARE,0);
        final int fourWheelerBaseHour = preferences.getInt(FOUR_WHEELER_BASE_HOUR,0);
        final int fourWheelerRatePerHour = preferences.getInt(FOUR_WHEELER_RATE_PER_HOUR,0);
        return new Fare(twoWheelerBaseFare,twoWheelerBaseHour,twoWheelerRatePerHour,
                fourWheelerBaseFare,fourWheelerBaseHour,fourWheelerRatePerHour);
    }
    public static void removeFare(Context context){
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(TWO_WHEELER_BASE_FARE);
        editor.remove(TWO_WHEELER_BASE_HOUR);
        editor.remove(TWO_WHEELER_RATE_PER_HOUR);
        editor.remove(FOUR_WHEELER_BASE_FARE);
        editor.remove(FOUR_WHEELER_BASE_HOUR);
        editor.remove(FOUR_WHEELER_RATE_PER_HOUR);
        editor.apply();
    }
    public static boolean isFareSet(Context context){
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(TWO_WHEELER_BASE_FARE) &&
                preferences.contains(TWO_WHEELER_BASE_HOUR) &&
                preferences.contains(TWO_WHEELER_RATE_PER_HOUR) &&
                preferences.contains(FOUR_WHEELER_BASE_FARE) &&
                preferences.contains(FOUR_WHEELER_BASE_HOUR) &&
                preferences.contains(FOUR_WHEELER_RATE_PER_HOUR);

    };
    public static boolean isInputDataValid(CharSequence twoWheelerBaseFare,CharSequence twoWheelerBaseHour,
                                           CharSequence twoWheelerRatePerHour, CharSequence fourWheelerBaseFare,
                                           CharSequence fourWheelerBaseHour,CharSequence fourWheelerRatePerHour) {
        return !TextUtils.isEmpty(twoWheelerBaseFare) && !TextUtils.isEmpty(twoWheelerBaseHour) &&
                !TextUtils.isEmpty(twoWheelerRatePerHour) && !TextUtils.isEmpty(fourWheelerBaseFare) &&
                !TextUtils.isEmpty(fourWheelerBaseHour) && !TextUtils.isEmpty(fourWheelerRatePerHour);
    }
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FARE_PREFERENCES, Context.MODE_PRIVATE);
    }

}
