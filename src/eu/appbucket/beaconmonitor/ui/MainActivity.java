package eu.appbucket.beaconmonitor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import eu.appbucket.beaconmonitor.BicycleAddActivity;
import eu.appbucket.beaconmonitor.R;
import eu.appbucket.beaconmonitor.ui.fragments.HomeFragment;

public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "MainActivity::onCreate");
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_add:
                Toast.makeText(this, "Add button pressed", Toast.LENGTH_SHORT).show();
                runBicycleAddAction();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings pressed", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                Toast.makeText(this, "Home pressed", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void runBicycleAddAction() {
    	Intent intent = new Intent(this, BicycleAddActivity.class);
    	startActivity(intent);
    }
}