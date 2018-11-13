package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Q1 extends AppCompatActivity {
    TextView q1_text1;
    TextView q1_text2;
    Button q1_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q1);

        q1_text1 = (TextView) findViewById(R.id.q1_text1);
        q1_text2 = (TextView) findViewById(R.id.q1_text2);
        q1_btn = (Button) findViewById(R.id.q1_btn);

        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.q1_radiogroup);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);
    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() == R.id.q1_radiogroup) {
                q1_btn.setVisibility(View.VISIBLE);
                if (checkedId == R.id.q1_radio1)
                    q1_text2.setText("You selected LYING");
                else if (checkedId == R.id.q1_radio2)
                    q1_text2.setText("You selected SITTING");
                else if (checkedId == R.id.q1_radio3)
                    q1_text2.setText("You selected STANDING");
                else if (checkedId == R.id.q1_radio4)
                    q1_text2.setText("You selected WALKING");
            }
        }
    };

    public void q1_btn(View v)
    {
        if (q1_text2.getText() == "You selected LYING") {
            Intent intent = new Intent(this, Q2.class);
            intent.putExtra("q1", "LYING");
            startActivity(intent);
        }
        else if (q1_text2.getText() == "You selected SITTING") {
            Intent intent = new Intent(this, Q2.class);
            intent.putExtra("q1", "SITTING");
            startActivity(intent);
        }
        if (q1_text2.getText() == "You selected STANDING") {
            Intent intent = new Intent(this, Q5.class);
            intent.putExtra("q1", "STANDING");
            startActivity(intent);
        }
        else if (q1_text2.getText() == "You selected WALKING") {
            Intent intent = new Intent(this, Q5.class);
            intent.putExtra("q1", "WALKING");
            startActivity(intent);
        }
        finish();
    }
}
