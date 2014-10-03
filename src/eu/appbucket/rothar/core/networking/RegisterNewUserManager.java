package eu.appbucket.rothar.core.networking;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import eu.appbucket.rothar.core.networking.task.RegisterNewUserTask;
import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment;

public class RegisterNewUserManager {
	
	private Context applicationContext;
	private Activity activity;

	public RegisterNewUserManager(Context applicationContext, Activity activity) {
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
}
