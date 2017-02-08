package com.example.manasa.timerstopwatch;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class StopWatchFragment extends Fragment {
    long startTime=0L;
    Handler customHandler= new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private TextView timerValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_stop_watch,container,false);
        final Button start =(Button)view.findViewById(R.id.start);
        Button reset=(Button)view.findViewById(R.id.reset);
        timerValue=(TextView)view.findViewById(R.id.stopWatch_txtView_id);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startTime= SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread,0);
                //start.setClickable(false);
                start.setEnabled(false);

            }
        });
        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timeSwapBuff = 0L;
                timerValue.setText("00 min:00 sec");
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
        return view;
    }

    private Runnable updateTimerThread = new Runnable() {


        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + " min:"
                    + String.format("%02d", secs) + " sec"
                    );//+ String.format("%03d", milliseconds)
            customHandler.postDelayed(this, 0);
        }

    };

}
