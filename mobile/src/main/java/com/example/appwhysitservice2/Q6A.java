package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Q6A extends AppCompatActivity {
    TextView q6a_text1;
    TextView q6a_text2;
    Button q6a_btn;

    String q1, q2, q3, q4, q5;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q6a);

        q6a_text1 = (TextView) findViewById(R.id.q6a_text1);
        q6a_text2 = (TextView) findViewById(R.id.q6a_text2);
        q6a_btn = (Button) findViewById(R.id.q6a_btn);

        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.q6a_radiogroup);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);


        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");
        q3 = intent.getStringExtra("q3");
        q4 = intent.getStringExtra("q4");
        q5 = intent.getStringExtra("q5");
    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() == R.id.q6a_radiogroup) {
                q6a_btn.setVisibility(View.VISIBLE);
                if (checkedId == R.id.q6a_radio1)
                    q6a_text2.setText("You selected OUTDOORS");
                else if (checkedId == R.id.q6a_radio2)
                    q6a_text2.setText("You selected INDOORS");
                else if (checkedId == R.id.q6a_radio3)
                    q6a_text2.setText("You selected TRANSPORT");
                else if (checkedId == R.id.q6a_radio4)
                    q6a_text2.setText("You selected OTHER");
            }
        }
    };

    public void q6a_btn(View v)
    {
        if (q6a_text2.getText() == "You selected OUTDOORS")
        {
            Intent intent = new Intent(this, Q6B.class);
            intent.putExtra("q1", q1);
            intent.putExtra("q2", q2);
            intent.putExtra("q3", q3);
            intent.putExtra("q4", q4);
            intent.putExtra("q5", q5);
            intent.putExtra("q6a", "OUTDOORS");
            startActivity(intent);
        }

        else if (q6a_text2.getText() == "You selected INDOORS")
        {
            Intent intent = new Intent(this, Q6C.class);
            intent.putExtra("q1", q1);
            intent.putExtra("q2", q2);
            intent.putExtra("q3", q3);
            intent.putExtra("q4", q4);
            intent.putExtra("q5", q5);
            intent.putExtra("q6a", "INDOORS");
            startActivity(intent);
        }
        else if (q6a_text2.getText() == "You selected TRANSPORT") {
            Intent intent = new Intent(this, Q6D.class);
            intent.putExtra("q1", q1);
            intent.putExtra("q2", q2);
            intent.putExtra("q3", q3);
            intent.putExtra("q4", q4);
            intent.putExtra("q5", q5);
            intent.putExtra("q6a", "TRANSPORT");
            startActivity(intent);
        }
        else if (q6a_text2.getText() == "You selected OTHER")
        {
            Intent intent = new Intent(this, Q7.class);
            intent.putExtra("q1", q1);
            intent.putExtra("q2", q2);
            intent.putExtra("q3", q3);
            intent.putExtra("q4", q4);
            intent.putExtra("q5", q5);
            intent.putExtra("q6a", "OTHER");
            startActivity(intent);
        }
        finish();
    }
}