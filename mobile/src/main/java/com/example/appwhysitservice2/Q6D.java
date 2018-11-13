package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Q6D extends AppCompatActivity {
    TextView q6d_text1;
    TextView q6d_text2;
    Button q6d_btn;

    String q1, q2, q3, q4, q5, q6a;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q6d);

        q6d_text1 = (TextView) findViewById(R.id.q6d_text1);
        q6d_text2 = (TextView) findViewById(R.id.q6d_text2);
        q6d_btn = (Button) findViewById(R.id.q6d_btn);

        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.q6d_radiogroup);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);


        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");
        q3 = intent.getStringExtra("q3");
        q4 = intent.getStringExtra("q4");
        q5 = intent.getStringExtra("q5");
        q6a = intent.getStringExtra("q6a");
    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() == R.id.q6d_radiogroup) {
                q6d_btn.setVisibility(View.VISIBLE);
                if (checkedId == R.id.q6d_radio1)
                    q6d_text2.setText("You selected CAR");
                else if (checkedId == R.id.q6d_radio2)
                    q6d_text2.setText("You selected BUS");
                else if (checkedId == R.id.q6d_radio3)
                    q6d_text2.setText("You selected TRAIN");
                else if (checkedId == R.id.q6d_radio4)
                    q6d_text2.setText("You selected PLANE");
                else if (checkedId == R.id.q6d_radio5)
                    q6d_text2.setText("You selected OTHER");
            }
        }
    };

    public void q6d_btn(View v)
    {
        Intent intent = new Intent(this, Q7.class);
        intent.putExtra("q1", q1);
        intent.putExtra("q2", q2);
        intent.putExtra("q3", q3);
        intent.putExtra("q4", q4);
        intent.putExtra("q5", q5);
        intent.putExtra("q6a", q6a);

        if (q6d_text2.getText() == "You selected CAR")
            intent.putExtra("q6d", "CAR");
        else if (q6d_text2.getText() == "You selected BUS")
            intent.putExtra("q6d", "BUS");
        else if (q6d_text2.getText() == "You selected TRAIN")
            intent.putExtra("q6d", "TRAIN");
        else if (q6d_text2.getText() == "You selected PLANE")
            intent.putExtra("q6d", "PLANE");
        else if (q6d_text2.getText() == "You selected OTHER")
            intent.putExtra("q6d", "OTHER");

        startActivity(intent);
        finish();
    }
}