package com.example.admin.week2projectapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener,TimerFragment.OnFragmentInteractionListener,StartStopFragment.OnFragmentInteractionListener {
    private static final String TAG = "Second";
    WebView wvWeb;
    Button btnDefault, btnCustom, btnArray;
    TextView tvTextArray;
    AlertDialog dialog;
    String[] planetsList;
    boolean[] checkedItems;
    ArrayList<Integer> selectedItems = new ArrayList<>();
    String action = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        wvWeb = (WebView) findViewById(R.id.wvWeb);
        btnDefault = (Button) findViewById(R.id.btnDefault);
        btnCustom = (Button) findViewById(R.id.btnCustom);
        btnArray = (Button) findViewById(R.id.btnArray);
        String url = "https://www.bvb.de/";
        if (savedInstanceState == null) {
            wvWeb.loadUrl(url);
        }
        // Enable Javascript
        WebSettings webSettings = wvWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Force links and redirects to open in the WebView instead of in a browser
        wvWeb.setWebViewClient(new WebViewClient());
        wvWeb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });
        planetsList = getResources().getStringArray(R.array.planets);
        checkedItems = new boolean[planetsList.length];
        btnDefault.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        btnArray.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDefault:
                AlertDialog defaultDialog = new AlertDialog.Builder(this)
                        .setTitle("Default Alert Dialog")
                        .setMessage("This is the Default Alert dialog")
                        .setNeutralButton("OK", null)
                        .show();
                break;
            case R.id.btnCustom:
                final AlertDialog.Builder customDialog = new AlertDialog.Builder(this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                ImageView ivImage = (ImageView) mView.findViewById(R.id.ivImage);
                final TextView tvText = (TextView) mView.findViewById(R.id.tvText);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                customDialog.setView(mView);
                dialog = customDialog.setTitle("Custom Layout").setCancelable(false).create();
                dialog.show();
                break;
            case R.id.btnArray:
                final AlertDialog aDialog;
                final AlertDialog.Builder arrayDialog = new AlertDialog.Builder(this);
                tvTextArray = (TextView) findViewById(R.id.tvTextArray);
                arrayDialog.setTitle("Array Dialog").setMultiChoiceItems(planetsList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            if (!selectedItems.contains(i)) {
                                selectedItems.add(i);
                            } else {
                                selectedItems.remove(i);
                            }
                        }
                    }
                });
                arrayDialog.setCancelable(false);
                arrayDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item = "";
                        for (int j = 0; j < selectedItems.size(); j++) {
                            item = item + planetsList[selectedItems.get(j)];
                            if (j != selectedItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        tvTextArray.setText(item);
                    }
                });
                arrayDialog.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                arrayDialog.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < checkedItems.length; j++) {
                            checkedItems[j] = false;
                            selectedItems.clear();
                            tvTextArray.setText("");
                        }
                    }
                });
                aDialog = arrayDialog.create();
                aDialog.show();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(String string) {
        action = string;
        TimerFragment timerFragment = TimerFragment.newInstance(action,"");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.timerFragment,timerFragment,"TAG")
                .commit();
        Log.d(TAG, "onFragmentInteraction: to Second Activity " + string);
    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tvTimer = (TextView) findViewById(R.id.tvTimer);
                tvTimer.setText(time);

            }
        });
    }
}
