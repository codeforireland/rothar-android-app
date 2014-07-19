package eu.appbucket.beaconmonitor.core.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import eu.appbucket.beaconmonitor.core.constants.App;
import eu.appbucket.beaconmonitor.core.constants.Flags;
import eu.appbucket.beaconmonitor.core.service.ScannerService;

public class ServiceScheduler {
	
	private static final String LOG_TAG = 
			ServiceScheduler.class.getName();
	
	private Context context;
	private static final long MINUTESx10 = 1000 * 60 * 10;
	private static final long SECONDSx10 = 1000 * 10;
	private static final long SECONDSx1 = 1000;
	private static final long ALARM_INTERVAL = SECONDSx10;
	
	public ServiceScheduler(Context context) {
		this.context = context;
	}
	
	public void startScheduler() {
		log("Starting the scheduler.");
		PendingIntent alarmIntent = cerateNewAlarmIntent();
		AlarmManager alarmManager = createNewAlarmManager();
		activateSchedulerFlag();
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP , 0, ALARM_INTERVAL, alarmIntent);
	}
	
	private void activateSchedulerFlag() {
		SharedPreferences settings = context.getSharedPreferences(
				App.PREFERENCES_APP_ID, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean(Flags.IS_SCHEDULER_ACTIVE, true);
	    editor.commit();
	}
	
	public void stopScheduler() {
		log("Canceling the scheduler.");
		PendingIntent alarmIntent = cerateNewAlarmIntent();
		AlarmManager alarmManager = createNewAlarmManager();
		if(alarmManager != null) {
			alarmManager.cancel(alarmIntent);
			alarmIntent.cancel();
		}
		deactivateSchedulerFlag();
	}
	
	private void deactivateSchedulerFlag() {
		SharedPreferences settings = context.getSharedPreferences(
				App.PREFERENCES_APP_ID, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean(Flags.IS_SCHEDULER_ACTIVE, false);
	    editor.commit();
	}
	
	public boolean isSchedulerActive() {
		SharedPreferences settings = context.getSharedPreferences(
				App.PREFERENCES_APP_ID, Context.MODE_PRIVATE);
		boolean isSchedulerActive = settings.getBoolean(Flags.IS_SCHEDULER_ACTIVE, false);
		return isSchedulerActive;
	}
	
	private AlarmManager createNewAlarmManager() {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		return alarmManager;
	}
	
	private PendingIntent cerateNewAlarmIntent() {
		Intent intent = new Intent(context, ScannerService.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return alarmIntent;
	}
	
	private void log(String content) {
		Log.d(LOG_TAG, content);
	}
}
