package laborator03.softwareup.com.lab3_ex2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class ServiceListenLocation extends Service implements LocationListener {
    // override the method for Service
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // to start the service, create an intent object for the specified service
    // method called when the application starts
    public static void startService(Context context) {
        Log.d("START_SERV", "startService: Location service started");
        Intent serviceIntent = new Intent(context, ServiceListenLocation.class);
        context.startService(serviceIntent);
    }

    // define how the service behaves

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // your initialization code there
        // get last known location for providers
        try {

            // init the LocationManager object
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            // request the service to perform periodical updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);

            // get the last known location for gps and wifi providers
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        } catch (SecurityException e) {
            Log.d("START_CMD", String.valueOf(e));
        }

        return START_STICKY;
    }

    // override locationlistener methods
    @Override
    public void onLocationChanged(Location location) {
        // if the location changed - generate the new location
        EventBus.getDefault().post(new LocationEvent("Location changed event", location));
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
}
