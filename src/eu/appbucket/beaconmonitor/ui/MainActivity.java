package eu.appbucket.beaconmonitor.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.beaconmonitor.core.scheduler.ServiceScheduler;

public class MainActivity extends Activity {

	private static final String LOG_TAG = MainActivity.class.getName();
	private ServiceScheduler serviceScheduler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setupBackend();
		setupFrontend(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setupButtonLabel();
	}
	
	public void setupBackend() {
		serviceScheduler = new ServiceScheduler(this.getApplicationContext());
	}
	
	public void setupButtonLabel() {
		Button scanBtn = (Button) findViewById(R.id.btnScan);
		if(serviceScheduler.isSchedulerActive()) {
			scanBtn.setText("Stop scanning ...");
		} else {
			scanBtn.setText("Scan now");
		}
	}
	
	public void setupFrontend(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupButtonListener();
	}
	
	public void setupButtonListener() {
		Button scanBtn = (Button) findViewById(R.id.btnScan);
		scanBtn.setOnClickListener(
				new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					switchButtonAction();
					setupButtonLabel();
				}
			}
		);
	}
	
	private void switchButtonAction() {
		if(serviceScheduler.isSchedulerActive()) {
			stopService();
		} else {
			startService();
		}
	}
	
	public void startService() {
		serviceScheduler.startScheduler();
	}
	
	public void stopService() {
		serviceScheduler.stopScheduler();
	}
}
