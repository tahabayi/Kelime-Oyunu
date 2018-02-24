package com.tml.kelimeoyunu;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PlaymainActivity extends AppCompatActivity {

    private List<Integer> harfArr;
    private static Question[] questions = MainActivity.questions;
    private TimeCounter gameTimer;
    private int questionNum = 0;
    private int totalPoint = 0;
    private int questPoint;
    private boolean[] harfFilled;
    private boolean[] harfPicked;


    private Button btnCvp, btnHarf;
    private TextView timeTextView;
    private TextView questionTextView;
    private LinearLayout buttonLayout;
    private TextView questPointTextView;
    private TextView totalPointTextView;
    private ConstraintLayout harfLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        btnCvp = (Button) findViewById(R.id.cevaplaButton);
        btnHarf = (Button) findViewById(R.id.harfButton);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        questPointTextView = (TextView) findViewById(R.id.questPointTextView);
        totalPointTextView = (TextView) findViewById(R.id.totalPointTextView);
        harfLayout = (ConstraintLayout) findViewById(R.id.harfLayout);

        gameTimer = new TimeCounter(240, timeTextView, true);
        gameTimer.run();
        setQuestions(questionNum);
        setOnClicks();


        /*if(MainActivity.getG().getGameTime()!=null) {
            timeCount = MainActivity.getG().getGameTime().getUpperLimit();
            MainActivity.getG().getGameTime().stop();
        }
        else {
            timeCount = 240;
        }
        MainActivity.getG().setGameTime(new TimeCounter(timeCount, timeTextView, true));
        MainActivity.getG().getGameTime().run();

        setQuestions();
        startTimer();*/
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

    public void setQuestions(int questionNum){
        btnHarf.setClickable(true);
        btnCvp.setClickable(true);
        buttonLayout.removeAllViewsInLayout();
        harfArr = new ArrayList<Integer>();
        harfFilled = new boolean[questions[questionNum].no];


        questionTextView.setText(questions[questionNum].question+" ?");
        questPoint = questions[questionNum].no * 100;
        questPointTextView.setText(String.valueOf(questPoint));
        totalPointTextView.setText(String.valueOf(totalPoint));

        // Add letter boxes into layout
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(dpToPx(36), dpToPx(40));
        for(int i=0; i<questions[questionNum].no; i++){
            Button btn =new Button(this);
            btn.setIncludeFontPadding(false);
            btn.setBackground(getResources().getDrawable(R.drawable.hexagon));
            btn.setLayoutParams(lparams);
            btn.setId(i);
            buttonLayout.addView(btn);
        }

        for(int i = 0;i<questions[questionNum].no;i++){
            harfArr.add(i);
        }
    }

    public void setOnClicks(){

        // TODO This is Harf Lutfen Button
        btnHarf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO cevap zaten cikti
                if(harfArr.size()==0){
                    questionNum += 1;
                    setQuestions(questionNum);
                    return;
                }
                Random r = new Random();
                int rand = r.nextInt(harfArr.size());
                int btn_no = harfArr.get(rand);
                harfArr.remove(rand);
                Button b = (Button) findViewById(btn_no);
                b.setText(String.valueOf(questions[questionNum].answer.charAt(btn_no)));
                questPoint -= 100;
                questPointTextView.setText(String.valueOf(questPoint));
                harfFilled[btn_no] = true;
            }
        });

        btnCvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCvp.setClickable(false);
                btnHarf.setClickable(false);
                harfLayout.setVisibility(View.VISIBLE);


                final TimeCounter answerTime = new TimeCounter(10, timeTextView, false);
                answerTime.setTimerRunnable( new Runnable() {
                    @Override
                    public void run() {
                        if(answerTime.getUpperLimit()!=0) {
                            answerTime.setUpperLimit(answerTime.getUpperLimit()-1);
                        }else{
                            for(int i=0; i<questions[questionNum].no; i++){
                                Button btn = (Button) findViewById(i);
                                btn.setText("x");
                            }
                            return;
                        }

                        answerTime.getTimerHandler().postDelayed(this, 1000);
                    }
                });

                answerTime.run();
                gameTimer.stop();

                findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0;i<questions[questionNum].no;i++){
                            if(!harfFilled[i]){
                                harfFilled[i] = true;
                                Button b = (Button) findViewById(i);
                                b.setText(String.valueOf(questions[questionNum].answer.charAt(i)));
                                break;
                            }
                        }
                    }
                });

                findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0;i<questions[questionNum].no;i++){
                            if(!harfFilled[i]){
                                harfFilled[i] = true;
                                Button b = (Button) findViewById(i);
                                b.setText("A");
                                break;
                            }
                        }
                    }
                });

                findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = "";
                        for(int j = 0;j<questions[questionNum].no;j++){
                            Button b1 = (Button) findViewById(j);
                            s += b1.getText();
                        }
                        if(s.equals(questions[questionNum].answer)){
                            questionNum += 1;
                            setQuestions(questionNum);
                        }
                    }
                });
            }
        });

    }

}
