package com.tml.kelimeoyunu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PlaymainActivity extends AppCompatActivity {

    public Button btnPause, btnResume;
    public TextView timeTextView;
    private TimeCounter gameTime;
    private TimeCounter answerTime;
    private TextView questionTextView;
    private LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        btnPause = (Button) findViewById(R.id.cevaplaButton);
        btnResume = (Button) findViewById(R.id.harfButton);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);

        int letternum = getIntent().getIntExtra("letternum",4);

        // Necessary Timer instances
        gameTime = new TimeCounter(241, timeTextView, true);
        answerTime = new TimeCounter(100, timeTextView, false);


        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameTime.run();
                answerTime.run();
            }
        });

        // TODO This is Harf Lutfen Button
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameTime.stop();
            }
        });

        // Start game time
        gameTime.run();

        // Add letter boxes into layout
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(dpToPx(36), dpToPx(36));
       for(int i=0; i<letternum; i++){
           Button btn =new Button(this);
           btn.setBackground(getResources().getDrawable(R.drawable.hexagon));
           btn.setLayoutParams(lparams);
           btn.setId(i);
           buttonLayout.addView(btn);
       }



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child(String.valueOf(letternum)).child("0");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question q = dataSnapshot.getValue(Question.class);
                questionTextView.setText(q.question+" ?");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
