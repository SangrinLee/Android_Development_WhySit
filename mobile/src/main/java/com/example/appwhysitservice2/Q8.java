package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Q8 extends AppCompatActivity {
    SeekBar q8_seekbar;

    TextView q8_text1;
    TextView q8_text2;
    Button q8_btn;

    String q1, q2, q3, q4, q5, q6a, q6b, q6c, q6d, q7, q8;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q8);

        q8_seekbar = (SeekBar) findViewById(R.id.q8_seekbar);
        q8_text1 = (TextView) findViewById(R.id.q8_text1);
        q8_text2 = (TextView) findViewById(R.id.q8_text2);
        q8_btn = (Button) findViewById((R.id.q8_btn));

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
        q7 = intent.getStringExtra("q7");

        q8_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    q8_btn.setVisibility(View.GONE);
                    q8_btn.setVisibility(View.GONE);
                }
                else {
                    q8_text2.setText("Your level : " + progress);
                    q8 = Integer.toString(progress);
                    q8_btn.setVisibility(View.VISIBLE);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void q8_btn(View v)
    {
        Intent intent = new Intent(this, Q9.class);
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
        intent.putExtra("q8", q8);

        startActivity(intent);
        finish();
    }
}