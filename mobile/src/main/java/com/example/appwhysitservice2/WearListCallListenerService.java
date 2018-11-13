package com.example.appwhysitservice2;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
//import com.example.test.MainActivity

//import com.androprogrammer.tutorials.activities.Listactivity;

/*
public class WearListCallListenerService extends WearableListenerService {

    public static String SERVICE_CALLED_WEAR = "WearListClicked";


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        String event = messageEvent.getPath();

        MainActivity.t.setText("YES!!");

        Log.v("Listclicked", "YES");
//        Log.d("Listclicked", event);

        String [] message = event.split("--");

        if (message[0].equals(SERVICE_CALLED_WEAR)) {
            Log.d("Come Here?", "Yes");
//            startActivity(new Intent((Intent) Listactivity.getInstance().tutorials.get(message[1]))
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
*/


/*
public class WearListCallListenerService extends WearableListenerService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private GoogleApiClient mApiClient;
    private SharedPreferences prefs;

    @Override
    public void onCreate()
    {
        Log.d("", "##DataService created1");
        Log.v("", "##DataService created2");

        super.onCreate();
        if (mApiClient == null)
        {
            mApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        }

        if (!mApiClient.isConnected())
        {
            mApiClient.connect();
            Log.d("", "##Api connecting");
        }
    }

    @Override
    public void onDestroy()
    {
        Log.d("", "##DataService destroyed");
        if (null != mApiClient)
        {
            if (mApiClient.isConnected())
            {
                mApiClient.disconnect();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        Log.d("", "##DataService received " + messageEvent.getPath());
        super.onMessageReceived(messageEvent);
        if (messageEvent.getPath().contains("/OPEN;"))
        {
            // only if called twice :(
        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        Log.d("", "##Data Changed@@@@@@@@@@@@@22d");
    }


    @Override
    public void onConnectionFailed(ConnectionResult arg0)
    {
        Log.d("", "##Connection Failed@@@@@@@@@@@@@22d");

    }

    @Override
    public void onConnected(Bundle arg0)
    {
        Log.d("", "##Connection Succeeded@@@@@@@@@@@@@22d");
    }

    @Override
    public void onConnectionSuspended(int arg0)
    {
        Log.d("", "##Connection Suspended@@@@@@@@@@@@@22d");

    }
}
*/

import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

import static android.support.v4.app.NotificationCompat.*;

public class WearListCallListenerService extends WearableListenerService {

    private final String LOG_TAG = WearListCallListenerService.class.getSimpleName();

    private static GoogleApiClient googleApiClient;

    private static final long CONNECTION_TIME_OUT_MS = 100;
    private static final String WEAR_PATH = "/wear";
    private String nodeId;
    Button btn2;

    NotificationManager mNotiManager;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        mNotiManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        Log.d("LOG PATH = ", messageEvent.getPath());

        Intent intent = new Intent(this, Q1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent content = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.small_icon)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setContentTitle("Whysit Survey")
                .setContentText("Please click here to take the survey!")
                .setAutoCancel(true)
                .setContentIntent(content);


        mNotiManager.notify(001, mBuilder.build());

        Handler h = new Handler();
        long delayInMilliseconds = 1200000; // 1200000 for 20min, 420000 for 7min, 5000 for 5sec
        h.postDelayed(new Runnable() {
            public void run() {
                mNotiManager.cancel(001);
            }
        }, delayInMilliseconds);

//        this.startActivity(intent); // need to do this


//        startActivities(intent);
//        toast.show();
//        if(toast.getView().isShown()) {toast.cancel();}//Toast is cancelled here if currently showing



//        final Toast toast = Toast.makeText(this, messageEvent.getPath(), Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.show();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {




//            @Override
//            public void run() {
//                toast.cancel();
//            }
//        }, 1500);

//        if (messageEvent.getPath().equals("/mobile")) {
//            nodeId = messageEvent.getSourceNodeId();
//            Log.v(LOG_TAG, "Node ID of watch: " + nodeId);
//            showToast(messageEvent.getPath());

//            reply(WEAR_PATH);
//        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void reply(final String path) {
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Wearable.API)
                .build();

        Log.v(LOG_TAG, "In reply()");
        Log.v(LOG_TAG, "Path: " + path);

        if (googleApiClient != null && !(googleApiClient.isConnected() || googleApiClient.isConnecting()))
            googleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);

        Wearable.MessageApi.sendMessage(googleApiClient, nodeId, path, null).await();
        googleApiClient.disconnect();
    }
}