package com.example.appwhysitservice2;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Intent;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Bundle;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;
import java.util.UUID;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class NewsService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,WearableListView.ClickListener {
    private boolean on_advertising = false;
    public static boolean isAdv = false;
    BluetoothLeAdvertiser advertiser;
    ParcelUuid pUuid;
    AdvertiseData data;
    AdvertiseSettings settings;

    Node mNode; // the connected device to send the message to
    GoogleApiClient mGoogleApiClient;

    public static SensorManager mManager;
    public static Sensor mAccel;
    public static Sensor mGyroscope;
    public static Sensor mGravity;
    public static Sensor mRotation;
    public static Sensor mMagnetic;
    public static Sensor mOrientation;
    public static Sensor mLinearAccel;

    public static Sensor mStepCounter;
    public static Sensor mStepDetector;

    public static int after20 = 0;

    public int wake_hour, wake_min, sleep_hour, sleep_min;
    public String activity;
    public int winsize = 0;
    public int start_time = 0;
    public int current_time = 0;
    public int acc_time = 0;
    public int grav_time = 0;
    public int mag_time = 0;
    public int acc_count, min_count;
    public int grav_count;
    public int mag_count;

    public int win1 = 0;
    public int win2 = 0;
    public int win3 = 0;
    public int win4 = 0;
    public int win5 = 0;
    public boolean iswin1 = false;
    public boolean iswin2 = false;
    public boolean iswin3 = false;
    public boolean iswin4 = false;
    public boolean iswin5 = false;

    public float acc1_prev = 0;
    public float acc1_now = 0;
    public float energy1_sum = 0;
    public float acc2_prev = 0;
    public float acc2_now = 0;
    public float energy2_sum = 0;
    public float acc3_prev = 0;
    public float acc3_now = 0;
    public float energy3_sum = 0;

    public int total_count = 0;
    public float seconds = 0;
    public boolean step = false;

    private static ParcelUuid parseUuidFrom(byte[] uuidBytes) {
        /** Length of bytes for 16 bit UUID */
        final int UUID_BYTES_16_BIT = 2;
        /** Length of bytes for 32 bit UUID */
        final int UUID_BYTES_32_BIT = 4;
        /** Length of bytes for 128 bit UUID */
        final int UUID_BYTES_128_BIT = 16;
        final ParcelUuid BASE_UUID = //ParcelUuid.fromString("8CB88C3B-E0B0-4448-B1D3-EE073DDA5941");
                ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");

        if (uuidBytes == null) {
            throw new IllegalArgumentException("uuidBytes cannot be null");
        }
        int length = uuidBytes.length;
        if (length != UUID_BYTES_16_BIT && length != UUID_BYTES_32_BIT &&
                length != UUID_BYTES_128_BIT) {
            throw new IllegalArgumentException("uuidBytes length invalid - " + length);
        }
        // Construct a 128 bit UUID.
        if (length == UUID_BYTES_128_BIT) {
            ByteBuffer buf = ByteBuffer.wrap(uuidBytes).order(ByteOrder.LITTLE_ENDIAN);
            long msb = buf.getLong(8);
            long lsb = buf.getLong(0);
            return new ParcelUuid(new UUID(msb, lsb));
        }
        // For 16 bit and 32 bit UUID we need to convert them to 128 bit value.
        // 128_bit_value = uuid * 2^96 + BASE_UUID
        long shortUuid;
        if (length == UUID_BYTES_16_BIT) {
            shortUuid = uuidBytes[0] & 0xFF;
            shortUuid += (uuidBytes[1] & 0xFF) << 8;
        } else {
            shortUuid = uuidBytes[0] & 0xFF;
            shortUuid += (uuidBytes[1] & 0xFF) << 8;
            shortUuid += (uuidBytes[2] & 0xFF) << 16;
            shortUuid += (uuidBytes[3] & 0xFF) << 24;
        }
        long msb = BASE_UUID.getUuid().getMostSignificantBits() + (shortUuid << 32);
        long lsb = BASE_UUID.getUuid().getLeastSignificantBits();
        return new ParcelUuid(new UUID(msb, lsb));
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void resolveNode() {
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                        for (Node node : nodes.getNodes()) {
                            mNode = node;
                        }
                    }
                });
    }

    @Override
    public void onConnected(Bundle bundle) {
        resolveNode();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void sendMessage(String Key) {
        if (mNode != null && mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Log.d(TAG, "-- " + mGoogleApiClient.isConnected());
            Wearable.MessageApi.sendMessage(
                    mGoogleApiClient, mNode.getId(), Key + "!!!", null).setResultCallback(
                    new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            Log.d(TAG, "Activity Node is : " + mNode.getId() + " - " + mNode.getDisplayName());
                            if (!sendMessageResult.getStatus().isSuccess()) {
                                Log.d(TAG, "Failed to send message with status code: "
                                        + sendMessageResult.getStatus().getStatusCode());
                            }
                        }
                    }
            );
        }
    }

    AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Log.d("BLE", "Advertising onStartSuccess: " + "success");
            on_advertising = true;
            super.onStartSuccess(settingsInEffect);
        }

        @Override
        public void onStartFailure(int errorCode) {
            Log.d("BLE", "Advertising onStartFailure: " + errorCode);
            on_advertising = false;
            super.onStartFailure(errorCode);
        }
    };

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {

    }

    @Override
    public void onTopEmptyRegionClick() {
        Toast.makeText(this, "You tapped on Top empty area", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        advertiser = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();
        settings = new AdvertiseSettings.Builder().setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY).setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH).setConnectable(false).build();
        int serviceUuid = 0xFEAA;
        byte[] serviceUuidBytes = new byte[]{(byte) (serviceUuid & 0xff), (byte) ((serviceUuid >> 8) & 0xff)};
        pUuid = parseUuidFrom(serviceUuidBytes);
        data = new AdvertiseData.Builder().setIncludeDeviceName(true).addServiceUuid(pUuid).addServiceData(pUuid, "Data".getBytes(Charset.forName("UTF-8"))).build();

        mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mGravity = mManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mMagnetic = mManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mRotation = mManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mOrientation = mManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mLinearAccel = mManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mStepCounter = mManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetector = mManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }

    public void onDestroy() {
        mManager.unregisterListener(listener);
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        mManager.registerListener(listener, mAccel, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mGravity, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mRotation, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mOrientation, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mLinearAccel, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL); // 5hz
        mManager.registerListener(listener, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL); // 5hz

        Calendar c = Calendar.getInstance();
        wake_hour = intent.getIntExtra("w_hour", -1);
        wake_min = intent.getIntExtra("w_min", -1);
        sleep_hour = intent.getIntExtra("s_hour", -1);
        sleep_min = intent.getIntExtra("s_min", -1);
        activity = intent.getStringExtra("activity");

        winsize = ((sleep_hour * 60 * 60 + sleep_min * 60) - (wake_hour * 60 * 60 + wake_min * 60)) / 5;
        start_time = c.get(Calendar.SECOND) + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.HOUR) * 60 * 60;

        acc_count = 0;
        min_count = 0;
        grav_count = 0;
        mag_count = 0;

        total_count = 0;
        seconds = 0;
        step = false;

        win1 = start_time + winsize;
        win2 = start_time + winsize * 2;
        win3 = start_time + winsize * 3;
        win4 = start_time + winsize * 4;
        win5 = start_time + winsize * 5;

        return START_STICKY;
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
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

            if(sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
//                Log.d("STEP COUNTER : ", Float.toString(event.values[0]));
                write_file("step_counter", date, activity, event.values[0]);
            }
            else if(sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                step = true;
//                Log.d("STEP DETECTOR : ", Float.toString(event.values[0]));
                write_file("step_detector", date, activity, event.values[0]);
            }
            else if(sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                write_file("acc", date, activity, event.values[0], event.values[1], event.values[2]);
            else if(sensor.getType() == Sensor.TYPE_GYROSCOPE)
                write_file("gyro", date, activity, event.values[0], event.values[1], event.values[2]);
            else if(sensor.getType() == Sensor.TYPE_GRAVITY)
                write_file("grav", date, activity, event.values[0], event.values[1], event.values[2]);
            else if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                write_file("mag", date, activity, event.values[0], event.values[1], event.values[2]);
            else if(sensor.getType() == Sensor.TYPE_ORIENTATION)
                write_file("ori", date, activity, event.values[0], event.values[1], event.values[2]);
            else if(sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
                write_file("rot", date, activity, event.values[0], event.values[1], event.values[2]);
            else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
                write_file("lin_acc", date, activity, event.values[0], event.values[1], event.values[2]);

//                write_file(sensor.getName(), date, activity, event.values[0], event.values[1], event.values[2]);

            current_time = c.get(Calendar.SECOND) + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.HOUR) * 60 * 60;

            if (sensor.getType() == Sensor.TYPE_GRAVITY) {
                if (g_x(event.values[0]) == 1)
                    grav_count += 1;

                if (current_time == grav_time)
                    return;
                else
                    grav_time = current_time;
            }

            if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                if (m_x(event.values[0]) == 1)
                    mag_count += 1;

                if (current_time == mag_time)
                    return;
                else
                    mag_time = current_time;
            }

            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (a_x(event.values[0]) == 1)
                    acc_count += 1;

                if (current_time == acc_time)
                    return;
                else
                    acc_time = current_time;

                Log.d("current - start : ", Integer.toString(current_time - start_time));

                acc1_now = event.values[0];
                acc2_now = event.values[1];
                acc3_now = event.values[2];
                energy1_sum += (acc1_prev - acc1_now) * (acc1_prev - acc1_now);
                energy2_sum += (acc2_prev - acc2_now) * (acc2_prev - acc2_now);
                energy3_sum += (acc3_prev - acc3_now) * (acc3_prev - acc3_now);

                if (after20 - start_time >= 60) {
                    advertiser.stopAdvertising(advertisingCallback);
                    isAdv = false;
                    after20 = 0;
                }

                write_file("engergy", date, activity, energy1_sum, energy2_sum, energy3_sum);

                total_count = 0;
                if(acc_count >= 4) total_count++;
                if(grav_count >= 4) total_count++;
                if(mag_count >= 4) total_count++;

                if(total_count >= 2)
                    seconds += 1;

                energy1_sum = 0;
                energy2_sum = 0;
                energy3_sum = 0;

                acc_count = 0;
                grav_count = 0;
                mag_count = 0;
                acc1_prev = event.values[0];
                acc2_prev = event.values[1];
                acc3_prev = event.values[2];

//                    if (((current_time - start_time) / 60) - min_count >= 2) {
//                        start_time = current_time;
//                        min_count = 0;
//                    }

//                if ((current_time - start_time) >= 60) { // need to change 300 to 1200
                if ((current_time - start_time) % 60 == 0) { // need to change 300 to 1200
                    if (seconds >= 30) {
                        if(step)
                            write_file("minute", date, activity, seconds, "false");
                        else
                        {
                            write_file("minute", date, activity, seconds, "true");
                            min_count += 1;
                        }
                    }
                    else {
                        write_file("minute", date, activity, seconds, "false");
                    }
                    seconds = 0;
                    step = false;

                    if ((current_time - start_time) % 1200 == 0) { // need to change 300 to 1200
                        if (min_count >= 15) {
                            write_file("20minutes", date, activity, min_count, "true");
                        } else {
                            write_file("20minutes", date, activity, min_count, "false");
                        }
                        min_count = 0;
                    }

//                    if (min_count >= 17) { // need to change 4 to 19
//                        start_time = current_time;
//                        min_count = 0;
//
//                        if (current_time < win1 && !iswin1) {
//                            if (!isAdv) {
//                                advertiser.startAdvertising(settings, data, advertisingCallback);
//                                after20 = current_time + 60;
//                                isAdv = true;
//                            }
////                            sendMessage("You are Sitting!");
//                            iswin1 = true;
//                        } else if (win1 < current_time && current_time < win2 && !iswin2) {
//                            if (!isAdv) {
//                                advertiser.startAdvertising(settings, data, advertisingCallback);
//                                after20 = current_time + 60;
//                                isAdv = true;
//                            }
////                            sendMessage("You are Sitting!");
//                            iswin2 = true;
//                        } else if (win2 < current_time && current_time < win3 && !iswin3) {
//                            if (!isAdv) {
//                                advertiser.startAdvertising(settings, data, advertisingCallback);
//                                after20 = current_time + 60;
//                                isAdv = true;
//                            }
////                            sendMessage("You are Sitting!");
//                            iswin3 = true;
//                        } else if (win3 < current_time && current_time < win4 && !iswin4) {
//                            if (!isAdv) {
//                                advertiser.startAdvertising(settings, data, advertisingCallback);
//                                after20 = current_time + 60;
//                                isAdv = true;
//                            }
////                            sendMessage("You are Sitting!");
//                            iswin4 = true;
//                        } else if (win4 < current_time && current_time < win5 && !iswin5) {
//                            if (!isAdv) {
//                                advertiser.startAdvertising(settings, data, advertisingCallback);
//                                after20 = current_time + 1200;
//                                isAdv = true;
//                            }
////                            sendMessage("You are Sitting!");
//                            iswin5 = true;
//                        }
//                    }
                }

//                if (current_time == (win1 - 60) && !iswin1) {
//                    if (!isAdv) {
//                        advertiser.startAdvertising(settings, data, advertisingCallback);
//                        after20 = current_time + 60;
//                        isAdv = true;
//                    }
////                    sendMessage("You are Sitting!");
//                    iswin1 = true;
//                    start_time = current_time;
//                    min_count = 0;
//                } else if (current_time == (win2 - 60) && !iswin2) {
//                    if (!isAdv) {
//                        advertiser.startAdvertising(settings, data, advertisingCallback);
//                        after20 = current_time + 60;
//                        isAdv = true;
//                    }
////                    sendMessage("You are Sitting!");
//                    iswin2 = true;
//                    start_time = current_time;
//                    min_count = 0;
//                } else if (current_time == (win3 - 60) && !iswin3) {
//                    if (!isAdv) {
//                        advertiser.startAdvertising(settings, data, advertisingCallback);
//                        after20 = current_time + 60;
//                        isAdv = true;
//                    }
////                    sendMessage("You are Sitting!");
//                    iswin3 = true;
//                    start_time = current_time;
//                    min_count = 0;
//                } else if (current_time == (win4 - 60) && !iswin4) {
//                    if (!isAdv) {
//                        advertiser.startAdvertising(settings, data, advertisingCallback);
//                        after20 = current_time + 60;
//                        isAdv = true;
//                    }
////                    sendMessage("You are Sitting!");
//                    iswin4 = true;
//                    start_time = current_time;
//                    min_count = 0;
//                } else if (current_time == (win5 - 60) && !iswin5) {
//                    if (!isAdv) {
//                        advertiser.startAdvertising(settings, data, advertisingCallback);
//                        after20 = current_time + 60;
//                        isAdv = true;
//                    }
////                    sendMessage("You are Sitting!");
//                    iswin5 = true;
//                    start_time = current_time;
//                    min_count = 0;
//                }



            }

        }
    };

    public void write_file(String name, String date, String a, float b) {
        File root = Environment.getExternalStorageDirectory();
        File csvfile = new File("sdcard/", name + ".csv");

        FileWriter buffer = null;
        try {
            buffer = new FileWriter(csvfile, true);

            buffer.append(date).append(",");
            buffer.append(a).append(",");
            buffer.append(Float.toString(b)).append("\n");
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void write_file(String name, String date, String a, float b, float c, float d) {
        File root = Environment.getExternalStorageDirectory();
        File csvfile = new File("sdcard/", name + ".csv");

        FileWriter buffer = null;
        try {
            buffer = new FileWriter(csvfile, true);

            buffer.append(date).append(",");
            buffer.append(a).append(",");
            buffer.append(Float.toString(b)).append(",");
            buffer.append(Float.toString(c)).append(",");
            buffer.append(Float.toString(d)).append("\n");
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void write_file(String name, String date, String a, float b, String c) {
        File root = Environment.getExternalStorageDirectory();
        File csvfile = new File("sdcard/", name + ".csv");

        FileWriter buffer = null;
        try {
            buffer = new FileWriter(csvfile, true);

            buffer.append(date).append(",");
            buffer.append(a).append(",");
            buffer.append(Float.toString(b)).append(",");
            buffer.append(c).append("\n");
            buffer.flush();
            buffer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public float a_x(float acc_x) {
        if(acc_x <= -5.343858) { return 0;
        }else
        if(acc_x <= 6.624756) { if (acc_x <= 2.75) {
            if (acc_x <= 0.2) {if (acc_x <= -0.95) return 0;
            else return 1;}
            else if (acc_x <= 2.0) {if (acc_x <= 1.55) return 1;
            else return 0;}
            else return 1;
        }
        else {return 1;} }
        else
        if(acc_x <= 8.02297) { return 1;
        }
        else
        if(acc_x <= 8.528147) { return 0; }
        else
        if(acc_x <= 9.320627) { return 1; }
        else { return 0; }
    }

    public float g_x(float grav_x) {
        if(grav_x <= -5.345205)
            if(grav_x <= -7.542184) { return 0; }
            else
            if(grav_x <= -6.938396)
                if(grav_x <= -7.150733)
                    if(grav_x <= -7.221211) { return 0; }
                    else { return 1; }
                else { return 0; }
            else
            if(grav_x <= -6.155493)
                if(grav_x <= -6.242432) { return 0; }
                else { return 1; }
            else { return 0; }
        else
        if(grav_x <= 6.70975)
            if(grav_x <= -3.063381)
                if(grav_x <= -4.494066)
                    if(grav_x <= -5.049072)
                        if(grav_x <= -5.223699)
                            if(grav_x <= -5.282656) { return 1; }
                            else { return 0; }
                        else { return 1; }
                    else
                    if(grav_x <= -4.05) {return 0;}//-4.748899) { return 0; }
                    else { return 1; }
                else { //return 1; }
                    if (grav_x <= -3.1) {return 0;}
                    else {return 1;}
                }
            else {
                if (grav_x <=3) {//return 0;}
                    if (grav_x <= 1) {if (grav_x <= -1) {
                        if (grav_x <= -1.6) {
                            if (grav_x <= -1.9) {return 0;}
                            else return 1;
                        }
                        else return 0;
                    }
                    else return 1;}
                    else {return 0;}
                } else {return 1;}
            }
        else
        if(grav_x <= 7.972691) { return 1; }
        else
        if(grav_x <= 8.525602)
            if(grav_x <= 8.080132)
                if(grav_x <= 8.044218) { return 0; }
                else { return 1; }
            else { return 0; }
        else
        if(grav_x <= 9.333496)
            if(grav_x <= 9.18311)
                if(grav_x <= 8.956409) { return 1; }
                else { return 0; }
            else { return 1; }
        else { return 0; }
    }

    public float m_x(float mag_x) {
        mag_x = -mag_x;
        if (mag_x <= 20.765924)
//            if (mag_x <= -37.95767)
            if (mag_x <= 0)
                if (mag_x <= -43.75378)
                    if (mag_x <= -61.993473)
                        if (mag_x <= -75.812164)
                            if (mag_x <= -99.47592) {
                                return 1;
                            } else if (mag_x <= -77.81119) {
                                return 0;
                            } else {
                                return 1;
                            }
                        else {
                            return 1;
                        }
                    else {
                        return 1;
                        //return 0;
                    }
                else if (mag_x <= -40.07376)
                    if (mag_x <= -42.32022)
                        if (mag_x <= -43.14081) {
                            return 1;
                        } else {
                            return 0;
                        }
                    else {
                        return 0;
                    }
                else {
                    //return 0;
                    return 1;
                }
            else {
                return 1;
            }
        else {
            return 0;
        }
    }
}

