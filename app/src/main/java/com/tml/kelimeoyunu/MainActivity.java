package com.tml.kelimeoyunu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Button btnPause, btnResume;
    public TextView timeView;
    private TimeCounter gameTime;
    private TimeCounter answerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        btnPause = (Button) findViewById(R.id.cevaplaButton);
        btnResume = (Button) findViewById(R.id.harfButton);
        timeView = (TextView) findViewById(R.id.time_view);

        gameTime = new TimeCounter(241, timeView, true);
        answerTime = new TimeCounter(100, timeView, false);

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameTime.run();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameTime.stop();
            }
        });

        gameTime.run();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

