package eu.appbucket.beaconmonitor.ui.fragments;

import eu.appbucket.beaconmonitor.R;
import eu.appbucket.beaconmonitor.core.scheduler.ServiceScheduler;
import eu.appbucket.beaconmonitor.ui.lists.BikeListAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {
    
    private ServiceScheduler serviceScheduler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);
        serviceScheduler = new ServiceScheduler(this.getActivity());

        Button scanBtn = (Button) rootView.findViewById(R.id.btnScan);
        scanBtn.setOnClickListener(
                new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchButtonAction();
                    setupButtonLabel();
                }
            }
        );

        setupBikesList(rootView);
        return rootView;
    }

    private void setupBikesList(View rootView) {
        //Test data
        String[] titles = new String[] {"My bike", "My mothers bike", "My race bike"};
        String[] desc = new String[] {"My everyday bike", "The bike she owns", "The expensive bike"};
        int[] images = new int[] {R.drawable.bike, R.drawable.bike, R.drawable.bike};
        // our adapter instance
        BikeListAdapter adapter = new BikeListAdapter(rootView.getContext(), R.layout.bike_list_row, titles, desc, images);

        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = (ListView) rootView.findViewById(R.id.main_list);
        listViewItems.setAdapter(adapter);
        listViewItems.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Click item: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setupButtonLabel();
    }

    public void setupButtonLabel() {
        Button scanBtn = (Button) getActivity().findViewById(R.id.btnScan);
        if(serviceScheduler.isSchedulerActive()) {
            scanBtn.setText("Stop scanning ...");
        } else {
            scanBtn.setText("Scan now");
        }
    }

    private void switchButtonAction() {
        if(serviceScheduler.isSchedulerActive()) {
            stopService();
        } else {
            startService();
        }
    }

    public void startService() {
        serviceScheduler.startScheduler();
    }

    public void stopService() {
        serviceScheduler.stopScheduler();
    }
}