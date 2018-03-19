package com.example.student.lab3_ex3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // Get a reference to the Button
        // and the NonScrollListView elements in the onCreate method for the Activity.
        final Button button = (Button) findViewById(R.id.button4);
        final NonScrollListView nonScrollListView = findViewById(R.id.task4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> elements = new ArrayList<>();

                // Use the WiFiManager to scan for WiFi devices
                // and print their MAC address.
                // Do this in the OnClickListener for the button
                // and add the MAC addresses to the elements list.

                // inner class for wifi scan recv
                class WifiScanReceiver extends BroadcastReceiver {
                    String data;

                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Log.d("RECV", "I AM IN");
                        List<ScanResult> wifiScanList = wifiManager.getScanResults();
                        data = wifiScanList.get(0).toString();
                        Log.d("WifiScanReceiver", data);
                        //List<String> elements = new ArrayList<>();
                        //elements = wifiScanList.get
                        for (ScanResult result: wifiScanList) {
                            Log.d("WifiScanReceiver",String.format("%s",result.SSID) );
                            elements.add(result.SSID);
                        }

                        ListViewAdapter adapter = new ListViewAdapter(elements, getApplicationContext());
                        nonScrollListView.setAdapter(adapter);
                    }
                }

                WifiScanReceiver wifiScanReceiver = new WifiScanReceiver();
                registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


            }
        });

    }
}
