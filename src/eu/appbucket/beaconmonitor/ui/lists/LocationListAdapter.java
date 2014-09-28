package eu.appbucket.beaconmonitor.ui.lists;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationListAdapter  extends ArrayAdapter<String> {
    
    static class ViewHolder {
        public TextView text;
        public TextView description;
        public ImageView image; //TODO: Change to Google Map fragment
      }

    public LocationListAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
    }
}