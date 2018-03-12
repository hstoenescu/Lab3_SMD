package laborator03.softwareup.com.lab3_ex2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.OptionalDataException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PERM";

    int PERMISSIONS_REQUEST_CODE = 123;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // define button
        final Button button = (Button) findViewById(R.id.button);
        // text views
        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        final TextView textView3 = (TextView) findViewById(R.id.textView3);

        // verify permissions
        if (ContextCompat.checkSelfPermission(this, String.valueOf(PERMISSIONS)) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
        }

        // define location manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        // get the last known location
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        double lastLatitude = 0.0;
        double lastLongitude = 0.0;
        if (lastKnownLocation != null) {
            lastLatitude = lastKnownLocation.getLatitude();
            lastLongitude = lastKnownLocation.getLongitude();
        }

        final double finalLastLatitude = lastLatitude;
        final double finalLastLongitude = lastLongitude;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // last known location - show them to textView3
                String last_location = "LAST KNOWN LOCATION: " + Double.toString(finalLastLatitude) + " " + Double.toString(finalLastLongitude);
                textView3.setText(last_location);
                //Log.d("aa",Double.toString(finalLastLatitude) + " " + Double.toString(finalLastLongitude) );
            }
        });

    }

    // used to request the permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // your code here
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length >= PERMISSIONS.length) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: Permission " + permissions[i] +
                                " was not granted");
                    }
                }
            } else {
                Log.d(TAG, "onRequestPermissionsResult: not all permissions were granted");
            }
        }
    }
}
