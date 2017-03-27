package com.tml.kelimeoyunu;

/**
 * Created by taha on 27/03/17.
 */

public class Question {

    public int no;
    public String answer;
    public String question;

    public Question(){

    }

    public Question(int no,String answer,String question){
        this.no = no;
        this.answer = answer;
        this.question = question;
    }

    public String toString(){
        return no+"-----"+question+"-----"+answer;
    }
}
