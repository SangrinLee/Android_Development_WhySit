package com.example.appwhysitservice2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    SeekBar mSeekBar;
    TextView mVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q1);

        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mVolume = (TextView) findViewById(R.id.volume);

        RadioGroup ColGroup = (RadioGroup) findViewById(R.id.radiogroup);
        ColGroup.setOnCheckedChangeListener(mRadioCheck);

        CheckBox chkWhite = (CheckBox) findViewById(R.id.check1);
        chkWhite.setOnCheckedChangeListener(mCheckChange);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mVolume.setText("Now Volume : " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    RadioGroup.OnCheckedChangeListener mRadioCheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() == R.id.radiogroup) {
                if (checkedId == R.id.radio1)
                    mVolume.setText("RADIO CHANGED");
            }
        }
    };

    CompoundButton.OnCheckedChangeListener mCheckChange =
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.getId() == R.id.check1)
                        mVolume.setText("CHECKED!");
                }
            };




    public void Context1(View v)
    {
        Intent intent = new Intent(this, Q2.class);
//        Intent intent = new Intent(".step1");
        intent.putExtra("left", 10);
        intent.putExtra("right", 4);
        startActivity(intent);
    }

}
