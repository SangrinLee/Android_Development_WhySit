package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Q2 extends AppCompatActivity {
    TextView q2_text1;
    TextView q2_text2;
    Button q2_btn;

    String q1;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q2);

        q2_text1 = (TextView) findViewById(R.id.q2_text1);
        q2_text2 = (TextView) findViewById(R.id.q2_text2);
        q2_btn = (Button) findViewById(R.id.q2_btn);

        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.q2_radiogroup);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);

        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() == R.id.q2_radiogroup) {
                q2_btn.setVisibility(View.VISIBLE);
                if (checkedId == R.id.q2_radio1)
                    q2_text2.setText("You selected EATING");
                else if (checkedId == R.id.q2_radio2)
                    q2_text2.setText("You selected TELEVISION");
                else if (checkedId == R.id.q2_radio3)
                    q2_text2.setText("You selected TALKING");
                else if (checkedId == R.id.q2_radio4)
                    q2_text2.setText("You selected COMPUTER");
                else if (checkedId == R.id.q2_radio5)
                    q2_text2.setText("You selected DESK WORK");
                else if (checkedId == R.id.q2_radio6)
                    q2_text2.setText("You selected READING");
                else if (checkedId == R.id.q2_radio7)
                    q2_text2.setText("You selected ON PHONE");
                else if (checkedId == R.id.q2_radio8)
                    q2_text2.setText("You selected OTHER");
            }
        }
    };

    public void q2_btn(View v)
    {
        Intent intent = new Intent(this, Q3.class);
        intent.putExtra("q1", q1);
        if (q2_text2.getText() == "You selected EATING")
            intent.putExtra("q2", "EATING");
        else if (q2_text2.getText() == "You selected TELEVISION")
            intent.putExtra("q2", "TELEVISION");
        else if (q2_text2.getText() == "You selected TALKING")
            intent.putExtra("q2", "TALKING");
        else if (q2_text2.getText() == "You selected COMPUTER")
            intent.putExtra("q2", "COMPUTER");
        else if (q2_text2.getText() == "You selected DESK WORK")
            intent.putExtra("q2", "DESK WORK");
        else if (q2_text2.getText() == "You selected READING")
            intent.putExtra("q2", "READING");
        else if (q2_text2.getText() == "You selected ON PHONE")
            intent.putExtra("q2", "OH PHONE");
        else if (q2_text2.getText() == "You selected OTHER")
            intent.putExtra("q2", "OTHER");

        startActivity(intent);
        finish();
    }
}
