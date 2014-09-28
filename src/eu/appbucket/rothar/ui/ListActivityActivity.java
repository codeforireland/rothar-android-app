package eu.appbucket.rothar.ui;

import android.app.Activity;
import android.os.Bundle;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.rothar.core.networking.RegisterNewUserManager;
import eu.appbucket.rothar.core.settings.SettingsManager;

public class ListActivityActivity extends Activity  {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_list_activity);
		registerUserIfNeeded();
	}
	
	private void registerUserIfNeeded() {
		if(!isUserRegistered()) {
			registerUser();
		}
	}
	
	private boolean isUserRegistered() {
		String userId = new SettingsManager(getApplicationContext())
			.getUserId();
		if(userId.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private void registerUser() {
		new RegisterNewUserManager(getApplicationContext(), this)
			.registerUser();
	}
}
