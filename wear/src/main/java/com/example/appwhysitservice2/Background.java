package com.example.appwhysitservice2;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
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

public class Background extends AsyncTask<Object, Void, Void> {
    public static Context context;
    public static int interval = 0;
    public static boolean a = false;
    public static boolean b = false;
    public static boolean d = false;
    public static boolean e = false;
    public static boolean f = false;

    public static Sensor mAccel;
    public static Sensor mGyro;
    public static Sensor mMagnetic;
    public static Sensor mOrientation;
    public static Sensor mLinearAccel;
    public static Sensor mStepDetector;

    public static SensorManager mManager;

    public static String user = "A";
    public static String act = "0";
    public static String a1 = "0";
    public static String a2 = "0";
    public static String a3 = "0";
    public static String b1 = "0";
    public static String b2 = "0";
    public static String b3 = "0";
    public static String c1 = "0";
    public static String c2 = "0";
    public static String c3 = "0";
    public static String d1 = "0";
    public static String d2 = "0";
    public static String d3 = "0";
    public static String e1 = "0";
    public static String e2 = "0";
    public static String e3 = "0";
    public static String f1 = "0";
    public static String f2 = "0";
    public static String f3 = "0";
    public static String g1 = "0";
    public static String h1 = "0";

    public static AudioRecord audio;
    public static int sampleRate = 8000;
    public static int bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
    public static double lastLevel = 0.0;

    public Background(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        mManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        mAccel = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro = mManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetic = mManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mOrientation = mManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mLinearAccel = mManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mStepDetector = mManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
    }

    protected Void doInBackground(Object... params) {

        if (params[0] == "REPLICATE") {
            Log.d("REPLICATE?", "YES");
            replicate();
        }
        else
        {
            mManager.registerListener(accelListener, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
            mManager.registerListener(gyroListener, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
            mManager.registerListener(magneticListener, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
            mManager.registerListener(orientationListener, mOrientation, SensorManager.SENSOR_DELAY_NORMAL);
            mManager.registerListener(linearaccelListener, mLinearAccel, SensorManager.SENSOR_DELAY_NORMAL);
            mManager.registerListener(stepdetectorListener, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }


        return null;
    }

    public void replicate()
    {
        File root = Environment.getExternalStorageDirectory();
//        String PATH = context.getExternalFilesDir(null) + "/WearData/ERRORS/";

        File csvfile = new File(root, "whysit.csv");
//        File csvfile = new File(root, "/sdcard/whysit.csv");
        FileWriter buffer = null;
        Log.d("TAG 1 @@ DATA COUNT = ", "COME HERE?");

        try {
            buffer = new FileWriter(csvfile, true);
            Cursor c = EnergyDBHelper.getLatest60Entries(context);
            Log.d("TAG 1 @@ DATA COUNT = ", Integer.toString(c.getCount()));

            if (c.moveToFirst()) {
                while (!c.isAfterLast()) {
                    String id = c.getString(0);
                    String date = c.getString(1);
                    String type = c.getString(2);
                    Float a1 = c.getFloat(3);
                    Float a2 = c.getFloat(4);
                    Float a3 = c.getFloat(5);
                    Float b1 = c.getFloat(6);
                    Float b2 = c.getFloat(7);
                    Float b3 = c.getFloat(8);
                    Float c1 = c.getFloat(9);
                    Float c2 = c.getFloat(10);
                    Float c3 = c.getFloat(11);
                    Float d1 = c.getFloat(12);
                    Float d2 = c.getFloat(13);
                    Float d3 = c.getFloat(14);
                    Float aud = c.getFloat(15);
                    Float e1 = c.getFloat(16);
                    Float e2 = c.getFloat(17);
                    Float e3 = c.getFloat(18);
                    Float f1 = c.getFloat(19);
                    Float f2 = c.getFloat(20);
                    Float f3 = c.getFloat(21);
                    Float g1 = c.getFloat(22);
                    Float h1 = c.getFloat(23);

                    buffer.append(id).append(",");
                    buffer.append(date).append(",");
                    buffer.append(type).append(",");
                    buffer.append(Float.toString(a1)).append(",");
                    buffer.append(Float.toString(a2)).append(",");
                    buffer.append(Float.toString(a3)).append(",");
                    buffer.append(Float.toString(b1)).append(",");
                    buffer.append(Float.toString(b2)).append(",");
                    buffer.append(Float.toString(b3)).append(",");
                    buffer.append(Float.toString(c1)).append(",");
                    buffer.append(Float.toString(c2)).append(",");
                    buffer.append(Float.toString(c3)).append(",");
                    buffer.append(Float.toString(d1)).append(",");
                    buffer.append(Float.toString(d2)).append(",");
                    buffer.append(Float.toString(d3)).append(",");
                    buffer.append(Float.toString(aud)).append(",");
                    buffer.append(Float.toString(e1)).append(",");
                    buffer.append(Float.toString(e2)).append(",");
                    buffer.append(Float.toString(e3)).append(",");
                    buffer.append(Float.toString(f1)).append(",");
                    buffer.append(Float.toString(f2)).append(",");
                    buffer.append(Float.toString(f3)).append(",");
                    buffer.append(Float.toString(g1)).append(",");
                    buffer.append(Float.toString(h1));
                    buffer.append("\n");
                    Log.d("BUFFER = ", buffer.toString());
                    c.moveToNext();
                    buffer.flush();
                }
            }

            buffer.close();
            c.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        EnergyDBHelper.deleteEnergy(context);
        Log.d("SENT DONE ?", "YES");
    }


    protected void onPostExecute(String result) {

    }

    public static double getaud()
    {
        audio.startRecording();

        short[] buffer = new short[bufferSize];
        int bufferReadResult = 1;

        if (audio != null) {
            bufferReadResult = audio.read(buffer, 0, bufferSize);
            double sumLevel = 0;
            for (int i = 0; i < bufferReadResult; i++) {
                sumLevel += Math.abs(buffer[i]);
            }
            lastLevel = sumLevel / bufferReadResult;
        }
        double result = -20 * Math.log10(lastLevel / 100000000) - 94;
        audio.stop();
        return Double.valueOf(result);
    }

    public static SensorEventListener accelListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
//            Log.d("TAG!!!", "Accel Event received! x: " + Float.toString(event.values[0]));
//            Log.d("TAG!!!", "Accel Event received! y: " + Float.toString(event.values[1]));
//            Log.d("TAG!!!", "Accel Event received! z: " + Float.toString(event.values[2]));

            a1 = Float.toString(event.values[0]);
            a3 = Float.toString(event.values[2]);
            a = true;
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.w("TAG!!!", "Accuracy of accelerometer changed.");
        }
    };

    public static SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            b1 = Float.toString(event.values[0]);
            b2 = Float.toString(event.values[1]);
            b = true;

            interval += 1;
//            if(a && b && f && d && e)
            if (interval % 30 == 0)
            {


//                String aud = Double.toString(getaud());
                String aud = "0.0";

                Calendar c = Calendar.getInstance();
                String date = c.get(Calendar.YEAR) + "-" +
                        ((c.get(Calendar.MONTH) < 9) ? "0" : "") +
                        (c.get(Calendar.MONTH) + 1) + "-" +
                        ((c.get(Calendar.DAY_OF_MONTH) < 10) ? "0" : "") +
                        c.get(Calendar.DAY_OF_MONTH) + " " +
                        ((c.get(Calendar.HOUR_OF_DAY) < 10) ? "0" : "") +
                        c.get(Calendar.HOUR_OF_DAY) + ":" +
                        ((c.get(Calendar.MINUTE) < 10) ? "0" : "") +
                        c.get(Calendar.MINUTE) + ":" +
                        ((c.get(Calendar.SECOND) < 10) ? "0" : "") +
                        c.get(Calendar.SECOND);
                Log.d("USER ID = ", user);
                Log.d("INTERVAL = ", Integer.toString(interval));

                EnergyDBHelper.enterEnergy(user, date, act, a1, a2, a3, b1, b2, b3, c1, c2, c3, d1, d2, d3, aud, e1, e2, d3, f1, f2, f3, g1, h1, context);

                h1 = "0";
                a = false;
                b = false;
                d = false;
                e = false;
                f = false;
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.w("TAG!!!", "Accuracy of accelerometer changed.");
        }
    };

    public static SensorEventListener magneticListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            d1 = Float.toString(event.values[0]);
            d2 = Float.toString(event.values[1]);
            d3 = Float.toString(event.values[2]);
            d = true;
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.w("TAG!!!", "Accuracy of accelerometer changed.");
        }
    };

    public static SensorEventListener orientationListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            e1 = Float.toString(event.values[0]);
            e2 = Float.toString(event.values[1]);
            e3 = Float.toString(event.values[2]);
            e  = true;
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.w("TAG!!!", "Accuracy of accelerometer changed.");
        }
    };

    public static SensorEventListener linearaccelListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            g1 = Float.toString(event.values[0]);
            f = true;
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.w("TAG!!!", "Accuracy of accelerometer changed.");
        }
    };

    public static SensorEventListener stepdetectorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            h1 = Float.toString(event.values[0]);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.w("TAG!!!", "Accuracy of accelerometer changed.");
        }
    };
}


