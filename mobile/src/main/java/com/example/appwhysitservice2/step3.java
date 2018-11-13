package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class step3 extends AppCompatActivity {
    TextView step3_text1;
    TextView step3_text2;

    int q1 = 0;
    int q2 = 0;
    int q3 = 0;
    int q4 = 0;
    int q5 = 0;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step3);

        step3_text1 = (TextView) findViewById(R.id.step3_text1);
        step3_text2 = (TextView) findViewById(R.id.step3_text2);
        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.step3_radiogroup);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);


        Intent intent = getIntent();
        q1 = intent.getIntExtra("q1", 0);
        q2 = intent.getIntExtra("q2", 0);
        q3 = intent.getIntExtra("q3", 0);
        q4 = intent.getIntExtra("q4", 0);
        q5 = intent.getIntExtra("q5", 0);
//        step3_text2.setText("You selected " + Integer.toString(q1) + "," + Integer.toString(q2) + "," + Integer.toString(q2b) + "," + Integer.toString(q3));
    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() == R.id.step3_radiogroup) {
                if (checkedId == R.id.step3_radio1)
                    step3_text2.setText("You selected OUTSIDE.");
                else if (checkedId == R.id.step3_radio2)
                    step3_text2.setText("You selected HOUSE.");
                else if (checkedId == R.id.step3_radio3)
                    step3_text2.setText("You selected STORE.");
                else if (checkedId == R.id.step3_radio4)
                    step3_text2.setText("You selected TRANSPORT.");
            }
        }
    };

    public void step3_Context1(View v)
    {
        Intent intent = new Intent(this, step4.class);
        intent.putExtra("q1", q1);
        intent.putExtra("q2", q2);
        intent.putExtra("q3", q3);
        intent.putExtra("q4", q4);
        intent.putExtra("q5", q5);
        if (step3_text2.getText() == "You selected OUTSIDE.")
            intent.putExtra("q6", 1);
        else if (step3_text2.getText() == "You selected HOUSE.")
            intent.putExtra("q6", 2);
        else if (step3_text2.getText() == "You selected STORE.")
            intent.putExtra("q6", 3);
        else if (step3_text2.getText() == "You selected TRANSPORT.")
            intent.putExtra("q6", 4);

        startActivity(intent);
    }
}
