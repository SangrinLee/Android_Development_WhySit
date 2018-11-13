package com.example.appwhysitservice2;

import android.app.Service;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    Background background;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;

    PowerManager pm;
    PowerManager.WakeLock wl;

    private int detect = 0;

    private int w_hour = 0;
    private int w_min = 0;
    private int s_hour = 0;
    private int s_min = 0;
    private boolean a = false;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);

        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn1.setText("Hour");
        btn2.setText("Min");
        btn3.setText("Hour");
        btn4.setText("Min");
        btn5.setText("START");
        btn6.setText("TV");

        intent = new Intent(this, NewsService.class);

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
        wl.acquire();

        Check_Permission();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
//        if (isAmbient()) {
//            btn6.setText("(A)STOP");
//        } else {
//            btn6.setText("STOP");
//        }
    }

    public void Context1(View v) {
        if(btn1.getText().toString() == "0")
            btn1.setText("1");
        else if(btn1.getText().toString() == "1")
            btn1.setText("2");
        else if(btn1.getText().toString() == "2")
            btn1.setText("3");
        else if(btn1.getText().toString() == "3")
            btn1.setText("4");
        else if(btn1.getText().toString() == "4")
            btn1.setText("5");
        else if(btn1.getText().toString() == "5")
            btn1.setText("6");
        else if(btn1.getText().toString()== "6")
            btn1.setText("7");
        else if(btn1.getText().toString() == "7")
            btn1.setText("8");
        else if(btn1.getText().toString() == "8")
            btn1.setText("9");
        else if(btn1.getText().toString() == "9")
            btn1.setText("10");
        else if(btn1.getText().toString() == "10")
            btn1.setText("11");
        else if(btn1.getText().toString() == "11")
            btn1.setText("12");
        else if(btn1.getText().toString() == "12")
            btn1.setText("13");
        else if(btn1.getText().toString() == "13")
            btn1.setText("14");
        else if(btn1.getText().toString() == "14")
            btn1.setText("15");
        else if(btn1.getText().toString()== "15")
            btn1.setText("16");
        else if(btn1.getText().toString() == "16")
            btn1.setText("17");
        else if(btn1.getText().toString() == "17")
            btn1.setText("18");
        else if(btn1.getText().toString() == "18")
            btn1.setText("19");
        else if(btn1.getText().toString() == "19")
            btn1.setText("20");
        else if(btn1.getText().toString() == "20")
            btn1.setText("21");
        else if(btn1.getText().toString() == "21")
            btn1.setText("22");
        else if(btn1.getText().toString() == "22")
            btn1.setText("23");
        else
            btn1.setText("0");
        w_hour = Integer.parseInt(btn1.getText().toString());
        a = true;
        if(a && b && c && d)
        {
            if(w_hour < s_hour) {
                btn5.setEnabled(true);
                btn6.setEnabled(true);
            }
            else {
                btn5.setEnabled(false);
                btn6.setEnabled(false);
            }
        }
    }

    public void Context2(View v) {
        if(btn2.getText() == "0")
            btn2.setText("30");
        else
            btn2.setText("0");
        w_min = Integer.parseInt(btn2.getText().toString());
        b = true;
        if(a && b && c && d)
        {
            if(w_hour < s_hour) {
                btn5.setEnabled(true);
                btn6.setEnabled(true);
            }
            else {
                btn5.setEnabled(false);
                btn6.setEnabled(false);
            }
        }
    }

    public void Context3(View v) {
        if(btn3.getText() == "0")
            btn3.setText("1");
        else if(btn3.getText() == "1")
            btn3.setText("2");
        else if(btn3.getText() == "2")
            btn3.setText("3");
        else if(btn3.getText() == "3")
            btn3.setText("4");
        else if(btn3.getText() == "4")
            btn3.setText("5");
        else if(btn3.getText() == "5")
            btn3.setText("6");
        else if(btn3.getText() == "6")
            btn3.setText("7");
        else if(btn3.getText() == "7")
            btn3.setText("8");
        else if(btn3.getText() == "8")
            btn3.setText("9");
        else if(btn3.getText() == "9")
            btn3.setText("10");
        else if(btn3.getText() == "10")
            btn3.setText("11");
        else if(btn3.getText() == "11")
            btn3.setText("12");
        else if(btn3.getText() == "12")
            btn3.setText("13");
        else if(btn3.getText() == "13")
            btn3.setText("14");
        else if(btn3.getText() == "14")
            btn3.setText("15");
        else if(btn3.getText() == "15")
            btn3.setText("16");
        else if(btn3.getText() == "16")
            btn3.setText("17");
        else if(btn3.getText() == "17")
            btn3.setText("18");
        else if(btn3.getText() == "18")
            btn3.setText("19");
        else if(btn3.getText() == "19")
            btn3.setText("20");
        else if(btn3.getText() == "20")
            btn3.setText("21");
        else if(btn3.getText() == "21")
            btn3.setText("22");
        else if(btn3.getText() == "22")
            btn3.setText("23");
        else
            btn3.setText("0");
        s_hour = Integer.parseInt(btn3.getText().toString());
        c = true;
        if(a && b && c && d)
        {
            if(w_hour < s_hour) {
                btn5.setEnabled(true);
                btn6.setEnabled(true);
            }
            else {
                btn5.setEnabled(false);
                btn6.setEnabled(false);
            }
        }
    }

    public void Context4(View v) {
        if(btn4.getText() == "0")
            btn4.setText("30");
        else
            btn4.setText("0");
        s_min = Integer.parseInt(btn4.getText().toString());
        d = true;
        if(a && b && c && d)
        {
            if(w_hour < s_hour) {
                btn5.setEnabled(true);
                btn6.setEnabled(true);
            }
            else {
                btn5.setEnabled(false);
                btn6.setEnabled(false);
            }
        }
    }

    public void Context5(View v) {
//        btn5.setText("Running");
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
//        btn5.setEnabled(false);
//        btn6.setEnabled(true);

        intent.putExtra("w_hour", w_hour);
        intent.putExtra("w_min", w_min);
        intent.putExtra("s_hour", s_hour);
        intent.putExtra("s_min", s_min);
        intent.putExtra("activity", btn6.getText().toString());

        if(btn5.getText() == "START") {
            startService(intent);
            btn5.setText("STOP");
            btn6.setEnabled(false);
        }
        else {
            intent = new Intent(this, NewsService.class);
            stopService(intent);
            btn5.setText("START");
            btn6.setEnabled(true);
        }



//        startService(intent);
    }

    public void Context6(View v) {
        if(btn6.getText() == "TV")
            btn6.setText("DESKWORK");
        else if(btn6.getText() == "DESKWORK")
            btn6.setText("READ");
        else if(btn6.getText() == "READ")
            btn6.setText("PHONE");
        else if(btn6.getText() == "PHONE")
            btn6.setText("EATING");
        else if(btn6.getText() == "EATING")
            btn6.setText("STAND");
        else if(btn6.getText() == "STAND")
            btn6.setText("STAND/PHONE");
        else if(btn6.getText() == "STAND/PHONE")
            btn6.setText("WALK");
        else if(btn6.getText() == "WALK")
            btn6.setText("WALK/PHONE");
        else if(btn6.getText() == "WALK/PHONE")
            btn6.setText("COOK");
        else
            btn6.setText("TV");

//        btn5.setText("START");
//        btn1.setEnabled(false);
//        btn2.setEnabled(false);
//        btn3.setEnabled(false);
//        btn4.setEnabled(false);
//        btn5.setEnabled(true);
//        btn6.setEnabled(false);
//        intent = new Intent(this, NewsService.class);
//        stopService(intent);
    }

    public void Check_Permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "WAS NOT INCLUDED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
        } else
            Log.d("PERMISSION", "WAS INCLUDED");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "WAS NOT INCLUDED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
        } else
            Log.d("PERMISSION", "WAS INCLUDED");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "WAS NOT INCLUDED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
        } else
            Log.d("PERMISSION", "WAS INCLUDED");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "WAS NOT INCLUDED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.MODIFY_AUDIO_SETTINGS))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
        } else
            Log.d("PERMISSION", "WAS INCLUDED");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "WAS NOT INCLUDED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.BODY_SENSORS))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
        } else
            Log.d("PERMISSION", "WAS INCLUDED");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.KILL_BACKGROUND_PROCESSES) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "WAS NOT INCLUDED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.KILL_BACKGROUND_PROCESSES))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
        } else
            Log.d("PERMISSION", "WAS INCLUDED");
    }
}
