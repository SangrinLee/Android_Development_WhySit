package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class step4 extends AppCompatActivity {
    SeekBar seekbar;

    TextView step4_text1;
    TextView step4_text2;

    int q1 = 0;
    int q2 = 0;
    int q3 = 0;
    int q4 = 0;
    int q5 = 0;
    int q6 = 0;
    int q7 = 0;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step4);

        seekbar = (SeekBar) findViewById(R.id.seekbar);
        step4_text1 = (TextView) findViewById(R.id.step4_text1);
        step4_text2 = (TextView) findViewById(R.id.step4_text2);

        Intent intent = getIntent();
        q1 = intent.getIntExtra("q1", 0);
        q2 = intent.getIntExtra("q2", 0);
        q3 = intent.getIntExtra("q3", 0);
        q4 = intent.getIntExtra("q4", 0);
        q5 = intent.getIntExtra("q5", 0);
        q6 = intent.getIntExtra("q6", 0);
//        step4_text2.setText("You selected " + Integer.toString(q1) + "," + Integer.toString(q2) + "," + Integer.toString(q2b) + "," + Integer.toString(q3) + "," + Integer.toString(q4));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                step4_text2.setText("Your Stress level : " + progress);
                q7 = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void step4_Context1(View v)
    {
        step4_text2.setText("You've selected " + Integer.toString(q1) + "," + Integer.toString(q2) + "," + Integer.toString(q3) + "," + Integer.toString(q4) + "," + Integer.toString(q5) + "," + Integer.toString(q6) + "," + Integer.toString(q7));
    }
}
