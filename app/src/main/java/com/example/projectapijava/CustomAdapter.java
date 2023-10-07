package com.example.projectapijava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter<CustomAdapter1> extends BaseAdapter implements View.OnClickListener{
    private final Context mContext;
    private final List<Asteriods> mItems;
    private LayoutInflater inflater;

    public CustomAdapter(Context mContext, List<Asteriods> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.inflater = inflater;
    }

    @Override
    public void onClick(View view) {
        Intent switchActivityIntent = new Intent(this.mContext, Astriod_info.class);
        switchActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switchActivityIntent.putExtra("asteroid", (Asteriods) view.getTag());
        CustomAdapter.this.mContext.startActivity(switchActivityIntent);

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.row_xml, parent, false);
        }

        Asteriods item = (Asteriods) getItem(i);

        TextView nameTextView = convertView.findViewById(R.id.textName);
        TextView magnitudeTextView = convertView.findViewById(R.id.textMagnitude);
        TextView distanceTextView = convertView.findViewById(R.id.textDistance);

        nameTextView.setText(String.format(mContext.getString(R.string.name_asteroid), item.getName()));
        magnitudeTextView.setText(String.format(mContext.getString(R.string.magnitude), item.getMagnitude()));
        distanceTextView.setText(String.format(mContext.getString(R.string.distance), item.getDistance()));
        convertView.setTag(item);
        convertView.setOnClickListener(this);

        return convertView;
    }

  

}
