package eu.appbucket.beaconmonitor.core.service;

import java.util.HashMap;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData;
import uk.co.alt236.bluetoothlelib.util.IBeaconUtils;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import eu.appbucket.beaconmonitor.core.constants.App;

public class ScannerService extends BroadcastReceiver {
	
	private Context context;
	
	private static final String LOG_TAG = ScannerService.class.getName();
	
	private Map<String, BluetoothLeDevice> foundBeacons = new HashMap<String, BluetoothLeDevice>();
	
	private BluetoothAdapter bluetoothAdapter;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		scan();
	}
	
	private LeScanCallback bluetoothCallback = new LeScanCallback() {
		
		public void onLeScan(android.bluetooth.BluetoothDevice device, int rssi, byte[] scanRecord) {
			BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
			boolean isIBeacon = IBeaconUtils.isThisAnIBeacon(deviceLe);
			if(isIBeacon) {
				IBeaconManufacturerData iBeaconData = new IBeaconManufacturerData(deviceLe);
				foundBeacons.put(iBeaconData.getUUID(), deviceLe);
			} else {
				Log.d(ScannerService.class.getName(), "Not iBeacon: " + device.getAddress());
			}
		};
	};
	
	private long SCAN_DURATION = 2500;
	
	private Handler taskScheduler = new Handler();
	
	public void scan() {
		initiateBluetoothAdapter();
		startScanner();
	}

	private void log(String content) {
		Log.d(LOG_TAG, content);
	}
	
	void initiateBluetoothAdapter() {
		BluetoothManager bluetoothManager =
		        (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
	}
	
    void startScanner() {
    	log("Starting bluetooth scanner for: " + SCAN_DURATION + " [milliseconds] ...");
        bluetoothAdapter.startLeScan(bluetoothCallback);
        taskScheduler.postDelayed(scannerStopCommand, SCAN_DURATION);
        foundBeacons.clear();
    }
    
    Runnable scannerStopCommand = new Runnable() {
        @Override
        public void run() {
            stopScanner();
        }
    };
	
    void stopScanner() {
    	log("Stopping bluetooth scanner.");
        bluetoothAdapter.stopLeScan(bluetoothCallback);
        if(foundBeacons.size() > 0) {
        	log("Found " + foundBeacons.size() + " iBeacon[s].");
			notifyBroadcastReveivers();
        } else {
        	log("No iBeacons found.");
        }
    }
    
    private void notifyBroadcastReveivers() {
    	Intent notifyOnFoundDevices = new Intent(App.BROADCAST_DEVICE_FOUND_ID);
    	notifyOnFoundDevices.putExtra(App.BROADCAST_DEVICE_FOUND_PAYLOAD,
    			getFoundDevicesAsParcelableTable());
    	context.sendBroadcast(notifyOnFoundDevices);
    }
    
    private Parcelable[] getFoundDevicesAsParcelableTable() {
    	Parcelable[] devices = new Parcelable[foundBeacons.size()];
    	int i = 0;
    	for(BluetoothLeDevice device: foundBeacons.values()) {
    		devices[i++] = device;
    	}
    	return devices;
    }
}
