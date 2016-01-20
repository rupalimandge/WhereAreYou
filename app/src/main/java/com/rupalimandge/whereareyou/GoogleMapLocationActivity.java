package com.rupalimandge.whereareyou;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;


public class GoogleMapLocationActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 99;
    static LocationManager locationManager;
    static GoogleMap googleMap;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    Button btnSend = null;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    String deviceId = null, accountName = "", receiverLat, receiverLong, currentDeviceId, selectedDeviceId;

    boolean isFromReceiver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);

        btnSend = (Button) findViewById(R.id.btnWhereAreYou);

        // this code will work for receiver. receiver will get the device id(the unique identifer of device) and will update the current location.
        if(this.getIntent().getExtras() != null){
            deviceId = this.getIntent().getStringExtra("DeviceId");

            if(deviceId != null) {
                isFromReceiver = true;
                btnSend.setText("Share Location");
            }else{
                accountName = this.getIntent().getStringExtra("AccountName");
                isFromReceiver = false;
            }
        }

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion >= 23) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {


                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                //You can also use LocationManager.GPS_PROVIDER and LocationManager.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                currentDeviceId = mngr.getDeviceId();

                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                installation.put("deviceId", currentDeviceId);
                installation.saveInBackground();

                ParseObject testObject = new ParseObject("DeviceInfo");
                testObject.put("deviceId", currentDeviceId);
                testObject.put("name", accountName);
                testObject.saveInBackground();


            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_PHONE_STATE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                //You can also use LocationManager.GPS_PROVIDER and LocationManager.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                currentDeviceId = mngr.getDeviceId();

                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                installation.put("deviceId", currentDeviceId);
                installation.saveInBackground();

                ParseObject testObject = new ParseObject("DeviceInfo");
                testObject.put("deviceId", currentDeviceId);
                testObject.put("name", accountName);
                testObject.saveInBackground();

            }
        }else{
            locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
            //You can also use LocationManager.GPS_PROVIDER and LocationManager.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            currentDeviceId = mngr.getDeviceId();

            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("deviceId", currentDeviceId);
            installation.saveInBackground();

            ParseObject testObject = new ParseObject("DeviceInfo");
            testObject.put("deviceId",currentDeviceId);
            testObject.put("name", accountName);
            testObject.saveInBackground();

        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnSend.getText().toString().equalsIgnoreCase("Share Location")){
                    locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, GoogleMapLocationActivity.this);

                }else{
                    Intent i = new Intent(getApplicationContext(), DevicesListActivity.class);
                    startActivityForResult(i, 100);

                }

            }
        });
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 100
        if(requestCode==100)
        {
            selectedDeviceId=data.getStringExtra("MESSAGE");


            final ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("DeviceInfo");
            userQuery.whereEqualTo("deviceId", selectedDeviceId);

            userQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        Log.d("score", "Retrieved the object.");
                            /*object.put("senderLatitude", String.valueOf(location.getLatitude()));
                            object.put("senderLongitude", String.valueOf(location.getLongitude()));
                            object.saveInBackground();*/

                        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

                        ParseQuery query = ParseInstallation.getQuery();
                        query.whereEqualTo("deviceId", selectedDeviceId);

                        ParsePush push = new ParsePush();
                        push.setQuery(query);
                        String strMessage = null, strPrefix = ":";

                        // deviceId:name wants to know....
                        strMessage = currentDeviceId.concat(":".concat(accountName.concat(" wants to know your current location.")));

                        push.setMessage(strMessage);
                        push.sendInBackground();
                    }
                }
            });

                /*ParseQuery<ParseObject> query = ParseQuery.getQuery("DeviceInfo");
                query.whereEqualTo("deviceId", "357478060184543");
                ParsePush push = new ParsePush();
                push.setQuery(query);
                String strMessage = null, strPrefix = ":";

                // deviceId:name wants to know....
                strMessage = installation.get("deviceId").toString().concat(strPrefix.concat(installation.get("name").toString().concat(" wants to know your current location.")));

                push.setMessage(strMessage);
                push.sendInBackground();*/

                    /*ParseInstallation installation = ParseInstallation.getCurrentInstallation();

                    ParseQuery query = ParseInstallation.getQuery();
                    query.whereEqualTo("deviceId", "357478060184543");*/


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        googleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onLocationChanged(final Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Marker"));

        if(btnSend.getText().toString().equalsIgnoreCase("Share Location")){

            receiverLat = String.valueOf(location.getLatitude());
            receiverLong = String.valueOf(location.getLongitude());

            ParseObject testObject = new ParseObject("DeviceInfo");
            testObject.put("receiverLatitude", receiverLat);
            testObject.put("receiverLongitude", receiverLong);
            testObject.saveInBackground();

        }
        else {

            // ParseObject testObject = new ParseObject("DeviceInfo");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("DeviceInfo");
            query.whereEqualTo("deviceId", currentDeviceId);

            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Log.d("score", "The getFirst request failed.");
                    } else {
                        Log.d("score", "Retrieved the object.");
                        object.put("senderLatitude", String.valueOf(location.getLatitude()));
                        object.put("senderLongitude", String.valueOf(location.getLongitude()));
                        object.saveInBackground();
                    }
                }
            });
        }

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        googleMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
    }

    @Override
    public void onProviderDisabled(String provider) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable location");
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setMessage("Your location is not available . Plaese enable it from settings.");
        AlertDialog dialog=builder.create();
        dialog.show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //You can also use LocationManager.GPS_PROVIDER and LocationManager.
                    locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                    //Granted permission

                } else {
                    // permission denied, boo!
                    Toast.makeText(getApplicationContext(), "Permission Denied..!!", Toast.LENGTH_LONG).show();


                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    currentDeviceId = mngr.getDeviceId();

                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("deviceId", currentDeviceId);
                    installation.saveInBackground();

                    ParseObject testObject = new ParseObject("DeviceInfo");
                    testObject.put("deviceId",currentDeviceId);
                    testObject.put("name", accountName);
                    testObject.saveInBackground();

                } else {

                    Toast.makeText(getApplicationContext(), "Permission Denied..!!", Toast.LENGTH_LONG).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


}