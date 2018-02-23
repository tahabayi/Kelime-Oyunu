package com.tml.kelimeoyunu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * Created by tahabayi on 23/02/2018.
 */

public class Game {

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public Question[] getQuestions() {
        return questions;
    }

    private Question[] questions = new Question[14];

    public Game() {



    }
}
