package com.rupalimandge.whereareyou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rupali on 20/01/16.
 */
public class DeviceListAdapter extends BaseAdapter{

    Context mContext = null;
    ArrayList<DeviceInfo> arrListDevices = null;

    public DeviceListAdapter(Context mContext, ArrayList<DeviceInfo> arrListDevices){
        this.mContext = mContext;
        this.arrListDevices = arrListDevices;
    }

    @Override
    public int getCount() {
        return arrListDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        DeviceInfo dInfo = arrListDevices.get(i);

        ViewHolder view = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            view.tvName = (TextView) convertView
                    .findViewById(R.id.tvName);

            view.tvMobileNumber = (TextView) convertView
                    .findViewById(R.id.tvMobileNumber);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        view.tvName.setText(dInfo.getUsername());
        view.tvMobileNumber.setText(dInfo.getMobileNo());

        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvMobileNumber;
    }
}
