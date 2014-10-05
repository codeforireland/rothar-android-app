package eu.appbucket.beaconmonitor.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.rothar.core.networking.NetworkingManager;
import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment.NetworkProblemRetryDialogListener;
import eu.appbucket.rothar.web.domain.asset.AssetData;

public class ConfigurationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuraiton);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.configuration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_save) {
			saveConfiguration();
			return true;
		}		
		return super.onOptionsItemSelected(item);
	}	
    
    private void saveConfiguration() {
    	
    }    
}
