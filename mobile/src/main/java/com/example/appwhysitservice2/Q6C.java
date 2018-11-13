package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Q6C extends AppCompatActivity {
    TextView q6c_text1;
    TextView q6c_text2;
    Button q6c_btn;

    String q1, q2, q3, q4, q5, q6a;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q6c);

        q6c_text1 = (TextView) findViewById(R.id.q6c_text1);
        q6c_text2 = (TextView) findViewById(R.id.q6c_text2);
        q6c_btn = (Button) findViewById(R.id.q6c_btn);

        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.q6c_radiogroup);
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
            if (group.getId() == R.id.q6c_radiogroup) {
                q6c_btn.setVisibility(View.VISIBLE);
                if (checkedId == R.id.q6c_radio1)
                    q6c_text2.setText("You selected MY HOUSE");
                else if (checkedId == R.id.q6c_radio2)
                    q6c_text2.setText("You selected OTHER'S HOUSE");
                else if (checkedId == R.id.q6c_radio3)
                    q6c_text2.setText("You selected RESTAURANT");
                else if (checkedId == R.id.q6c_radio4)
                    q6c_text2.setText("You selected STORE");
                else if (checkedId == R.id.q6c_radio5)
                    q6c_text2.setText("You selected WORK");
                else if (checkedId == R.id.q6c_radio6)
                    q6c_text2.setText("You selected OTHER");
            }
        }
    };

    public void q6c_btn(View v)
    {
        Intent intent = new Intent(this, Q7.class);
        intent.putExtra("q1", q1);
        intent.putExtra("q2", q2);
        intent.putExtra("q3", q3);
        intent.putExtra("q4", q4);
        intent.putExtra("q5", q5);
        intent.putExtra("q6a", q6a);

        if (q6c_text2.getText() == "You selected MY HOUSE")
            intent.putExtra("q6c", "MY HOUSE");
        else if (q6c_text2.getText() == "You selected OTHER'S HOUSE")
            intent.putExtra("q6c", "OTHER'S HOUSE");
        else if (q6c_text2.getText() == "You selected RESTAURANT")
            intent.putExtra("q6c", "RESTAURANT");
        else if (q6c_text2.getText() == "You selected STORE")
            intent.putExtra("q6c", "STORE");
        else if (q6c_text2.getText() == "You selected WORK")
            intent.putExtra("q6c", "WORK");
        else if (q6c_text2.getText() == "You selected OTHER")
            intent.putExtra("q6c", "OTHER");

        startActivity(intent);
        finish();
    }
}