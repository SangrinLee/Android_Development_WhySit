package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Q3 extends AppCompatActivity {
    TextView q3_text1;
    TextView q3_text2;
    TextView q3_text3;
    TextView q3_text4;
    TextView q3_text5;
    Button q3_btn;

    String q1, q2;
    String a = "0";
    String b = "0";
    String c = "0";
    String d = "0";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q3);

        q3_text1 = (TextView) findViewById(R.id.q3_text1);
        q3_text2 = (TextView) findViewById(R.id.q3_text2);
        q3_text3 = (TextView) findViewById(R.id.q3_text3);
        q3_text4 = (TextView) findViewById(R.id.q3_text4);
        q3_text5 = (TextView) findViewById(R.id.q3_text5);
        q3_btn = (Button) findViewById(R.id.q3_btn);

        CheckBox q3_check1 = (CheckBox) findViewById(R.id.q3_check1);
        q3_check1.setOnCheckedChangeListener(mCheckChange);

        CheckBox q3_check2 = (CheckBox) findViewById(R.id.q3_check2);
        q3_check2.setOnCheckedChangeListener(mCheckChange);

        CheckBox q3_check3 = (CheckBox) findViewById(R.id.q3_check3);
        q3_check3.setOnCheckedChangeListener(mCheckChange);

        CheckBox q3_check4 = (CheckBox) findViewById(R.id.q3_check4);
        q3_check4.setOnCheckedChangeListener(mCheckChange);

        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");
//        q3_text2.setText("You selected " + Integer.toString(q1) + "," + Integer.toString(q2));
    }

    CompoundButton.OnCheckedChangeListener mCheckChange =
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    q3_btn.setVisibility(View.VISIBLE);
                    if (buttonView.getId() == R.id.q3_check1 && isChecked) {
                        q3_text2.setVisibility(View.VISIBLE);
                        a = "1";
                    }
                    else if (buttonView.getId() == R.id.q3_check1 && !isChecked) {
                        q3_text2.setVisibility(View.GONE);
                        a = "0";
                    }
                    else if (buttonView.getId() == R.id.q3_check2 && isChecked) {
                        q3_text3.setVisibility(View.VISIBLE);
                        b = "1";
                    }
                    else if (buttonView.getId() == R.id.q3_check2 && !isChecked) {
                        q3_text3.setVisibility(View.GONE);
                        b = "0";
                    }
                    else if (buttonView.getId() == R.id.q3_check3 && isChecked) {
                        q3_text4.setVisibility(View.VISIBLE);
                        c = "1";
                    }
                    else if (buttonView.getId() == R.id.q3_check3 && !isChecked) {
                        q3_text4.setVisibility(View.GONE);
                        c = "0";
                    }
                    else if (buttonView.getId() == R.id.q3_check4 && isChecked) {
                        q3_text5.setVisibility(View.VISIBLE);
                        d = "1";
                    }
                    else if (buttonView.getId() == R.id.q3_check4 && !isChecked) {
                        q3_text5.setVisibility(View.GONE);
                        d = "0";
                    }
                }
            };

    public void q3_btn(View v)
    {
        Intent intent = new Intent(this, Q4.class);
        intent.putExtra("q1", q1);
        intent.putExtra("q2", q2);
        intent.putExtra("q3", a+b+c+d);
        startActivity(intent);
        finish();
    }
}
