package eu.appbucket.beaconmonitor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.beaconmonitor.ui.fragments.HomeFragment;

public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "MainActivity::onCreate");
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new HomeFragment()).commit();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                callBicycleAdd();
                return true;
            case R.id.action_settings:
                callConfiguraiton();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void callBicycleAdd() {
    	Intent intent = new Intent(this, BicycleAddActivity.class);
    	startActivity(intent);
    }
    
    private void callConfiguraiton() {
    	Intent intent = new Intent(this, ConfigurationActivity.class);
    	startActivity(intent);
    }
}