package laborator03.softwareup.com.lab3_ex2;

import android.location.Location;

/**
 * Created by student on 3/19/18.
 */

// LocationEvent class - set location and get it
public class LocationEvent extends Event{

    private Location location;

    public LocationEvent(String message, Location location) {
        super(message);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
