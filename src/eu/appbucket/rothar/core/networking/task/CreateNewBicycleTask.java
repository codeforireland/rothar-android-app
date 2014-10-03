
package eu.appbucket.rothar.core.networking.task;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import eu.appbucket.rothar.core.settings.SettingsManager;
import eu.appbucket.rothar.web.domain.asset.AssetData;
import eu.appbucket.rothar.web.domain.user.UserData;

public class CreateNewBicycleTask extends AsyncTask<String, Void, AssetData> {
	
	private Context applicationContext;
	private Exception exception = null;
	private ProgressDialog dialog;
	private Activity activity;
	
	public CreateNewBicycleTask(Context applicationContext, Activity activity) {
		this.applicationContext = applicationContext;
        this.dialog = new ProgressDialog(activity);
        this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {
		dialog.setTitle("One second...");
		dialog.setMessage("Registering bicycle...");
		dialog.show();
	}
	
	@Override
	protected AssetData doInBackground(String... urls) {
		try {
			return createNewAsset(urls[0]);
		} catch (Exception e) {
			exception = e;
			return new AssetData();
		}
	}
	
	private AssetData createNewAsset(String assetRegistrationUrl) {
		AssetData newAsset = new AssetData();
		/*try {
			JSONObject assetDataInJson = new TaskCommons().getJsonFromUrl(assetRegistrationUrl);
			newAsset.setAssetId(assetDataInJson.getInt("assetId"));
			SettingsManager settingsManager = new SettingsManager(applicationContext);
			settingsManager.setUserId(newUser.getUserId().toString());
		} catch (Exception e) {
			exception = e;
		}*/
		return newAsset;
	}


}
