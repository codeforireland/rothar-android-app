package eu.appbucket.beaconmonitor.ui.lists;

import eu.appbucket.beaconmonitor.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BikeListAdapter extends ArrayAdapter<String> {

    private Context ctx;

    private String[] titles;
    private String[] descriptions;
    private int[] images;

    public BikeListAdapter(Context context, int resource, String[] titles, String[] descs, int[] images) {
        super(context, resource, titles);
        ctx = context;
        this.titles = titles;
        this.descriptions = descs;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            // inflate the layout
            //LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            convertView = inflater.inflate(R.layout.bike_list_row, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.bike_list_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.bike_list_description);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.bike_list_image);

            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(titles[position]);
        viewHolder.description.setText(descriptions[position]);
        viewHolder.image.setImageResource(images[position]);

        return convertView;
    }

    static class ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
      }
}