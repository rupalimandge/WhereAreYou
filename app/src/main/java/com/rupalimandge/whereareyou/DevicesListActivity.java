package com.rupalimandge.whereareyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class DevicesListActivity extends Activity {

    ListView lvDevices = null;
    ArrayList<DeviceInfo> arrListDevices = null;
    String selectedDeviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);

        arrListDevices = new ArrayList<>();
        lvDevices = (ListView) findViewById(R.id.lvDevices);

        getDevicesList();

        lvDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedDeviceId = arrListDevices.get(position).getDeviceId();
                Intent intent=new Intent();
                intent.putExtra("MESSAGE",selectedDeviceId);
                setResult(2,intent);
                finish();//finishing activity
            }
        });
    }

    private void getDevicesList() {

         ParseQuery<ParseObject> query = ParseQuery.getQuery("DeviceInfo");

      //  ParseQuery query = ParseInstallation.getQuery();

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    arrListDevices.clear();
                    for (ParseObject post : postList) {

                        // DeviceInfo dInfo = new DeviceInfo(post.getString("deviceId"),post.getString("name"),post.getString("mobileNumber"));
                        DeviceInfo dInfo = new DeviceInfo(post.getString("deviceId"), post.getString("name"), "XXXXXXXXXX");
                        arrListDevices.add(dInfo);

                    }
                    lvDevices.setAdapter(new DeviceListAdapter(getApplicationContext(), arrListDevices));
                    //    ((ArrayAdapter<Note>) getListAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });


    }
}
