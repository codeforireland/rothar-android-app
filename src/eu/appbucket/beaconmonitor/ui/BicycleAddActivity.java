package eu.appbucket.beaconmonitor.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.rothar.core.networking.NetworkingManager;
import eu.appbucket.rothar.core.settings.SettingsManager;
import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment.NetworkProblemRetryDialogListener;
import eu.appbucket.rothar.web.domain.asset.AssetData;

public class BicycleAddActivity extends Activity 
	implements  NetworkProblemRetryDialogListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bicycle_add);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bicycle_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_save) {
			saveNewBicycle();
			return true;
		}		
		return super.onOptionsItemSelected(item);
	}	
    
    private void saveNewBicycle() {
    	EditText tagField = (EditText) findViewById(R.id.bike_info_text_tag_value);
    	EditText descriptionField = (EditText) findViewById(R.id.bike_info_text_description_value);    	
    	String userId = "1";//(new SettingsManager(getApplicationContext())).getUserId();
    	String tag = tagField.getText().toString();
    	String description = descriptionField.getText().toString();
    	AssetData assetToBeSaved = new AssetData();
    	assetToBeSaved.setUserId(Integer.valueOf(userId));
    	assetToBeSaved.setUuid(tag);
    	assetToBeSaved.setDescription(description);
		new NetworkingManager(getApplicationContext(), this)
			.registerAsset(assetToBeSaved);
    }
    
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		saveNewBicycle();
	}
}
