package com.tml.kelimeoyunu;

import android.content.Intent;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PlaymainActivity extends AppCompatActivity {

    public int[] quesnums = new int[14];
    public int[] queslimits = {186,186,186,185,189,185,179};
    public Question[] questions= new Question[14];
    public int counter = 0;
    private List<Integer> harfArr = new ArrayList<Integer>();
    private boolean ans_btn_clicked = false;


    private Button btnPause, btnHarf;
    private TextView timeTextView;
    private TimeCounter gameTime;
    private TimeCounter answerTime;
    private TextView questionTextView;
    private LinearLayout buttonLayout;
    private LinearLayout playLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        int questionNum = getIntent().getExtras().getInt("questionNum");


        btnPause = (Button) findViewById(R.id.cevaplaButton);
        btnHarf = (Button) findViewById(R.id.harfButton);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        playLayout = (LinearLayout) findViewById(R.id.layout_play);

        playLayout.setVisibility(View.INVISIBLE);

        //String questionsString = getIntent().getExtras().getString("questionsString");
        //Question[] questions = new Gson().fromJson(questionsString, Question[].class);

        getQuestions();

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
                        setQuestions();
                        startTimer();
                        /*
                        Intent i = new Intent(getApplicationContext(), PlaymainActivity.class);
                        String questionsString = new Gson().toJson(questions);
                        i.putExtra("questionsString", questionsString);
                        startActivity(i);
                        */
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    public void setQuestions(){
        questionTextView.setText(questions[0].question+" ?");

        // Add letter boxes into layout
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(dpToPx(48), dpToPx(48));
        for(int i=0; i<questions[0].no; i++){
            Button btn =new Button(this);
            btn.setBackground(getResources().getDrawable(R.drawable.hexagon));
            btn.setLayoutParams(lparams);
            btn.setId(i);
            buttonLayout.addView(btn);
        }

        playLayout.setVisibility(View.VISIBLE);
        for(int i = 0;i<questions[0].no;i++){
            harfArr.add(i);
        }

    }

    public void startTimer(){
        // Necessary Timer instances
        gameTime = new TimeCounter(241, timeTextView, true);
        answerTime = new TimeCounter(10, timeTextView, false);
        answerTime.setTimerRunnable( new Runnable() {
            @Override
            public void run() {
                if(answerTime.getUpperLimit()!=0) {
                    answerTime.setUpperLimit(answerTime.getUpperLimit()-1);
                }else{
                    for(int i=0; i<questions[0].no; i++){
                        Button btn = (Button) findViewById(i);
                        btn.setText("x");
                    }
                }

                answerTime.getTimerHandler().postDelayed(this, 1000);
            }
        });
        gameTime.run();

        // TODO This is Harf Lutfen Button
        btnHarf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO cevap zaten cikti
                if(harfArr.size()==0)
                    return;
                else if(ans_btn_clicked)
                    return;
                Random r = new Random();
                int rand = r.nextInt(harfArr.size());
                int btn_no = harfArr.get(rand);
                harfArr.remove(rand);
                Button b = (Button) findViewById(btn_no);
                b.setText(String.valueOf(questions[0].answer.charAt(btn_no)));
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ans_btn_clicked) {
                    ans_btn_clicked = true;
                    gameTime.stop();
                    answerTime.run();
                }
            }
        });

        // Start game time
        gameTime.run();

    }

}
