package eu.appbucket.beaconmonitor.ui;

import java.util.Locale;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData;
import uk.co.alt236.bluetoothlelib.resolvers.CompanyIdentifierResolver;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.beaconmonitor.core.constants.App;
import eu.appbucket.beaconmonitor.core.service.ScannerService;

public class NotificationReceiver extends BroadcastReceiver {
	
	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		Parcelable[] foundDevices = intent.getParcelableArrayExtra(App.BROADCAST_DEVICE_FOUND_PAYLOAD);
		for(Parcelable device:  foundDevices) {
			logBeaconDetails((BluetoothLeDevice) device);
			buildNotification((BluetoothLeDevice) device);
		}
	}
	
	private void buildNotification(BluetoothLeDevice beacon) {
		IBeaconManufacturerData iBeaconData = new IBeaconManufacturerData(beacon);
		Notification notification  = new Notification.Builder(context)
	        .setContentTitle("Stolen bicycle found.")
	        .setContentText("Bicycle id: " + iBeaconData.getUUID())
	        .setSmallIcon(R.drawable.ic_notification)
	        .setAutoCancel(true)
	        .build();
		NotificationManager notificationManager = (NotificationManager) 
				context.getSystemService(Context.NOTIFICATION_SERVICE); 
		notificationManager.notify(0, notification); 
	}
	
    private void logBeaconDetails(BluetoothLeDevice beacon) {
    	IBeaconManufacturerData iBeaconData = new IBeaconManufacturerData(beacon);
    	String companyId = 
				CompanyIdentifierResolver.getCompanyName(iBeaconData.getCompanyIdentifier(), 
				" (" + hexEncode(iBeaconData.getCompanyIdentifier()) + ")");
    	String advert = iBeaconData.getIBeaconAdvertisement() + " (" + hexEncode( iBeaconData.getIBeaconAdvertisement() ) + ")";
		String uuid = iBeaconData.getUUID().toString();
		String major = iBeaconData.getMajor() + " (" + hexEncode( iBeaconData.getMajor() ) + ")";
		String minor = iBeaconData.getMinor() + " (" + hexEncode( iBeaconData.getMinor() ) + ")";
		String txPower = iBeaconData.getCalibratedTxPower() + " (" + hexEncode( iBeaconData.getCalibratedTxPower() ) + ")";
		Log.d(ScannerService.class.getName(), 
				"Found iBeacon, company id: " + companyId + ", uuid: " + uuid + ", major: " + major + ", minor: " + minor 
				+ ", txPower: " + txPower + ", advert: " + advert);
    }
    
	private static String hexEncode(int integer){
		return "0x" + Integer.toHexString(integer).toUpperCase(Locale.US);
	}
}
