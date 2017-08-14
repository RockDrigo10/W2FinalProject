package com.example.admin.week2projectapp;

import android.content.Context;

/**
 * Created by Admin on 8/13/2017.
 */

public class Chronometer implements Runnable {
    Context context;
    private long mStartTime;
    private long mStopTime;
    boolean isRunning;
    public static final long MILLIS_TO_MINUTS = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;

    public Chronometer(Context context) {
        this.context = context;
    }

    public void start() {
        mStartTime = System.currentTimeMillis();
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            long since = System.currentTimeMillis() - mStartTime;
            int seconds = (int) since / (1000) % 60;
            int min = (int) ((since / (MILLIS_TO_MINUTS)) % 60);
            int hours = (int) ((since / (MILLIS_TO_HOURS)) % 24);
            int mills = (int) since % 1000;

            ((SecondActivity)context).updateTimerText(String.format(
                    "%02d:%02d:%02d:%02d", hours, min, seconds, mills));
        }
    }

}
