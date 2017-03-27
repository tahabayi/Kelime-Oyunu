package com.tml.kelimeoyunu;

import android.widget.TextView;

/**
 * Created by Melih on 27.3.2017.
 */

public class TimeCounter {

    private int upperLimit;
    private TextView tView;
    private boolean isRunning = false;
    private boolean show ;
    private android.os.Handler timerHandler= new android.os.Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            if(upperLimit != 0 && show){
                tView.setText( "Kalan süre: " +  upperLimit + " sn");
                upperLimit--;
            }else if(upperLimit != 0){
                upperLimit--;
            }else{
                    tView.setText("Süre doldu!");
            }


            timerHandler.postDelayed(this, 1000);
        }
    };

    public TimeCounter(int upperLimit, TextView tView, boolean show){
        this.upperLimit = upperLimit;
        this.tView = tView;
        this.show = show;
    }



    public void run(){
        if(!isRunning){
            timerRunnable.run();
            isRunning = true;
        }
    }

    public void stop(){
        if(isRunning){
            timerHandler.removeCallbacks(timerRunnable);
            isRunning = false;
        }
    }


}
