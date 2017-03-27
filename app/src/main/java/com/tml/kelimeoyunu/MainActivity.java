package com.tml.kelimeoyunu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Button btnPause, btnResume;
    public TextView timeView;
    private int currentTime = 240;
    boolean isRunning = false;
    private android.os.Handler timerHandler= new android.os.Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            if(currentTime != 0){
                timeView.setText( "Kalan süre: " +  currentTime + "sn");
                currentTime--;
            }else {
                timeView.setText("Süre doldu!");
            }

            timerHandler.postDelayed(this, 1000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPause = (Button) findViewById(R.id.btn_pause);
        btnResume = (Button) findViewById(R.id.btn_resume);
        timeView = (TextView) findViewById(R.id.time_view);

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunning){
                    timerRunnable.run();
                    isRunning = true;
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.removeCallbacks(timerRunnable);
                isRunning = false;
            }
        });

        timerRunnable.run();


    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

}
