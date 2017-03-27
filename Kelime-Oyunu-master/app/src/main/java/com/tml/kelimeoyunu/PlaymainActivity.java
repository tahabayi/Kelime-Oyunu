package com.tml.kelimeoyunu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by taha on 27/03/17.
 */

public class PlaymainActivity extends Activity{

    TextView questionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        questionTextView = (TextView) findViewById(R.id.questionTextView);

        int letternum = getIntent().getIntExtra("letternum",4);

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
}
