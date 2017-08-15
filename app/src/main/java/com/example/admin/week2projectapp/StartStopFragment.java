package com.example.admin.week2projectapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartStopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "StartStop";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btnStar, btnStop;
    private Chronometer chronometer;
    private Thread mThreadChrono;
    long since = 0L, mills = 0L, timeSwapBuff = 0L, updateTime = 0L, timeInMiliseconds = 0L;
    Handler customHandler = new Handler();
    private long mStartTime;
    private long mStopTime;
    boolean isRunning;
    public static final long MILLIS_TO_MINUTS = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;
    Context context;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                since = System.currentTimeMillis() - mStartTime;
                int seconds = (int) since / (1000) % 60;
                int min = (int) ((since / (MILLIS_TO_MINUTS)) % 60);
                int hours = (int) ((since / (MILLIS_TO_HOURS)) % 24);
                int mills = (int) since % 1000;

                updateTimerText(String.format(
                        "%02d:%02d:%02d:%02d", hours, min, seconds, mills));
                //customHandler.postDelayed(this,0);
            }
        }
        public void start() {
            mStartTime = System.currentTimeMillis();
            isRunning = true;
        }

        public void stop() {
            isRunning = false;
        }

    };
    TextView tvTimer;

    public void updateTimerText(final String time) {

        //tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvTimer.setText(time);

    }

    private OnFragmentInteractionListener mListener;

    public StartStopFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StartStopFragment newInstance(String param1, String param2) {
        StartStopFragment fragment = new StartStopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mv = inflater.inflate(R.layout.fragment_timer, container, false);
        tvTimer = (TextView) mv.findViewById(R.id.tvTimer);
        return inflater.inflate(R.layout.fragment_start_stop, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStar = (Button) view.findViewById(R.id.btnStar);
        btnStop = (Button) view.findViewById(R.id.btnStop);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction("start");
//                chronometer = new Chronometer(getContext());
//                if (chronometer != null) {
//                    chronometer = new Chronometer(getContext());
//                    mThreadChrono = new Thread(chronometer);
//                    mThreadChrono.start();
//                    chronometer.start();
//                }
                since = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                //.postDelayed(updateTimerThread,0);

            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction("stop");
                if (chronometer != null) {
                    chronometer.stop();
                    mThreadChrono.interrupt();
                    mThreadChrono = null;
                    chronometer = null;
                }
                Toast.makeText(getActivity(), " Timer stopped!!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onFragmentInteraction(string);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String string);
    }
}
