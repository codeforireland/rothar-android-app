package eu.appbucket.rothar.core.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SettingsManager {
	
	private SharedPreferences settings;
	
	public enum Keys {
		USER_ID
	}
	
	public SettingsManager(Context applicationContext) {
		settings = PreferenceManager.getDefaultSharedPreferences(
				applicationContext);
	}
	
	public String getUserId() {
		return findString(Keys.USER_ID);
	}
	
	private String findString(Keys key) {
		String settingsValue = settings.getString(key.toString(), "");
		return settingsValue.trim();
	}
	
	public void setUserId(String userId) {
		storeString(Keys.USER_ID, userId);
	}
	
	private void storeString(Keys key, String value) {
		Editor editor = settings.edit();
		editor.putString(key.toString(), value);
		editor.commit(); 
	}
}
