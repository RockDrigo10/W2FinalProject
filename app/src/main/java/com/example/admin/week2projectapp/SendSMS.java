package com.example.admin.week2projectapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Admin on 8/13/2017.
 */

public class SendSMS extends DialogFragment {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final String SENT = "Message Sent!!!!";
    private static final String DELIVERED = "Message Delivered";
    EditText etNumber, etMessage;
    Button btnSMS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sending_sms, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        etNumber = (EditText) view.findViewById(R.id.etNumber);
        btnSMS = (Button) view.findViewById(R.id.btnSMS);
        getDialog().setTitle("Send Message");
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                dismiss();
                etMessage.setText("");
                etNumber.setText("");
            }
        });

        return view;
    }

    private void sendMessage() {
        // Get the default instance of the SmsManager
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(etNumber.getText().toString(), null, etMessage.getText().toString(), null, null);
        Toast.makeText(getActivity(), "Your sms was successfully sent!",
                Toast.LENGTH_LONG).show();

    }
}
