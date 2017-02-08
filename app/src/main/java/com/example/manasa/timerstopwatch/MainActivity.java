package com.example.manasa.timerstopwatch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timer;
    TextView stopWatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=(TextView)findViewById(R.id.timerText);
        stopWatch=(TextView)findViewById(R.id.stopWatchText);

        timer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Fragment fragments=getFragmentManager().findFragmentById(R.id.fragment_replace);
                if (fragments instanceof TimerFragment ){
                    return;
                }
                Fragment timerFragmet=new TimerFragment();
                replaceFragment(timerFragmet);
            }
        });

        stopWatch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment stopWatchFragment= new StopWatchFragment();
                replaceFragment(stopWatchFragment);
            }
        });
    }

    public  void replaceFragment(Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fragment_replace, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}
