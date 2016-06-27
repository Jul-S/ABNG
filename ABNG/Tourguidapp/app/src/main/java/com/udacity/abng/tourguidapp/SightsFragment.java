package com.udacity.abng.tourguidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 */
public class SightsFragment extends Fragment {

    public SightsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        // Create a list of locations
        final ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(R.string.sites_name1,
                R.string.sites_description1, R.drawable.sights_london_eye));
        locations.add(new Location(R.string.sites_name2,
                R.string.sites_description2, R.drawable.sights_tower_bridge));
        locations.add(new Location(R.string.sites_name3,
                R.string.sites_description3, R.drawable.sights_westminster_abbey));
        locations.add(new Location(R.string.sites_name4,
                R.string.sites_description4, R.drawable.sights_british_museum));
        locations.add(new Location(R.string.sites_name5,
                R.string.sites_description5, R.drawable.sights_big_ben));
        locations.add(new Location(R.string.sites_name6,
                R.string.sites_description6, R.drawable.sights_buckingham_palace));
        locations.add(new Location(R.string.sites_name7,
                R.string.sites_description7, R.drawable.sights_stpaul_cathedral));
        locations.add(new Location(R.string.sites_name8,
                R.string.sites_description8, R.drawable.sights_palace_of_westmister));

        // Create an {@link LocationAdapter}, whose data source is a list of {@link Locations}. The
        // adapter knows how to create list items for each item in the list.
        LocationAdapter adapter = new LocationAdapter(getActivity(), locations, R.color.category_sights);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // location_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link LocationAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link location} in the list.
        listView.setAdapter(adapter);

        return rootView;
    }
}
