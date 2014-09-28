package eu.appbucket.rothar.core.networking;

import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class RegisterNewUserManager {
	
	private Context applicationContext;
	private Activity activity;
	private static final String SERVER = "http://api.dev.rothar.appbucket.eu/v2";

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
			new RegisterNewUserTask(applicationContext, activity).execute(SERVER + "/users");	
		} else {
			NetworkProblemRetryDialogFragment networkProblemRetryDialog = 
					new NetworkProblemRetryDialogFragment("Retry to access network ?");
			networkProblemRetryDialog.show(activity.getFragmentManager(), "networkProblemRetryDialog");
		}
	}
}
