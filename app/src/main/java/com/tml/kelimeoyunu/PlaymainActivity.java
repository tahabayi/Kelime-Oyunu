package com.tml.kelimeoyunu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PlaymainActivity extends AppCompatActivity {

    public Button btnPause, btnResume;
    public TextView timeView;
    private TimeCounter gameTime;
    private TimeCounter answerTime;
    private TextView questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        btnPause = (Button) findViewById(R.id.cevaplaButton);
        btnResume = (Button) findViewById(R.id.harfButton);
        timeView = (TextView) findViewById(R.id.time_view);
        questionTextView = (TextView) findViewById(R.id.questionTextView);

        int letternum = getIntent().getIntExtra("letternum",4);

        // Necessary Timer instances
        gameTime = new TimeCounter(241, timeView, true);
        answerTime = new TimeCounter(100, timeView, false);


        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameTime.run();
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
}
