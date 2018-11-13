package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Q5 extends AppCompatActivity {
    TextView q5_text1;
    TextView q5_text2;
    TextView q5_text3;
    TextView q5_text4;
    TextView q5_text5;
    TextView q5_text6;
    TextView q5_text7;
    TextView q5_text8;
    Button q5_btn;

    String q1, q2, q3, q4;
    String a = "0";
    String b = "0";
    String c = "0";
    String d = "0";
    String e = "0";
    String f = "0";
    String g = "0";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q5);

        q5_text1 = (TextView) findViewById(R.id.q5_text1);
        q5_text2 = (TextView) findViewById(R.id.q5_text2);
        q5_text3 = (TextView) findViewById(R.id.q5_text3);
        q5_text4 = (TextView) findViewById(R.id.q5_text4);
        q5_text5 = (TextView) findViewById(R.id.q5_text5);
        q5_text6 = (TextView) findViewById(R.id.q5_text6);
        q5_text7 = (TextView) findViewById(R.id.q5_text7);
        q5_text8 = (TextView) findViewById(R.id.q5_text8);
        q5_btn = (Button) findViewById(R.id.q5_btn);

        CheckBox q5_check1 = (CheckBox) findViewById(R.id.q5_check1);
        q5_check1.setOnCheckedChangeListener(mCheckChange);

        CheckBox q5_check2 = (CheckBox) findViewById(R.id.q5_check2);
        q5_check2.setOnCheckedChangeListener(mCheckChange);

        CheckBox q5_check3 = (CheckBox) findViewById(R.id.q5_check3);
        q5_check3.setOnCheckedChangeListener(mCheckChange);

        CheckBox q5_check4 = (CheckBox) findViewById(R.id.q5_check4);
        q5_check4.setOnCheckedChangeListener(mCheckChange);

        CheckBox q5_check5 = (CheckBox) findViewById(R.id.q5_check5);
        q5_check5.setOnCheckedChangeListener(mCheckChange);

        CheckBox q5_check6 = (CheckBox) findViewById(R.id.q5_check6);
        q5_check6.setOnCheckedChangeListener(mCheckChange);

        CheckBox q5_check7 = (CheckBox) findViewById(R.id.q5_check7);
        q5_check7.setOnCheckedChangeListener(mCheckChange);

        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");
        q3 = intent.getStringExtra("q3");
        q4 = intent.getStringExtra("q4");
    }

    CompoundButton.OnCheckedChangeListener mCheckChange =
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    q5_btn.setVisibility(View.VISIBLE);
                    if (buttonView.getId() == R.id.q5_check1 && isChecked) {
                        q5_text2.setVisibility(View.VISIBLE);
                        a = "1";
                    }
                    else if (buttonView.getId() == R.id.q5_check1 && !isChecked) {
                        q5_text2.setVisibility(View.GONE);
                        a = "0";
                    }
                    else if (buttonView.getId() == R.id.q5_check2 && isChecked) {
                        q5_text3.setVisibility(View.VISIBLE);
                        b = "1";
                    }
                    else if (buttonView.getId() == R.id.q5_check2 && !isChecked) {
                        q5_text3.setVisibility(View.GONE);
                        b = "0";
                    }
                    else if (buttonView.getId() == R.id.q5_check3 && isChecked) {
                        q5_text4.setVisibility(View.VISIBLE);
                        c = "1";
                    }
                    else if (buttonView.getId() == R.id.q5_check3 && !isChecked) {
                        q5_text4.setVisibility(View.GONE);
                        c = "0";
                    }
                    else if (buttonView.getId() == R.id.q5_check4 && isChecked) {
                        q5_text5.setVisibility(View.VISIBLE);
                        d = "1";
                    }
                    else if (buttonView.getId() == R.id.q5_check4 && !isChecked) {
                        q5_text5.setVisibility(View.GONE);
                        d = "0";
                    }
                    else if (buttonView.getId() == R.id.q5_check5 && isChecked) {
                        q5_text6.setVisibility(View.VISIBLE);
                        e = "1";
                    }
                    else if (buttonView.getId() == R.id.q5_check5 && !isChecked) {
                        q5_text6.setVisibility(View.GONE);
                        e = "0";
                    }
                    else if (buttonView.getId() == R.id.q5_check6 && isChecked) {
                        q5_text7.setVisibility(View.VISIBLE);
                        f = "1";
                    }
                    else if (buttonView.getId() == R.id.q5_check6 && !isChecked) {
                        q5_text7.setVisibility(View.GONE);
                        f = "0";
                    }
                    else if (buttonView.getId() == R.id.q5_check7 && isChecked) {
                        q5_text8.setVisibility(View.VISIBLE);
                        g = "1";
                    }
                    else if (buttonView.getId() == R.id.q5_check7 && !isChecked) {
                        q5_text8.setVisibility(View.GONE);
                        g = "0";
                    }
                }
            };

    public void q5_btn(View v)
    {
        Intent intent = new Intent(this, Q6A.class);
        intent.putExtra("q1", q1);
        intent.putExtra("q2", q2);
        intent.putExtra("q3", q3);
        intent.putExtra("q4", q4);
        intent.putExtra("q5", a+b+c+d+e+f+g);
        startActivity(intent);
        finish();
    }
}
