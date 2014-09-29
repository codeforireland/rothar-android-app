package eu.appbucket.beaconmonitor.ui.fragments;

import eu.appbucket.beaconmonitor.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class BikeViewFragment extends Fragment {

    private static final String LOG_TAG = "BikeViewFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bike_info, container, false);

        setupView(rootView);
        return rootView;
    }

    private void setupView(View rootView) {
        Bundle args = getArguments();
        String title = args.getString("title");
        String desc = args.getString("desc");
        int imageRes = args.getInt("image");

        //Set values on screen
        EditText textTag = (EditText) rootView.findViewById(R.id.bike_info_text_tag_value);
        EditText textTitle = (EditText) rootView.findViewById(R.id.bike_info_text_name_value);
        EditText textDesc = (EditText) rootView.findViewById(R.id.bike_info_text_description_value);
        ImageView imageBike = (ImageView) rootView.findViewById(R.id.bike_info_image);

        textTitle.setText(title);
        textDesc.setText(desc);
        imageBike.setImageResource(imageRes);
        
        //TODO: Pull tag data from DB
    }
}