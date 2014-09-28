package eu.appbucket.rothar.core.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import eu.appbucket.rothar.core.settings.SettingsManager;
import eu.appbucket.rothar.web.domain.user.UserData;

public class RegisterNewUserTask extends AsyncTask<String, Void, UserData> {
	
	private Context applicationContext;
	private Exception exception = null;
	private ProgressDialog dialog;
	
	public RegisterNewUserTask(Context applicationContext, Activity activity) {
		this.applicationContext = applicationContext;
        this.dialog = new ProgressDialog(activity);
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
			Thread.sleep(4000);
			return registerNewUser(urls[0]);
		} catch (Exception e) {
			exception = e;
			return new UserData();
		}
	}

	private UserData registerNewUser(String userRegistrationUrl) {
		UserData newUser = new UserData();
		try {
			InputStream inputStream = getFromUrl(userRegistrationUrl);
			JSONObject userDataInJson = getUserDataFromInputStream(inputStream);
			newUser.setUserId(userDataInJson.getInt("userId"));
			SettingsManager settingsManager = new SettingsManager(applicationContext);
			settingsManager.setUserId(newUser.getUserId().toString());
		} catch (Exception e) {
			exception = e;
		}
		return newUser;
	}
	
	private JSONObject getUserDataFromInputStream(InputStream inputStream) throws IOException, JSONException {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 
	    StringBuilder responseStrBuilder = new StringBuilder();

	    String inputStr;
	    while ((inputStr = streamReader.readLine()) != null) {
	        responseStrBuilder.append(inputStr);
	    }
	    return new JSONObject(responseStrBuilder.toString());
	}

	private InputStream getFromUrl(String urlString) throws IOException {
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000);
	    conn.setConnectTimeout(15000);
	    conn.setRequestMethod("POST");
	    conn.setDoInput(true);
	    conn.connect();
	    return conn.getInputStream();
	}
	
	@Override
	protected void onPostExecute(UserData newUser) {
		if(exception == null) {
			Toast.makeText(
					applicationContext, 
					"New user registered: " + newUser.getUserId(), 
					Toast.LENGTH_LONG).show();	
		} else {
			Toast.makeText(
					applicationContext, 
					"Can't register user: " + exception.getMessage(), 
					Toast.LENGTH_LONG).show();
			//TODO: How to stop user from progressing on UI ?
		}
		if (dialog.isShowing()) {
            dialog.dismiss();
        }
	}
}
