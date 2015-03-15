package com.example.kunal.myapplication;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class WearAdapter extends WearableListView.Adapter {
    private String[] mDataset;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private static final String TAG = "Adapter";

    public WearAdapter(Context context, String[] dataset) {
        Log.i(TAG, "Initialize adapter");
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataset = dataset;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        Log.i(TAG, "Creating element");
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = itemHolder.textView;
        view.setText(mDataset[position]);
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}