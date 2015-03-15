package com.example.kunal.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends Activity
        implements WearableListView.ClickListener,DataApi.DataListener,
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "WearActivity";
    private static final String GET_CONTENT = "/content";
    private static final String CONTENT_TITLE = "title";
    private static final String CONTENT_BODY = "body";

    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError = false;
    private WearableListView mListView;
    private WearAdapter adapter;
    private String[] list = {"TITLE", "TEXT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(Wearable.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();

        setContentView(R.layout.activity_main);
        mListView = (WearableListView) findViewById(R.id.list);
        adapter = new WearAdapter(this, list);
        mListView.setAdapter(adapter);
        mListView.setClickListener(this);

    }

    // WearableListView click listener
    @Override
    public void onClick(WearableListView.ViewHolder v) {
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "Connected to mobile device");
        Wearable.DataApi.addListener(mGoogleApiClient, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            Wearable.DataApi.removeListener(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.i(TAG, "Data Changed");
        for (DataEvent event : dataEvents) {
            DataItem item = event.getDataItem();
            if (item.getUri().getPath().compareTo(GET_CONTENT) == 0) {
                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                Log.i(TAG, "GOT"+dataMap.get(CONTENT_TITLE).toString());
                String title = dataMap.get(CONTENT_TITLE).toString();
                String body =  dataMap.get(CONTENT_BODY).toString();
                list[0] = title;
                list[1] = body;
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}





