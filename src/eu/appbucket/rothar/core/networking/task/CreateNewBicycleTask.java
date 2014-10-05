
package eu.appbucket.rothar.core.networking.task;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import eu.appbucket.beaconmonitor.ui.BicycleAddActivity;
import eu.appbucket.beaconmonitor.ui.MainActivity;
import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment;
import eu.appbucket.rothar.web.domain.asset.AssetData;

public class CreateNewBicycleTask extends AsyncTask<String, Void, AssetData> {
	
	private Context applicationContext;
	private Exception exception = null;
	private ProgressDialog dialog;
	private Activity activity;
	private AssetData assetToBeSaved;
	
	public CreateNewBicycleTask(Context applicationContext, Activity activity, AssetData assetToBeSaved) {
		this.applicationContext = applicationContext;
        this.dialog = new ProgressDialog(activity);
        this.activity = activity;
        this.assetToBeSaved = assetToBeSaved;
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
		assetToBeSaved.getUserId();		
		Map<String, String> payload = new HashMap<String, String>();
		payload.put("uuid", assetToBeSaved.getUuid());
		payload.put("minor", "1");
		payload.put("major", "1");
		payload.put("description", assetToBeSaved.getDescription());
		AssetData newAsset = new AssetData();
		try {
			JSONObject assetDataInJson = new TaskCommons().postToUrl(assetRegistrationUrl, payload);
			newAsset.setAssetId(assetDataInJson.getInt("assetId"));
		} catch (Exception e) {
			exception = e;
		}
		return newAsset;
	}

	@Override
	protected void onPostExecute(AssetData newAsset) {
		if (dialog.isShowing()) {
            dialog.dismiss();
        }
		if(exception == null) {		    			
			Toast.makeText(
					applicationContext, 
					"New bicycle registered: " + newAsset.getAssetId(), 
					Toast.LENGTH_LONG).show();
			 callBicycleList();
		} else {
			NetworkProblemRetryDialogFragment networkProblemRetryDialog = 
					new NetworkProblemRetryDialogFragment("Retry to Register new bicycle ?");
			networkProblemRetryDialog.show(activity.getFragmentManager(), "networkProblemRetryDialog");
		}
	}
	
	private void callBicycleList() {
    	Intent intent = new Intent(applicationContext, MainActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	applicationContext.startActivity(intent);
    }
}
