package com.example.lifeband.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lifeband.R;
import com.example.lifeband.utils.AdapterData;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<AdapterData> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(List<AdapterData> data, Context context) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.customlist_view, parent, false);
            holder = new ViewHolder();
            holder.tv_BPM = (TextView) convertView.findViewById(R.id.bpm_tv);
            holder.tv_date = convertView.findViewById(R.id.date_tv);
            holder.tv_TEMP = convertView.findViewById(R.id.temp_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Car car = this.cars.get(position);
        AdapterData adapterData = this.data.get(position);
        holder.tv_TEMP.setText(adapterData.getTEMP() + " °C");
        holder.tv_date.setText(adapterData.getDate());
        holder.tv_BPM.setText(adapterData.getBPM() + " BPM");
        return convertView;
    }

    static class ViewHolder {
        TextView tv_date;
        TextView tv_BPM;
        TextView tv_TEMP;
    }
}
