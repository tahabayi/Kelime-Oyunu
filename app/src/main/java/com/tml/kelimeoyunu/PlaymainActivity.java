package com.tml.kelimeoyunu;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by taha on 27/03/17.
 */

public class PlaymainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmain);

        int letternum = getIntent().getIntExtra("letternum",4);


    }
}
