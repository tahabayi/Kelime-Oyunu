package com.tml.kelimeoyunu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public int[] quesnums = new int[14];
    public int[] queslimits = {186,186,186,185,189,185,179};
    public static Question[] questions= new Question[14];
    public int counter = 0;
    Button btn_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_play.setClickable(false);
                getQuestions();

            }
        });
    }

    public void getQuestions(){
        Random r = new Random();
        for(int i = 0;i<14;i++){
            final int count = i;
            quesnums[i] = r.nextInt(queslimits[i/2]);
            if(i%2==1)
                while(quesnums[i] == quesnums[i-1])
                    quesnums[i] = r.nextInt(queslimits[i/2]);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child(String.valueOf((i/2)+4)).child(String.valueOf(quesnums[i]));
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    counter +=1;
                    questions[count] = dataSnapshot.getValue(Question.class);
                    if(counter == 14){
                        Intent i = new Intent(getApplicationContext(), PlaymainActivity.class);
                        counter = 0;
                        btn_play.setClickable(true);
                        startActivity(i);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

}
