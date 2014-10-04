package eu.appbucket.rothar.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.rothar.core.networking.NetworkingManager;
import eu.appbucket.rothar.core.settings.SettingsManager;
import eu.appbucket.rothar.ui.NetworkProblemRetryDialogFragment.NetworkProblemRetryDialogListener;
import eu.appbucket.rothar.web.domain.asset.AssetData;
import eu.appbucket.rothar.web.domain.asset.AssetStatus;

public class MainListActivity extends ListActivity 
	implements  NetworkProblemRetryDialogListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerUserIfNeeded();
		showUserAssetsAsList();
	}
	
	private void registerUserIfNeeded() {
		if(!isUserRegistered()) {
			registerUser();
		}
	}
	
	private boolean isUserRegistered() {
		String userId = new SettingsManager(getApplicationContext()).getUserId();
		if(userId.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private void registerUser() {
		new NetworkingManager(getApplicationContext(), this)
			.registerUser();
	}

	private void showUserAssetsAsList() {		
		List<AssetData> assets = new ArrayList<AssetData>();
		AssetData asset = new AssetData();
		asset.setAssetId(1);
		asset.setUuid("6a778819-a7eb-47a8-93d2-22bea45e7d6b");
		asset.setDescription("some short description");
		asset.setStatus(AssetStatus.WITH_OWNER);
		assets.add(asset);		
		asset = new AssetData();
		asset.setAssetId(2);
		asset.setUuid("598c7e61-e787-4f00-8753-c054bcd8cb0b");
		asset.setDescription("some rather longer description");
		asset.setStatus(AssetStatus.STOLEN);
		assets.add(asset);		
		asset = new AssetData();
		asset.setAssetId(3);
		asset.setUuid("c568cdff-ef23-409d-813a-fc197dce7389");
		asset.setDescription("some very longer description which is probably quite strante");
		asset.setStatus(AssetStatus.RECOVERED);
		assets.add(asset);
		ArrayAdapter<AssetData> listAdapter = 
				new ArrayAdapter<AssetData>(this, R.layout.main_list, assets);
		/*listAdapter.add(asset.toString());
		ListView mainListView = (ListView) findViewById(eu.appbucket.R.id.listView); 
		mainListView.setAdapter(listAdapter);*/
		setListAdapter(listAdapter);
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		registerUser();
	}
}
