package com.example.admin.week2projectapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Admin on 8/12/2017.
 */

public class MyDialogFragment extends DialogFragment {
    ImageView ivImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog,container,false);
        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        getDialog().setTitle("Image");

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CountDownTimer gameTimer = new CountDownTimer(2500, 2500) {
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                dismiss();
            }
        };
        gameTimer.start();
    }
}
