package com.example.kunal.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import retrofit.RestAdapter;


public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private static final String GET_CONTENT = "/content";
    private static final String CONTENT_TITLE = "title";
    private static final String CONTENT_BODY = "body";

    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError = false;

//    Using locally hosted Drupal server
    private static final String API_URL = "http://192.168.1.220/~kunal/drupal8/";

    private RestAdapter restAdapter;
    private DrupalRequest req;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();
        req = restAdapter.create(DrupalRequest.class);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        final Button button = (Button) findViewById(R.id.button);
        final EditText edit  = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(
            new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Fetching data", Toast.LENGTH_SHORT).show();
                    APIAsyncTask task = new APIAsyncTask();
                    task.execute(req, getApplicationContext(), edit.getText().toString());
                }
            });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "Google API Client was connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Failed to connect to wear");
    }

    public class StartWearableActivityTask extends AsyncTask<String[], Void, Boolean> {
        private final static String TAG = "WearableTask";

        @Override
        protected Boolean doInBackground(String[]... args) {
            if (mGoogleApiClient.isConnected()) {
                String title = args[0][0];
                String body = args[0][1];

                PutDataMapRequest putDataMapReq = PutDataMapRequest.create(GET_CONTENT);
                putDataMapReq.getDataMap().putString(CONTENT_TITLE, title);
                putDataMapReq.getDataMap().putString(CONTENT_BODY, body);

                PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                PendingResult<DataApi.DataItemResult> pendingResult =
                        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
                Log.i(TAG, "Done");
                return true;
            } else {
                Log.e(TAG, "Not connected, data not sent");
                return false;
            }
        }


        @Override
        protected void onPostExecute(Boolean arg) {
            if (arg)
                Toast.makeText(getApplicationContext(), "Sent data to wear", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Failed to send data", Toast.LENGTH_LONG).show();
        }
    }


    public class APIAsyncTask extends AsyncTask<Object, String, String[]> {
        private String TAG = "DrupalGet";
        private Context context;

        @Override
        protected String[] doInBackground(Object... params) {
            try {
                DrupalRequest req = (DrupalRequest) params[0];
                this.context = (Context) params[1];
                String id = (String) params[2];

                DrupalResponse resp = req.requester(id);
                String body = resp.getBody();
                String title = resp.getTitle();
                String s[] = {body, title};
                Log.i(TAG, s[0]+s[1]);
                return s;
            } catch(Exception e) {
                Log.e(TAG, "Getting post", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            new StartWearableActivityTask().execute(result);
        }
    }

}