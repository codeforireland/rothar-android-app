package eu.appbucket.rothar.core.networking.task;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import eu.appbucket.rothar.core.settings.SettingsManager;
import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment;
import eu.appbucket.rothar.web.domain.user.UserData;

public class RegisterNewUserTask extends AsyncTask<String, Void, UserData> {
	
	private Context applicationContext;
	private Exception exception = null;
	private ProgressDialog dialog;
	private Activity activity;
	
	public RegisterNewUserTask(Context applicationContext, Activity activity) {
		this.applicationContext = applicationContext;
        this.dialog = new ProgressDialog(activity);
        this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {
		dialog.setTitle("One second...");
		dialog.setMessage("Registering...");
		dialog.show();
	}
	
	@Override
	protected UserData doInBackground(String... urls) {
		try {
			return registerNewUser(urls[0]);
		} catch (Exception e) {
			exception = e;
			return new UserData();
		}
	}

	private UserData registerNewUser(String userRegistrationUrl) {
		UserData newUser = new UserData();
		try {
			JSONObject userDataInJson = new TaskCommons().postToUrl(userRegistrationUrl);
			newUser.setUserId(userDataInJson.getInt("userId"));
			SettingsManager settingsManager = new SettingsManager(applicationContext);
			settingsManager.setUserId(newUser.getUserId().toString());
		} catch (Exception e) {
			exception = e;
		}
		return newUser;
	}

	@Override
	protected void onPostExecute(UserData newUser) {
		if (dialog.isShowing()) {
            dialog.dismiss();
        }
		if(exception == null) {
			Toast.makeText(
					applicationContext, 
					"New user registered: " + newUser.getUserId(), 
					Toast.LENGTH_LONG).show();
		} else {
			NetworkProblemRetryDialogFragment networkProblemRetryDialog = 
					new NetworkProblemRetryDialogFragment("Retry to Rregister new user ?");
			networkProblemRetryDialog.show(activity.getFragmentManager(), "networkProblemRetryDialog");
		}
	}
}
