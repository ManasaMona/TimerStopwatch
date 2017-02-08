package com.example.manasa.timerstopwatch;

import android.os.Bundle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import static com.example.manasa.timerstopwatch.R.mipmap.ic_launcher;

public class TimerFragment extends Fragment {
    EditText input_time;
    ProgressBar prgBar;
    Button startButton;
    Button resetButton;
    static MyCountDownTimer myCountDownTimer;
    boolean timecompleted=false;
    Long seconds;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_timer,container,false);
        startButton=(Button) view.findViewById(R.id.start);
        resetButton=(Button)view.findViewById(R.id.reset);
        input_time=(EditText) view.findViewById(R.id.timer_editText_id);
        prgBar=(ProgressBar)view.findViewById(R.id.ProgressBar);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String inputValue=input_time.getText().toString();
                if (inputValue.isEmpty() || inputValue.length() == 0 || inputValue.equals("") || input_time == null){
                    Toast.makeText(getActivity(),"Enter the time", Toast.LENGTH_LONG).show();
                }
                else {
                seconds=Long.parseLong(inputValue);
                prgBar.setMax(Integer.parseInt(inputValue));
                myCountDownTimer = new MyCountDownTimer(seconds*1000,1000);
                myCountDownTimer.start();
                startButton.setEnabled(false);
                }
            }
        } );

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myCountDownTimer.cancel();
                input_time.setText("");
                startButton.setEnabled(true);
            }
        });

        return view;
    }

    public class MyCountDownTimer extends CountDownTimer {
         boolean timeFinshed=false;
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished/1000);
            input_time.setText(progress+"");
            prgBar.setProgress(prgBar.getMax()-progress);
        }

        @Override
        public void onFinish() {
            timeFinshed=true;
            input_time.setText("Time Completed");
            prgBar.setProgress(prgBar.getMax());
            startNotification();
        }
        protected void startNotification() {
            NotificationCompat.Builder  notification = new NotificationCompat.Builder(getActivity());
            notification.setContentTitle("Timer");
            notification.setContentText("Alert");
            notification.setTicker("Time is up!");
            notification.setSmallIcon(ic_launcher);
            Intent resultIntent = new Intent(getActivity(), Result.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(contentIntent);
            NotificationManager manager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, notification.build());



        }


    }
}