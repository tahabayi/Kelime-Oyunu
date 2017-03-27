package com.tml.kelimeoyunu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static int counter = 0;

    public int[] quesnums = new int[14];
    public int[] queslimits = {186,186,186,185,189,185,179};
    public Question[] questions= new Question[14];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    if(counter==14)
                        for(int i=0;i<14;i++)
                            Log.d("asd",questions[i].toString());
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

    }
}
