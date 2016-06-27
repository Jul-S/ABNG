package com.udacity.abng.tourguidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by yul on 27.06.16.
 */
public class ShopsFragment extends Fragment {

    public ShopsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        // Create a list of locations
        final ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(R.string.shop_name1,
                R.string.shop_description1, R.drawable.shops_icon));
        locations.add(new Location(R.string.shop_name2,
                R.string.shop_description2, R.drawable.shops_icon));
        locations.add(new Location(R.string.shop_name3,
                R.string.shop_description3, R.drawable.shops_icon));
        locations.add(new Location(R.string.shop_name4,
                R.string.shop_description4, R.drawable.shops_icon));
        locations.add(new Location(R.string.shop_name5,
                R.string.shop_description5, R.drawable.shops_icon));
        locations.add(new Location(R.string.shop_name6,
                R.string.shop_description6, R.drawable.shops_icon));
        locations.add(new Location(R.string.shop_name7,
                R.string.shop_description7, R.drawable.shops_icon));
        locations.add(new Location(R.string.shop_name8,
                R.string.shop_description8, R.drawable.shops_icon));

        // Create an {@link LocationAdapter}, whose data source is a list of {@link Locations}. The
        // adapter knows how to create list items for each item in the list.
        LocationAdapter adapter = new LocationAdapter(getActivity(), locations, R.color.category_shops);

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
