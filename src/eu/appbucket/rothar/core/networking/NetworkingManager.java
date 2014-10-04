package eu.appbucket.rothar.core.networking;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import eu.appbucket.rothar.core.networking.task.CreateNewBicycleTask;
import eu.appbucket.rothar.core.networking.task.RegisterNewUserTask;
import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment;
import eu.appbucket.rothar.web.domain.asset.AssetData;

public class NetworkingManager {
	
	private Context applicationContext;
	private Activity activity;

	public NetworkingManager(Context applicationContext, Activity activity) {
		this.applicationContext = applicationContext;
		this.activity = activity;
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager network = 
				(ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = network.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	
	public void registerUser() {
		if(isNetworkAvailable()) {
			new RegisterNewUserTask(applicationContext, activity).execute(NetworkingConstants.API_HOST + "/users");	
		} else {
			NetworkProblemRetryDialogFragment networkProblemRetryDialog = 
					new NetworkProblemRetryDialogFragment("Retry to access network ?");
			networkProblemRetryDialog.show(activity.getFragmentManager(), "networkProblemRetryDialog");
		}
	}
	
	public void registerAsset(AssetData assetToBeSaved) {
		if(isNetworkAvailable()) {
			new CreateNewBicycleTask(applicationContext, activity, assetToBeSaved)
				.execute(NetworkingConstants.API_HOST + "/users/" + assetToBeSaved.getUserId() + "/assets");	
		} else {
			NetworkProblemRetryDialogFragment networkProblemRetryDialog = 
					new NetworkProblemRetryDialogFragment("Retry to access network ?");
			networkProblemRetryDialog.show(activity.getFragmentManager(), "networkProblemRetryDialog");
		}
	}
}
