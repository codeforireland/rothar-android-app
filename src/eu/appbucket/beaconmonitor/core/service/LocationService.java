package eu.appbucket.beaconmonitor.core.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LocationService {
	
	private Context context;
	
	public LocationService (Context context) {
		this.context = context;
	}
	
	public Location getCurrentLocation() {
		 LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		 Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		 return location;
	}
}
