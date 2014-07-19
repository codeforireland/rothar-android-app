package eu.appbucket.beaconmonitor.core.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import eu.appbucket.beaconmonitor.core.constants.App;

public class CommunicationService extends BroadcastReceiver {
	
	private static final String LOG_TAG = CommunicationService.class.getName();
	private Context context;

	private class StolenAsset implements Serializable {
		
		private String assetId;
		private double latitude;
		private double longitude;
		
		public StolenAsset() {			
		}
		
		public StolenAsset(BluetoothLeDevice beacon, Location location) {
			IBeaconManufacturerData iBeaconData = new IBeaconManufacturerData(beacon);
			this.assetId = iBeaconData.getUUID();
			this.longitude = location.getLongitude();
			this.latitude = location.getLatitude();
		}
		
		public String getAssetId() {
			return assetId;
		}
		public void setAssetId(String assetId) {
			this.assetId = assetId;
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		
		public String toJson() throws JSONException {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("assetId", this.assetId);
			jsonObj.put("latitude", this.latitude);
			jsonObj.put("longitude", this.longitude);			
			return jsonObj.toString();
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		Parcelable[] foundDevices = intent.getParcelableArrayExtra(App.BROADCAST_DEVICE_FOUND_PAYLOAD);
		for(Parcelable device:  foundDevices) {		
			try {
				reportStolenAsset((BluetoothLeDevice) device);
			} catch (JSONException e) {
				log("Can't convert to json");
			}
		}
	}
	
	public void reportStolenAsset(BluetoothLeDevice device) throws JSONException {
		if(!isOnline()) {
			log("Connection is not available");
			return;
		}
		Location currentLocation = getCurrentLocation();
		StolenAsset stolenAsset = new StolenAsset(device, currentLocation);
		String jsonRepresentation = stolenAsset.toJson();		
		try {
			this.postData(jsonRepresentation);
		} catch (IOException e) {
			log("Can't establish connection.");
		}
	}
	
	private class NetworkTask extends AsyncTask<String, Void, HttpResponse> {
	    @Override
	    protected HttpResponse doInBackground(String... params) {	    	
	        String link = params[0];
	        String payload = params[1];
	        AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
	        HttpPost request = new HttpPost(link);
	        HttpResponse response = null;
	        request.setHeader( "Content-Type", "application/json" );
	        try {
	        	StringEntity se = new StringEntity(payload);
		        se.setContentEncoding("UTF-8");
		        se.setContentType("application/json");	        
		        request.setEntity(se);		        
		        response = client.execute(request);
	        } catch (IOException e) {
	            log(e.getMessage());
	            e.printStackTrace();
	        } finally {
	        	client.close();
	        }
	        return response;
	    }
	}
	
	private void postData(String jsonString) throws IOException {
		String reportUrl = "http://api.dev.rothar.appbucket.eu/reports";
		new NetworkTask().execute(reportUrl, jsonString);
	}
	
	private boolean isOnline() {
	    ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}  
	
	private Location getCurrentLocation() {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(gpsLocation != null) {
			return gpsLocation;
		}
		Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		return networkLocation;
	}
	
	public UUID[] getStoleAssets() {
		List<UUID> stoleAssets = new ArrayList<UUID>();
		stoleAssets.add(UUID.fromString("F4C36EAC-0767-11E4-A4ED-B2227CCE2B54"));
		UUID[] stolenUUIDs = new UUID[stoleAssets.size()];
		stoleAssets.toArray(stolenUUIDs);
		return stolenUUIDs;
	}	
	
	private void log(String log) {
		Log.d(LOG_TAG, log);
	}
}
