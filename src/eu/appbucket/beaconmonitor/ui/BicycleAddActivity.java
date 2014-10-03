package eu.appbucket.beaconmonitor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import eu.appbucket.beaconmonitor.R;

public class BicycleAddActivity extends Activity {

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
    	Toast.makeText(this, "Save pressed", Toast.LENGTH_SHORT).show();
    }
}
