package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Q4 extends AppCompatActivity {
    TextView q4_text1;
    TextView q4_text2;
    Button q4_btn;

    String q1, q2, q3;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q4);

        q4_text1 = (TextView) findViewById(R.id.q4_text1);
        q4_text2 = (TextView) findViewById(R.id.q4_text2);
        q4_btn = (Button) findViewById(R.id.q4_btn);

        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.q4_radiogroup);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);

        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");
        q3 = intent.getStringExtra("q3");
    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() == R.id.q4_radiogroup) {
                q4_btn.setVisibility(View.VISIBLE);
                if (checkedId == R.id.q4_radio1)
                    q4_text2.setText("You selected LESS THAN 3 MINUTES");
                else if (checkedId == R.id.q4_radio2)
                    q4_text2.setText("You selected 3-5 MINUTES");
                else if (checkedId == R.id.q4_radio3)
                    q4_text2.setText("You selected 6-10 MINUTES");
                else if (checkedId == R.id.q4_radio4)
                    q4_text2.setText("You selected MORE THAN 10 MINUTES");
            }
        }
    };

    public void q4_btn(View v)
    {
        Intent intent = new Intent(this, Q5.class);
        intent.putExtra("q1", q1);
        intent.putExtra("q2", q2);
        intent.putExtra("q3", q3);
        if (q4_text2.getText() == "You selected LESS THAN 3 MINUTES")
            intent.putExtra("q4", "LESS THAN 3 MINUTES");
        else if (q4_text2.getText() == "You selected 3-5 MINUTES")
            intent.putExtra("q4", "3-5 MINUTES");
        else if (q4_text2.getText() == "You selected 6-10 MINUTES")
            intent.putExtra("q4", "6-10 MINUTES");
        else if (q4_text2.getText() == "You selected MORE THAN 10 MINUTES")
            intent.putExtra("q4", "MORE THAN 10 MINUTES");

        startActivity(intent);
        finish();
    }
}
