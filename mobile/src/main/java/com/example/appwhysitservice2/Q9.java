package com.example.appwhysitservice2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import android.provider.Settings.Secure;

public class Q9 extends AppCompatActivity {

    TextView q9_text1;
    TextView q9_text2;
    Button q9_btn2;
    Button q9_btn1;
    String q1, q2, q3, q4, q5, q6a, q6b, q6c, q6d, q7, q8;
    LocationManager mLocMan;
    String mProvider;
    String output = "";
    SendfeedbackJob job;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q9);

        q9_text1 = (TextView) findViewById(R.id.q9_text1);
        q9_text2 = (TextView) findViewById(R.id.q9_text2);
        q9_btn2 = (Button) findViewById(R.id.q9_btn2);
        q9_btn1 = (Button) findViewById(R.id.q9_btn1);
        mLocMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mProvider = mLocMan.getBestProvider(new Criteria(), true);
        Log.d("COME?", " YEAH");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "WAS NOT INCLUDED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
                Log.d("PERMISSION", "ALLOWED");
            else
                Log.d("PERMISSION", "NOT ALLOWED");
        } else {
            Log.d("PERMISSION", "WAS INCLUDED");
        }
        Log.d("PERMISSION", "WAS SUCCESS");

//        mLocMan.requestLocationUpdates(mProvider, 3000, 1, mListener);
        mLocMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mListener);


        Intent intent = getIntent();
        q1 = intent.getStringExtra("q1");
        q2 = intent.getStringExtra("q2");
        if (q2 == null)
            q2 = "N/A";
        q3 = intent.getStringExtra("q3");
        if (q3 == null)
            q3 = "N/A";
        else {
            String temp = "";
            if (q3.substring(0, 1).equals("1"))
                temp += " / I'm not willing to be disrupted";
            if (q3.substring(1, 2).equals("1"))
                temp += " / Get up and walk";
            if (q3.substring(2, 3).equals("1"))
                temp += " / Get up and walk";
            if (q3.substring(3, 4).equals("1"))
                temp += " / Get up and complete calisthenics";
            q3 = temp;
        }
        q4 = intent.getStringExtra("q4");
        if (q4 == null)
            q4 = "N/A";
        q5 = intent.getStringExtra("q5");

        String temp = "";
        if (q5.substring(0, 1).equals("1"))
            temp += "/Alone";
        if (q5.substring(1, 2).equals("1"))
            temp += "/Spouse,SignificantOther";
        if (q5.substring(2, 3).equals("1"))
            temp += "/Family Member";
        if (q5.substring(3, 4).equals("1"))
            temp += "/Friend";
        if (q5.substring(4, 5).equals("1"))
            temp += "/Colleague";
        if (q5.substring(5, 6).equals("1"))
            temp += "/Acquaintance";
        if (q5.substring(6, 7).equals("1"))
            temp += "/Pet";
        q5 = temp;

        q6a = intent.getStringExtra("q6a");
        q6b = intent.getStringExtra("q6b");
        if (q6b == null)
            q6b = "N/A";
        q6c = intent.getStringExtra("q6c");
        if (q6c == null)
            q6c = "N/A";
        q6d = intent.getStringExtra("q6d");
        if (q6d == null)
            q6d = "N/A";
        q7 = intent.getStringExtra("q7");
        q8 = intent.getStringExtra("q8");
    }

    public void onPause()
    {
        super.onPause();
        mLocMan.removeUpdates(mListener);
    }

    public void q9_btn(View v)
    {
//        q9_text1.setVisibility(View.VISIBLE);
//        q9_btn1.setVisibility(View.INVISIBLE);
        output = "";
        output += "Q1=" + q1 + "\n";
        output += "Q2=" + q2 + "\n";
        output += "Q3=" + q3 + "\n";
        output += "Q4=" + q4 + "\n";
        output += "Q5=" + q5 + "\n";
        output += "Q6=" + q6a + "\n";
        output += "Q6B=" + q6b + "\n";
        output += "Q6C=" + q6c + "\n";
        output += "Q6D=" + q6d + "\n";
        output += "Q7=" + q7 + "\n";
        output += "Q8=" + q8 + "\n";

//        q9_text2.setText(output);
    }

    public void q9_btn2(View v) {

        new SendfeedbackJob(Q9.this).execute();

        Toast.makeText(this, "Thank you for the Whysit Survey!", Toast.LENGTH_SHORT).show();
        finish();
    }

    LocationListener mListener = new LocationListener() {
        public void onLocationChanged(Location location)
        {
            String sloc = String.format("Latitude=%f\nLongitude=%f\nAltitude=%f\n", location.getLatitude(), location.getLongitude(), location.getAltitude());
            q9_text2.setText(sloc);

            output = "";
            output += "Q1=" + q1 + "\n";
            output += "Q2=" + q2 + "\n";
            output += "Q3=" + q3 + "\n";
            output += "Q4=" + q4 + "\n";
            output += "Q5=" + q5 + "\n";
            output += "Q6=" + q6a + "\n";
            output += "Q6B=" + q6b + "\n";
            output += "Q6C=" + q6c + "\n";
            output += "Q6D=" + q6d + "\n";
            output += "Q7=" + q7 + "\n";
            output += "Q8=" + q8 + "\n";
            output += sloc + "\n";
            q9_btn2.setVisibility(View.VISIBLE);
        }

        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            String sStatus = "";
            switch(status) {
                case LocationProvider.OUT_OF_SERVICE:
                    sStatus = "Out of Service";
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    sStatus = "Temporarily Unavailable";
                    break;
                case LocationProvider.AVAILABLE:
                    sStatus = "Available";
                    break;
            }
            q9_text2.setText("Location Status : " + sStatus);
        }

        @Override
        public void onProviderEnabled(String provider) {
            q9_text2.setText("Location Available");
        }

        @Override
        public void onProviderDisabled(String provider) {
            q9_text2.setText("Location Not Available");
        }
    };


    public class SendfeedbackJob extends AsyncTask<String, Void, String> {

        private Context context;

        public SendfeedbackJob(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String[] params) {
            OutputStreamWriter writer = null;
            BufferedReader reader = null;
            String android_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

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

            try {
                URL url = new URL("http://murphy.wot.eecs.northwestern.edu/~slk2940/survey.py");

                StringBuffer buffer = new StringBuffer();
                buffer.append("mac").append("=").append(android_id).append("&");
                buffer.append("time").append("=").append(date).append("&");
                buffer.append("activity").append("=\"").append(output).append("\"");


                URLConnection http = url.openConnection();
                http.setDoOutput(true);
                writer = new OutputStreamWriter(http.getOutputStream());
                writer.write(buffer.toString());
                writer.flush();

                reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String str = null;
                Log.d("ON HTTP POSTING - ", buffer.toString());
                do {
                    str = reader.readLine();
                } while (str != null);

            } catch (Exception e) {
                Log.d("POST : ", "Posting Error 1");
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null)
                        writer.close();
                } catch (IOException e) {
                    Log.d("POST : ", "Posting Error 2");
                    e.printStackTrace();
                }
            }
            return "some message";
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
        }
    }
}