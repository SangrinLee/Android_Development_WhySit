package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Q7 extends AppCompatActivity {
    SeekBar q7_seekbar;

    TextView q7_text1;
    TextView q7_text2;
    Button q7_btn;

    String q1, q2, q3, q4, q5, q6a, q6b, q6c, q6d, q7;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q7);

        q7_seekbar = (SeekBar) findViewById(R.id.q7_seekbar);
        q7_text1 = (TextView) findViewById(R.id.q7_text1);
        q7_text2 = (TextView) findViewById(R.id.q7_text2);
        q7_btn = (Button) findViewById(R.id.q7_btn);

        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");
        q3 = intent.getStringExtra("q3");
        q4 = intent.getStringExtra("q4");
        q5 = intent.getStringExtra("q5");
        q6a = intent.getStringExtra("q6a");
        q6b = intent.getStringExtra("q6b");
        q6c = intent.getStringExtra("q6c");
        q6d = intent.getStringExtra("q6d");

        q7_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    q7_text2.setText("Your level : " + progress);
                    q7_btn.setVisibility(View.GONE);
                }
                else {
                    q7_text2.setText("Your level : " + progress);
                    q7 = Integer.toString(progress);
                    q7_btn.setVisibility(View.VISIBLE);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void q7_btn(View v)
    {
        Intent intent = new Intent(this, Q8.class);
        intent.putExtra("q1", q1);
        intent.putExtra("q2", q2);
        intent.putExtra("q3", q3);
        intent.putExtra("q4", q4);
        intent.putExtra("q5", q5);
        intent.putExtra("q6a", q6a);
        intent.putExtra("q6b", q6b);
        intent.putExtra("q6c", q6c);
        intent.putExtra("q6d", q6d);
        intent.putExtra("q7", q7);

        startActivity(intent);
        finish();
    }
}