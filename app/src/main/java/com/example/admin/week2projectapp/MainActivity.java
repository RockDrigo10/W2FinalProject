package com.example.admin.week2projectapp;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

public class MainActivity extends AppCompatActivity {
    PDFView pdfViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pdfViewer = (PDFView) findViewById(R.id.pdfViewer);
        pdfViewer.fromAsset("alfa.pdf").load();
        final SendSMS sendSms = new SendSMS();
        final FragmentManager fragmentManager = getFragmentManager();
        final MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(fragmentManager, "tagDialog");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms.show(fragmentManager,"sendSms");
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.dortmund);
        builder.setContentTitle("Dortmund Notifications");
        builder.setContentText("Information related to Borussia Dortmund");
        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
        Toast.makeText(this, " Alert.. Notification Sent!!!", Toast.LENGTH_SHORT).show();
    }
}
